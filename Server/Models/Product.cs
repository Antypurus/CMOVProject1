using Newtonsoft.Json.Linq;
using Server.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class Product
    {



        private Guid productID;
        private int productPriceEuros;
        private int productPriceCents;
        private string productName;
        private string productImageURL;

        public Guid GetProductID()
        {
            return this.productID;
        }

        public int GetPriceEuros()
        {
            return this.productPriceEuros;
        }

        public int GetPriceCents()
        {
            return this.productPriceCents;
        }

        public string GetProductName()
        {
            return this.productName;
        }

        public string GetProductImageURL()
        {
            return this.productImageURL;
        }

        public Product(Guid id, int euros, int cents, string name, string imageURL)
        {
            this.productID = id;
            this.productPriceEuros = euros;
            this.productPriceCents = cents;
            this.productName = name;
            if (imageURL != null)
            {
                this.productImageURL = imageURL;
            }
            else
            {
                this.productImageURL = "";
            }
        }

        public JObject GetJSON()
        {
            JObject jsonProduct = new JObject();
            jsonProduct.Add("id", this.GetProductID().ToByteArray());
            jsonProduct.Add("name", this.GetProductName());
            jsonProduct.Add("euros", this.GetPriceEuros());
            jsonProduct.Add("cents", this.GetPriceCents());
            return jsonProduct;
        }

        public static List<Product> GetProducts()
        {
            Database db = Database.GetDatabase();
            List<Product> productList = new List<Product>();
            List<Dictionary<string, object>> products = db.Select("select * from Product;", new List<Entry>());
            foreach (Dictionary<string, object> productData in products)
            {
                Object image_url = productData["image_url"];
                string image_url_string = "";
                if (image_url != null)
                {
                    image_url_string = (string)image_url;
                }
                Product product = new Product(
                    (Guid)productData["id"],
                    (int)productData["price_euro"],
                    (int)productData["price_cent"],
                    (string)productData["name"],
                    image_url_string);
                productList.Add(product);
            }
            return productList;
        }

        public static Product GetProduct(string product_id)
        {
            Database db = Database.GetDatabase();
            Entry product_entry = new Entry { name = "product_id", value = product_id, isUUID = true };
            List<Dictionary<string, object>> products = db.Select("select * from Product where id=@product_id;", new List<Entry> { product_entry });
            if (products.Count <= 0) return null;
            Dictionary<string, object> productData = products[0];
            Object image_url = productData["image_url"];
            string image_url_string = "";
            if (image_url != null)
            {
                image_url_string = (string)image_url;
            }
            Product product = new Product(
                (Guid)productData["id"],
                (int)productData["price_euro"],
                (int)productData["price_cent"],
                (string)productData["name"],
                image_url_string);
            return product;
        }

        public static void SetTransaction(string product_id, string transaction_id)
        {
            Database db = Database.GetDatabase();
            Entry purchase = new Entry { name = "purchase_id", value = transaction_id, isUUID = true };
            Entry product = new Entry { name = "product_id", value = product_id, isUUID = true };
            db.Insert("update Product set purchase=@purchase_id where id=@product_id;", new List<Entry> { purchase, product });
        }

    }
}
