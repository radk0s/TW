package cache;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.Executors;

public class CacheTest {
    public static void main(String[] args) throws Exception {

        Cache<String,String> myCache = new Cache<String, String>(500, 2);

        myCache.setCache("ala","kot");
        Thread.sleep(600);
        myCache.setCache("ola", "pies");
        myCache.setCache("ula", "chomik");
        System.out.print(myCache);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            StringBuilder response =  new StringBuilder();


            URL url = new URL("http://docs.oracle.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            t.sendResponseHeaders(200, response.length());
            System.out.println(t.getHttpContext().getPath());
            OutputStream os = t.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
}
