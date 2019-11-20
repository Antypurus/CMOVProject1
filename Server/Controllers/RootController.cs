using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
using Server.Models;
using Server.Utils;

namespace Server.Controllers
{
    [Microsoft.AspNetCore.Mvc.Route("/")]
    [ApiController]
    public class RootController : ControllerBase
    {
        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        [Microsoft.AspNetCore.Mvc.HttpGet("status")]
        [Microsoft.AspNetCore.Mvc.HttpGet("heartbeat")]
        public string HeartBeat()
        {
            return "Alive";
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        [Microsoft.AspNetCore.Mvc.HttpPost("register")]
        public JObject register([Microsoft.AspNetCore.Mvc.FromBody]JObject data)
        {
            string name = data["name"].ToString();
            string username = data["username"].ToString();
            string password = data["password"].ToString();
            int credit = data["credit_card_no"].ToObject<int>();
            string key = data["public_key"].ToString();
            JObject ret = new JObject();
            ret.Add("user_id", Client.RegisterUser(name, username, password, key, credit).GetClientID().ToString());
            ret.Add("server_key", RSAEncrypter.GetRSAEncrypter().GetPEMPublicKey());
            return ret;
        }

        /// <summary>
        /// Return the product list as well as the decryption key necessary to get the product data
        /// </summary>
        /// <returns></returns>
        [Microsoft.AspNetCore.Mvc.HttpGet("products")]
        public JObject products()
        {
            List<Product> productList = Product.GetProducts();
            JArray signedProductList = new JArray();
            foreach (Product product in productList)
            {
                JObject jsonProduct = new JObject();
                jsonProduct.Add("id", product.GetProductID().ToByteArray());
                jsonProduct.Add("name", product.GetProductName());
                jsonProduct.Add("euros", product.GetPriceEuros());
                jsonProduct.Add("cents", product.GetPriceCents());
                string productJSONString = jsonProduct.ToString();

                string productSignedHash = RSAEncrypter.GetRSAEncrypter().Sign(productJSONString);
                JObject signedProduct = new JObject();
                signedProduct.Add("product", productJSONString);
                signedProduct.Add("sign", productSignedHash);
                signedProductList.Add(signedProduct);
            }
            JObject response = new JObject();
            response.Add("products", signedProductList);
            response.Add("key", RSAEncrypter.GetRSAEncrypter().GetPEMPublicKey());
            return response;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="user_id"></param>
        /// <returns></returns>
        [Microsoft.AspNetCore.Mvc.HttpGet("transactions")]
        public List<JObject> transactions(string user_id)
        {
            List<Transaction> transactions = Transaction.GetTransactions(user_id);
            List<JObject> jsonTransactions = new List<JObject>();
            foreach (Transaction transaction in transactions)
            {
                jsonTransactions.Add(transaction.GetJSON());
            }
            return jsonTransactions;
        }

        [Microsoft.AspNetCore.Mvc.HttpGet("coupons")]
        public JObject coupons(string user_id)
        {
            List<Voucher> vouchers = Voucher.GetVouchers(user_id);
            KeyValuePair<int, int> accumulated_discount = Client.GetAccumulatedDiscount(user_id);

            if (accumulated_discount.Key == -1 || accumulated_discount.Value == -1)
            {
                HttpResponseMessage message = new HttpResponseMessage(HttpStatusCode.NotFound);
                message.Content = new StringContent("User ID Is Not Valid");
                throw new HttpResponseException(message);
            }

            JObject response = new JObject();
            JArray vouchersJSON = new JArray();
            foreach (Voucher voucher in vouchers)
            {
                vouchersJSON.Add(voucher.GetJSON());
            }
            response.Add("vouchers", vouchersJSON);
            response.Add("accumulated_euro", accumulated_discount.Key);
            response.Add("accumulated_cent", accumulated_discount.Value);

            return response;
        }

        [Microsoft.AspNetCore.Mvc.HttpPost("checkout")]
        public bool checkout([Microsoft.AspNetCore.Mvc.FromBody]JObject data)
        {
            string products = data["products"].ToString();
            string user_id = data["user_id"].ToString();
            bool use_discount = data["use_discount"].ToObject<bool>();
            string sign = data["sign"].ToString();
            string voucher_id_string = "";
            if (data.ContainsKey("voucher_id"))
            {
                voucher_id_string = data["voucher_id"].ToString();
                bool valid = Voucher.isVoucherAvailable(voucher_id_string);
                if (!valid)
                {
                    HttpResponseMessage message = new HttpResponseMessage(HttpStatusCode.Unauthorized);
                    message.Content = new StringContent("Invalid Coupon");
                    throw new HttpResponseException(message);
                }
            }

            List<Product> products_list = new List<Product>();
            string id_string = "";
            float final_price = 0.0f;
            JArray prods = new JArray(products);
            for (int i = 0; i < prods.Count; ++i)
            {
                string product_id = prods.ElementAt(i).ToString();
                id_string += product_id;
                Product product = Product.GetProduct(product_id);
                if (product == null)
                {
                    HttpResponseMessage message = new HttpResponseMessage(HttpStatusCode.NotFound);
                    message.Content = new StringContent("Invalid Product ID");
                    throw new HttpResponseException(message);
                }
                float price = product.GetPriceEuros() + product.GetPriceCents() / 100.0f;
                final_price += price;
                products_list.Add(product);
            }

            string user_key = Client.GetUserPublicKey(user_id);
            if (user_key == null)
            {
                HttpResponseMessage message = new HttpResponseMessage(HttpStatusCode.NotFound);
                message.Content = new StringContent("Invalid User ID");
                throw new HttpResponseException(message);
            }

            /*
            RSAEncrypter encrypter = RSAEncrypter.GetRSAEncrypter();
            if (!encrypter.Verify(id_string, sign, user_key))
            {
                HttpResponseMessage message = new HttpResponseMessage(HttpStatusCode.Unauthorized);
                message.Content = new StringContent("Invalid Content Signature");
                throw new HttpResponseException(message);
            }*/

            // figure out if voucher should be generated
            bool generateVoucher = false;
            int vouchersToGenerate = 0;
            KeyValuePair<int, int> spent = Client.GetTotalAmmountSpent(user_id);
            float hundredsSpent = MathF.Truncate(spent.Key / 100.0f);
            float totalAmmountSpent = spent.Key + spent.Value / 100.0f;
            totalAmmountSpent += final_price;
            float newHundredsSpent = MathF.Truncate(totalAmmountSpent);

            if (!newHundredsSpent.Equals(hundredsSpent))
            {
                generateVoucher = true;
                vouchersToGenerate = (int)(newHundredsSpent - hundredsSpent);
            }


            float account_delta = 0;

            KeyValuePair<int, int> accumulated = Client.GetAccumulatedDiscount(user_id);
            float totalAccumulated = accumulated.Key + accumulated.Value / 100.0f;
            if (use_discount)
            {
                if (totalAccumulated >= final_price)
                {
                    account_delta = totalAccumulated - final_price;
                }
                else
                {
                    account_delta = -totalAccumulated;
                }

            }

            if (voucher_id_string != "")
            {
                Voucher.Use(voucher_id_string);
                account_delta += 0.15f * final_price;
            }

            // generate transaction
            if (voucher_id_string != "")
            {
                Transaction.RegisterTransaction(user_id, voucher_id_string, use_discount, products_list);
            }
            else
            {
                Transaction.RegisterTransaction(user_id, "NULL", use_discount, products_list);
            }

            // update current spent
            int spent_euros = (int)MathF.Truncate(totalAmmountSpent);
            int spent_cents = (int)(totalAmmountSpent - spent_euros);
            Client.UpdateTotalSpent(user_id, spent_euros, spent_cents);

            // update ammount in account
            totalAccumulated += account_delta;
            int accumulated_euros = (int)MathF.Truncate(totalAccumulated);
            int accumulated_cents = (int)(totalAccumulated - accumulated_euros);
            Client.UpdateTotalAccumulated(user_id, accumulated_euros, accumulated_cents);

            if (generateVoucher)
            {
                // add voucher to user account
                for (int i = 0; i < vouchersToGenerate; ++i)
                {
                    Voucher.CreateVoucher(user_id);
                }
            }

            return true;
        }

    }
}