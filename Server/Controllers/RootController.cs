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
        public bool insert_test()
        {
            List<Entry> values = new List<Entry>();
            values.Add(new Entry { name = "p", value = 5 });
            return Database.GetDatabase().Insert("insert into test(val) values(@p)", values);
        }
    }
}