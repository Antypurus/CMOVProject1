using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
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

        [HttpPost("test")]
        public string test([FromBody]JObject data)
        {
            string name = data["name"].ToObject<string>();
            string job = data["job"].ToString();
            return name+job;
        }
    }
}