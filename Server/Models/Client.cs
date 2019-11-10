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
            entries.Add(new Entry { name = "name", value = name });
            entries.Add(new Entry { name = "username", value = username });
            entries.Add(new Entry { name = "password", value = password });
            entries.Add(new Entry { name = "credit", value = credit_card });
            entries.Add(new Entry { name = "key", value = public_key });
            Guid userID = (Guid)db.InsertWithReturn("insert into " +
                "Client(name,username,password,credit_card,public_key) " +
                "values(@name,@username,@password,@credit,@key) returning id;", entries);
            Client client = new Client(userID);
            return client;
        }
    }
}
