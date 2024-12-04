package pl.dkaluza.chatclient;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class MyWebSocketListener implements WebSocket.Listener {
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        WebSocket.Listener.super.onText(webSocket, data, last);
        System.out.println("Received message: " + data);
        return null;
    }
}
