using Newtonsoft.Json.Linq;
using Server.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class Transaction
    {

        private Guid id;
        private Guid client;
        private Guid voucher;
        private bool was_discount_used;

        private List<Product> products;

        public static void RegisterTransaction(string client, string voucher, bool wasDiscounted, List<Product> products)
        {
            Entry client_id = new Entry { name = "client_id", value = client };
            Entry voucher_id = new Entry { name = "voucher_id", value = voucher };
            Entry was_discounted = new Entry { name = "should_discount", value = wasDiscounted };
            Database database = Database.GetDatabase();
            string id = (string)database.InsertWithReturn("insert into Purchase(client,voucher,should_discount) values (@client_id,@voucher_id,@should_discounrd) returning id;",
                new List<Entry>{client_id,voucher_id,was_discounted});
            foreach (Product product in products)
            {
                
            }
        }

        private Transaction(Guid id, Guid client, Guid voucher, bool wasDiscountUser, List<Product> products)
        {
            this.id = id;
            this.client = client;
            this.voucher = voucher;
            this.was_discount_used = was_discount_used;
            this.products = products;
        }

        public JObject GetJSON()
        {
            JObject jsonTransaction = new JObject();
            jsonTransaction.Add("transaction_id", this.id.ToString());
            jsonTransaction.Add("client_id", this.client.ToString());
            jsonTransaction.Add("was_discounter", this.was_discount_used);

            JArray products = new JArray();
            foreach (Product product in this.products)
            {
                products.Add(product.GetJSON());
            }
            jsonTransaction.Add("products", products);

            return jsonTransaction;
        }

        public static List<Transaction> GetTransactions(string user_id)
        {
            List<Transaction> transactions = new List<Transaction>();

            Database database = Database.GetDatabase();

            Entry client_id = new Entry();
            client_id.name = "client_id";
            client_id.value = "user_id";
            List<Dictionary<string, object>> transactions_data = database.Select("select * from Purchase where client = @client_id;", new List<Entry> { client_id });
            foreach (Dictionary<string, object> transaction_data in transactions_data)
            {
                Guid id = (Guid)transaction_data["id"];
                Guid client = (Guid)transaction_data["client"];
                Guid voucher = (Guid)transaction_data["voucher"];
                bool wasDiscounter = (bool)transaction_data["should_discount"];

                List<Product> products = new List<Product>();

                Entry purchase_id = new Entry { name = "purchase_id", value = id.ToString() };
                List<Dictionary<string, object>> products_data = database.Select("select * from Product where purchase=@purchase_id", new List<Entry> { purchase_id });
                foreach (Dictionary<string, object> product in products_data)
                {
                    Guid product_id = (Guid)product["id"];
                    int price_euro = (int)product["price_euro"];
                    int price_cent = (int)product["price_cent"];
                    string name = (string)product["name"];
                    Object image_url = product["image_url"];
                    string image_url_string = "";
                    if (image_url != null)
                    {
                        image_url_string = (string)image_url;
                    }

                    products.Add(new Product(product_id, price_euro, price_cent, name, image_url_string));
                }

                transactions.Add(new Transaction(id, client, voucher, wasDiscounter, products));
            }

            return transactions;
        }
    }
}
