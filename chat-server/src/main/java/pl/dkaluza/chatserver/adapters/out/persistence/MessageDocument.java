package pl.dkaluza.chatserver.adapters.out.persistence;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("messages")
record MessageDocument(String sender, String message, Instant timestamp) {
}
