package cache;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;


public class CacheTest {

    static Cache<String,String> myCache = new Cache<String, String>(3000, 2);

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        for(int i = 0; i< 10; i++){
            Thread td = new Thread(){
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        URL url = new URL("http://localhost:8000/test?"+(int)(Math.random()*100)%2);
                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                        }
                        in.close();
                        long stop= System.currentTimeMillis();
                        System.out.println("Request time: " + (stop - start));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            td.start();
           // if (i%3 == 0)
                Thread.sleep(500);
        }
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {


            String result = myCache.getCache(t.getRequestURI().toString());

            if(result == null){
                StringBuilder response =  new StringBuilder();
                URL url = new URL("http://docs.oracle.com/");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                result = response.toString();
                myCache.setCache(t.getRequestURI().toString(),result);
            }


            t.sendResponseHeaders(200, result.length());
            System.out.println(t.getRequestURI());
            OutputStream os = t.getResponseBody();
            os.write(result.getBytes());
            os.close();
        }
    }
}
