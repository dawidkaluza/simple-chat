package pl.dkaluza.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.ExecutionException;

public class Application {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        try (var client = HttpClient.newHttpClient()) {
            var wsFuture = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8080/chat"), new MyWebSocketListener());
            var ws = wsFuture.get();
            System.out.println("Connection established!");

            while (true) {
                var reader = new BufferedReader(new InputStreamReader(System.in));
                var message = reader.readLine();
                ws.sendText(message, true);
            }
        }
    }
}
