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
        public string insert_test()
        {
            string encrypted = RSAEncrypter.GetRSAEncrypter().Encrypt("data");
            string decrypted = RSAEncrypter.GetRSAEncrypter().Decrypt(encrypted);
            return "Encrypted="+encrypted+"\n\nDecrypted="+decrypted;
        }
    }
}