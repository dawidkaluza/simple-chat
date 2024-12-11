package pl.dkaluza.chatserver;

import io.netty.util.internal.ConcurrentSet;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class ChatWebSocketEventsBroker {
    private final Set<Consumer<String>> eventsListeners;
    private final Flux<String> eventsPublisher;

    public ChatWebSocketEventsBroker() {
        eventsListeners = ConcurrentHashMap.newKeySet();
        eventsPublisher = Flux.push(emitter -> {
            Consumer<String> listener = emitter::next;
            eventsListeners.add(listener);
            emitter.onDispose(() -> eventsListeners.remove(listener));
        });
    }

    public void push(String event) {
        for (var listener : eventsListeners) {
            listener.accept(event);
        }
    }

    public Flux<String> pull() {
        return eventsPublisher;
    }
}
