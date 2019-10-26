﻿using Npgsql;
using System;

namespace Server.Utils
{
    public class Database
    {
        public static Database DB = new Database("localhost", 5432, "cmov", "cmov", "cmovdb");

        private string hostname;
        private int port = 5432;
        private string username;
        private string password;
        private string database;

        private NpgsqlConnection connection;

        public static Database GetDatabase()
        {
            return DB;
        }

        /// <summary>
        /// Database wrapper class constructor
        /// </summary>
        /// <param name="hostname">hostname of the database server</param>
        /// <param name="port">port the database server is running on</param>
        /// <param name="username">username to authenticate into the database server with</param>
        /// <param name="password">password to authenticate into the dtabase server with</param>
        /// <param name="database">database to connect to on the database server</param>
        public Database(string hostname, int port, string username, string password, string database)
        {
            this.hostname = hostname;
            this.port = port;
            this.username = username;
            this.password = password;
            this.database = database;
            this.ConnectToDatabase();
        }

        /// <summary>
        /// Database wrapper class constructor
        /// </summary>
        /// <param name="hostname">hostname of the database server</param>
        /// <param name="username">username to authenticate into the database server with</param>
        /// <param name="password">password to authenticate into the dtabase server with</param>
        /// <param name="database">database to connect to on the database server</param>
        public Database(string hostname, string username, string password, string database)
        {
            this.hostname = hostname;
            this.username = username;
            this.password = password;
            this.database = database;
            this.ConnectToDatabase();
        }

        /// <summary>
        /// Creates a Npgsql style database connection string
        /// </summary>
        /// <returns>connection string</returns>
        private string CreateConnectionString()
        {
            string connString = "";
            connString += "Host=" + this.hostname + ";";
            connString += "Port=" + this.port + ";";
            connString += "Database=" + this.database + ";";
            connString += "Username=" + this.username + ";";
            connString += "Password=" + this.password + ";";
            return connString;
        }

        /// <summary>
        /// Connects to the database server with the specified parameters
        /// </summary>
        private void ConnectToDatabase()
        {
            string connectionString = this.CreateConnectionString();
            this.connection = new NpgsqlConnection(connectionString);
            try
            {
                this.connection.Open();
            } catch (Exception e)
            {
                Logger.LogError("Failed to connect to database, reason:" + e.Message, "Database");
            }
            this.ValidateConnection();
        }

        /// <summary>
        /// Validates wether or not the connection to the database was completed with
        /// success
        /// </summary>
        private void ValidateConnection()
        {
            if (this.connection.State == System.Data.ConnectionState.Open)
            {
                Logger.LogSuccess("connected to database", "database");
            }
            else
            {
                Logger.LogError("failed to connect to database", "database");
                throw new System.Exception("Failed To Connect To Database");
            }
        }

        /// <summary>
        /// Closes the connection to the database server
        /// </summary>
        public void CloseConnection()
        {
            this.connection.Close();
        }

    }
}
