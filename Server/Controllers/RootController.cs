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
            ret.Add("user_id",Client.RegisterUser(name,username,password,key,credit).GetClientID().ToString());
            ret.Add("server_key",RSAEncrypter.GetRSAEncrypter().GetPEMPublicKey());
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
            foreach(Product product in productList)
            {
                JObject jsonProduct = new JObject();
                jsonProduct.Add("id",product.GetProductID().ToByteArray());
                jsonProduct.Add("name",product.GetProductName());
                jsonProduct.Add("euros",product.GetPriceEuros());
                jsonProduct.Add("cents",product.GetPriceCents());
                string productJSONString = jsonProduct.ToString();

                string productSignedHash = RSAEncrypter.GetRSAEncrypter().Sign(productJSONString);
                JObject signedProduct = new JObject();
                signedProduct.Add("product",productJSONString);
                signedProduct.Add("sign", productSignedHash);
                signedProductList.Add(signedProduct);
            }
            JObject response = new JObject();
            response.Add("products",signedProductList);
            response.Add("key",RSAEncrypter.GetRSAEncrypter().GetPEMPublicKey());
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
            foreach(Transaction transaction in transactions)
            {
                jsonTransactions.Add(transaction.GetJSON());
            }
            return jsonTransactions;
        }

        [Microsoft.AspNetCore.Mvc.HttpGet("coupons")]
        public JObject coupons(string user_id)
        {
            List<Voucher> vouchers = Voucher.GetVouchers(user_id);
            KeyValuePair<int,int> accumulated_discount = Client.GetAccumulatedDiscount(user_id);
            
            if(accumulated_discount.Key == -1 || accumulated_discount.Value == -1)
            {
                HttpResponseMessage message = new HttpResponseMessage(HttpStatusCode.NotFound);
                message.Content = new StringContent("User ID Is Not Valid");
                throw new HttpResponseException(message);
            }

            JObject response = new JObject();
            JArray vouchersJSON = new JArray();
            foreach(Voucher voucher in vouchers)
            {
                vouchersJSON.Add(voucher.GetJSON());
            }
            response.Add("vouchers",vouchersJSON);
            response.Add("accumulated_euro",accumulated_discount.Key);
            response.Add("accumulated_cent",accumulated_discount.Value);

            return response;
        }

    }
}