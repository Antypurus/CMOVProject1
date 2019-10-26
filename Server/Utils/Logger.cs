using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Utils
{
    public class Logger
    {
        public static void Log(string message)
        {
            Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine(message);
        }

        public static void LogError(string message,string module)
        {
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine("[ERROR][" + Timestamp.Now() + "]@" + module + ":" + message);
            Console.ForegroundColor = ConsoleColor.White;
        }

        public static void LogWarning(string message, string module)
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("[WARNING][" + Timestamp.Now() + "]@" + module + ":" + message);
            Console.ForegroundColor = ConsoleColor.White;
        }

        public static void LogSuccess(string message, string module)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("[SUCCESS][" + Timestamp.Now() + "]@" + module + ":" + message);
            Console.ForegroundColor = ConsoleColor.White;
        }

        public static void LogInfo(string message, string module)
        {
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine("[INFO][" + Timestamp.Now() + "]@" + module + ":" + message);
            Console.ForegroundColor = ConsoleColor.White;
        }

    }
}
