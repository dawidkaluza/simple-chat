package pl.dkaluza.chatserver.domain;

import pl.dkaluza.chatserver.domain.exceptions.ValidationException;

import java.time.Instant;
import java.util.Objects;

public class Message {
    private final String sender;
    private final String message;
    private final Instant sentAt;

    private Message(String sender, String message, Instant sentAt) {
        this.sender = sender;
        this.message = message;
        this.sentAt = sentAt;
    }

    public static Message of(String sender, String message, Instant sentAt) throws ValidationException {
        var errorBuilder = new StringBuilder();
        if (sender == null || sender.isBlank()) {
            errorBuilder.append("Sender is empty. ");
        }

        if (message == null || message.isBlank()) {
            errorBuilder.append("Message is empty. ");
        }

        if (sentAt == null) {
            errorBuilder.append("Sent at is null.");
        }

        if (!errorBuilder.isEmpty()) {
            throw new ValidationException(errorBuilder.toString());
        }

        return new Message(sender, message, sentAt);
    }

    public String sender() {
        return sender;
    }

    public String message() {
        return message;
    }

    public Instant sentAt() {
        return sentAt;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Message message1)) return false;

        return Objects.equals(sender, message1.sender) && Objects.equals(message, message1.message) && Objects.equals(sentAt, message1.sentAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sender);
        result = 31 * result + Objects.hashCode(message);
        result = 31 * result + Objects.hashCode(sentAt);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
               "sender='" + sender + '\'' +
               ", message='" + message + '\'' +
               ", sentAt=" + sentAt +
               '}';
    }
}
