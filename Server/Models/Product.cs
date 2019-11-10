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

        private Product(Guid id, int euros, int cents, string name, string imageURL)
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

        public static List<Product> GetProducts()
        {
            Database db = Database.GetDatabase();
            List<Product> productList = new List<Product>();
            List<Dictionary<string, object>> products = db.Select("select * from Product;", new List<Entry>());
            foreach (Dictionary<string, object> productData in products)
            {
                Object image_url = productData["image_url"];
                string image_url_string = "";
                if(image_url != null)
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

    }
}
