package pl.dkaluza.chatserver.adapters.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface MessageDocumentRepository extends ReactiveMongoRepository<MessageDocument, String> {
}
