using System;
using System.IO;
using System.Net.Sockets;
using System.Text;

namespace JavaClient
{
    class Program
    {
        private static string HOST = "localhost";
        private static string addr = "127.0.0.1";
        private static int Port = 10000;
        private static string EOF = "\r\n.\r\n";

        static void Main(string[] args)
        {
            var client = new TcpClient(HOST, Port);
            var builder = new StringBuilder();
            var buffer = new byte[1024];
            using (var stream = client.GetStream())
                while (client.Connected)
                {
                    var input = Encoding.Default.GetBytes(Console.ReadLine() + EOF);
                    stream.Write(input, 0, input.Length);
                    while (!builder.ToString().Contains(EOF))
                    {
                        stream.Read(buffer, 0, buffer.Length);
                        var str = Encoding.UTF8.GetString(buffer);
                        var index = str.IndexOf(EOF);
                        builder.Append(index >= 0 ? str.Substring(0, index + EOF.Length) : str);
                    }
                    Console.WriteLine(builder.ToString());
                    builder = builder.Clear();
                }
        }
    }
}
