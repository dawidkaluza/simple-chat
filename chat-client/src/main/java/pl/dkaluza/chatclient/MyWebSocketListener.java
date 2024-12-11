package pl.dkaluza.chatclient;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class MyWebSocketListener implements WebSocket.Listener {
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("> " + data);
        webSocket.request(1);
        return null;
    }
}
