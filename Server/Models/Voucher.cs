﻿using Newtonsoft.Json.Linq;
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

        private Voucher(Guid id, Guid client, bool wasUsed)
        {
            this.id = id;
            this.client = client;
            this.wasUsed = wasUsed;
        }

        public JObject GetJSON()
        {
            JObject voucherJSON = new JObject();
            voucherJSON.Add("voucher_id",this.id.ToString());
            voucherJSON.Add("client_id",this.client.ToString());
            voucherJSON.Add("was_used",this.wasUsed);
            return voucherJSON;
        }

        public static List<Voucher> GetVouchers(string user_id)
        {
            List<Voucher> vouchers = new List<Voucher>();

            Database database = Database.GetDatabase();
            Entry client_id = new Entry { name = "client_id", value = user_id };
            List<Dictionary<string, object>> voucher_data = database.Select("select * from Voucher where was_used = FALSE and client=@client_id;", new List<Entry> { client_id });

            foreach(Dictionary<string,object> voucher in voucher_data)
            {
                Guid id = (Guid)voucher["id"];
                Guid client = (Guid)voucher["client"];
                bool wasUsed = (bool)voucher["was_used"];

                vouchers.Add(new Voucher(id,client,wasUsed));
            }

            return vouchers;
        }


    }
}
