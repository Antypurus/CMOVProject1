using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
using Server.Models;
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

        [HttpPost("register")]
        public Guid register([FromBody]JObject data)
        {
            string name = data["name"].ToString();
            string username = data["username"].ToString();
            string password = data["password"].ToString();
            int credit = data["credit_card_no"].ToObject<int>();
            string key = data["public_key"].ToString();
            return Client.RegisterUser(name,username,password,key,credit).GetClientID();
        }
    }
}