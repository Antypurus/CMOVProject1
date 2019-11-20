using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Utils;

namespace Server.Models
{
    public class Client
    {
        private Guid ClientID;

        public Client(Guid clientID)
        {
            this.ClientID = clientID;
        }

        public Client(string clientID)
        {
            this.ClientID = Guid.Parse(clientID);
        }

        public Guid GetClientID()
        {
            return this.ClientID;
        }

        public static Client RegisterUser(string name, string username, string password, string public_key, int credit_card)
        {
            Database db = Database.GetDatabase();
            List<Entry> entries = new List<Entry>();
            entries.Add(new Entry { name = "name", value = name ,isUUID=false});
            entries.Add(new Entry { name = "username", value = username,isUUID=false });
            entries.Add(new Entry { name = "password", value = password ,isUUID=false});
            entries.Add(new Entry { name = "credit", value = credit_card,isUUID=false });
            entries.Add(new Entry { name = "key", value = public_key,isUUID=false });
            Guid userID = (Guid)db.InsertWithReturn("insert into " +
                "Client(name,username,password,credit_card,public_key) " +
                "values(@name,@username,@password,@credit,@key) returning id;", entries);
            Client client = new Client(userID);
            return client;
        }

        public static KeyValuePair<int, int> GetAccumulatedDiscount(String user_id)
        {
            Database database = Database.GetDatabase();

            Entry client_id = new Entry { name = "client_id", value = user_id ,isUUID=true};
            List<Dictionary<String, object>> values = database.Select("select current_accumulated_euro, current_accumulated_cent from Client where id=@client_id", new List<Entry> { client_id });
            if (values.Count > 0)
            {
                return new KeyValuePair<int, int>((int)values[0]["current_accumulated_euro"], (int)values[0]["current_accumulated_cent"]);
            }
            else
            {
                return new KeyValuePair<int, int>(-1, -1);
            }
        }

        public static KeyValuePair<int, int> GetTotalAmmountSpent(String user_id)
        {
            Database database = Database.GetDatabase();

            Entry client_id = new Entry { name = "client_id", value = user_id ,isUUID=true};
            List<Dictionary<String, object>> values = database.Select("select current_total_spent_euro, current_total_spent_cent from Client where id=@client_id", new List<Entry> { client_id });
            if (values.Count > 0)
            {
                return new KeyValuePair<int, int>((int)values[0]["current_total_spent_euro"], (int)values[0]["current_total_spent_cent"]);
            }
            else
            {
                return new KeyValuePair<int, int>(-1, -1);
            }
        }

        public static void UpdateTotalSpent(string user_id, int euro, int cent)
        {
            Database database = Database.GetDatabase();
            Entry user_entry = new Entry { name = "user_id", value = user_id,isUUID=true };
            Entry euro_entry = new Entry { name = "euro", value = euro ,isUUID=false};
            Entry cent_entry = new Entry { name = "cent", value = cent ,isUUID=false};
            database.Insert("update Client set current_total_spent_euro=@euro, current_total_spent_cent=@cent where id=@user_id", new List<Entry> { user_entry, euro_entry, cent_entry });
        }

        public static void UpdateTotalAccumulated(string user_id, int euro, int cent)
        {
            Database database = Database.GetDatabase();
            Entry user_entry = new Entry { name = "user_id", value = user_id ,isUUID=true};
            Entry euro_entry = new Entry { name = "euro", value = euro ,isUUID=false};
            Entry cent_entry = new Entry { name = "cent", value = cent ,isUUID=false};
            database.Insert("update Client set current_accumulated_euro=@euro, current_accumulated_cent=@cent where id=@user_id", new List<Entry> { user_entry, euro_entry, cent_entry });
        }

        public static string GetUserPublicKey(string user_id)
        {
            Database database = Database.GetDatabase();
            Entry entry = new Entry { name = "client_id", value = user_id ,isUUID=true};
            List<Dictionary<string, object>> result = database.Select("select public_key from Client where id=@client_id", new List<Entry> { entry });
            if (result.Count <= 0) return null;
            return (string)result[0]["public_key"];
        }

    }
}
