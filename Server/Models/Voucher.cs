using Newtonsoft.Json.Linq;
using Server.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class Voucher
    {

        private Guid id;
        private Guid client;
        private bool wasUsed;

        public static void CreateVoucher(string user_id)
        {
            Database database = Database.GetDatabase();
            Entry entry = new Entry { name = "user_id", value = user_id,isUUID=true };
            database.Insert("insert into Voucher(client) values(@user_id);", new List<Entry> { entry });
        }

        private Voucher(Guid id, Guid client, bool wasUsed)
        {
            this.id = id;
            this.client = client;
            this.wasUsed = wasUsed;
        }

        public JObject GetJSON()
        {
            JObject voucherJSON = new JObject();
            voucherJSON.Add("voucher_id", this.id.ToString());
            voucherJSON.Add("client_id", this.client.ToString());
            voucherJSON.Add("was_used", this.wasUsed);
            return voucherJSON;
        }

        public static List<Voucher> GetVouchers(string user_id)
        {
            List<Voucher> vouchers = new List<Voucher>();

            Database database = Database.GetDatabase();
            Entry client_id = new Entry { name = "client_id", value = user_id,isUUID=true };
            List<Dictionary<string, object>> voucher_data = database.Select("select * from Voucher where was_used=FALSE and client=@client_id;", new List<Entry> { client_id });

            foreach (Dictionary<string, object> voucher in voucher_data)
            {
                Guid id = (Guid)voucher["id"];
                Guid client = (Guid)voucher["client"];
                bool wasUsed = (bool)voucher["was_used"];

                vouchers.Add(new Voucher(id, client, wasUsed));
            }

            return vouchers;
        }

        public static bool isVoucherAvailable(string voucher_id)
        {
            Database database = Database.GetDatabase();
            Entry voucher = new Entry { name = "voucher_id", value = voucher_id,isUUID=true };
            List<Dictionary<string, object>> result = database.Select("select exists(select * from Voucher where id=@voucher_id and was_used=FALSE);", new List<Entry> { voucher });
            return (bool)result[0]["exists"];
        }

        public static void Use(string voucher_id)
        {
            Database database = Database.GetDatabase();
            Entry voucher = new Entry { name = "voucher_id", value = voucher_id,isUUID=true };
            database.Insert("update Voucher set was_used=TRUE where id=@voucher_id", new List<Entry> { voucher });
        }

    }
}
