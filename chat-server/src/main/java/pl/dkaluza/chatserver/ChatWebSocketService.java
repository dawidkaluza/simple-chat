package pl.dkaluza.chatserver;

import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.function.Supplier;

public class ChatWebSocketService extends HandshakeWebSocketService {
    public ChatWebSocketService() {
        super(initRequestUpgradeStrategy());
    }

    private static RequestUpgradeStrategy initRequestUpgradeStrategy() {
        var nettyRequestUpgradeStrategy = new ReactorNettyRequestUpgradeStrategy();
        return (exchange, wsHandler, subprotocol, handshakeInfoFactory) -> {
            Supplier<HandshakeInfo> handshakeInfoWithNicknameFactory = () -> {
                var handshakeInfo = handshakeInfoFactory.get();
                var attributes = new HashMap<>(handshakeInfo.getAttributes());
                attributes.put("Nickname", retrieveNickname(exchange));
                return new HandshakeInfo(
                        handshakeInfo.getUri(), handshakeInfo.getHeaders(), handshakeInfo.getCookies(),
                        handshakeInfo.getPrincipal(), handshakeInfo.getSubProtocol(), handshakeInfo.getRemoteAddress(),
                        attributes, handshakeInfo.getLogPrefix()
                );
            };
            return nettyRequestUpgradeStrategy.upgrade(exchange, wsHandler, subprotocol, handshakeInfoWithNicknameFactory);
        };
    }

    private static String retrieveNickname(ServerWebExchange exchange) {
        var headers = exchange.getRequest().getHeaders();
        var nicknameHeader = headers.get("X-Nickname");
        if (nicknameHeader == null || nicknameHeader.size() > 1) {
            return "Anonymous";
        }

        return nicknameHeader.getFirst();
    }
}
