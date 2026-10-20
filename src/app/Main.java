package app;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/** Minimal dependency-free service used to exercise the CI/CD platform. */
public class Main {
    static final String VERSION = System.getenv().getOrDefault("APP_VERSION", "dev");

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/health", ex -> reply(ex, 200, "{\"status\":\"UP\"}"));
        server.createContext("/", ex -> reply(ex, 200,
                "{\"message\":\"" + greeting("AIO") + "\",\"version\":\"" + VERSION + "\",\"time\":\"" + Instant.now() + "\"}"));
        server.start();
        System.out.println("sample-app listening on :" + port);
    }

    static String greeting(String name) {
        return "Hello from " + name + " CI/CD";
    }

    private static void reply(com.sun.net.httpserver.HttpExchange ex, int code, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", "application/json");
        ex.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}
