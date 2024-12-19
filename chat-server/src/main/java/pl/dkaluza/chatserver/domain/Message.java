package pl.dkaluza.chatserver.domain;

import pl.dkaluza.chatserver.domain.exceptions.ValidationException;

public class Message {
    private final String sender;
    private final String message;

    private Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public static Message of(String sender, String message) throws ValidationException {
        var errorBuilder = new StringBuilder();
        if (sender == null || sender.isBlank()) {
            errorBuilder.append("Sender is empty. ");
        }

        if (message == null || message.isBlank()) {
            errorBuilder.append("Message is empty. ");
        }

        if (!errorBuilder.isEmpty()) {
            throw new ValidationException(errorBuilder.toString());
        }

        return new Message(sender, message);
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Message message1)) return false;
        return sender.equals(message1.sender) && message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
               "sender='" + sender + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
