using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Server.Utils;

namespace Server.Controllers
{
    [Route("/")]
    [ApiController]
    public class RootController : ControllerBase
    {
        [HttpGet("status")]
        [HttpGet("heartbeat")]
        public string HeartBeat()
        {
            return "Alive";
        }

        [HttpGet("test")]
        public List<Dictionary<string,object>> insert_test()
        {
            List<Entry> values = new List<Entry>();
            //values.Add(new Entry { name = "e", value = 1 });
            //values.Add(new Entry { name = "c", value = 2 });
            //values.Add(new Entry { name = "n", value = "choura" });
            return Database.GetDatabase().Select("select * from Product;", values);
        }
    }
}