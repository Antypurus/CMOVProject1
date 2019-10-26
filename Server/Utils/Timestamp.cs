using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Utils
{
    public class Timestamp
    {
        DateTime time;

        public Timestamp(DateTime time)
        {
            this.time = time;
        }

        public static Timestamp Now()
        {
            DateTime now = DateTime.Now;
            return new Timestamp(now);
        }

        override public string ToString()
        {
            return time.ToString("dddd dd/MM/yyyy HH:mm:ss");
        }
    }
}
