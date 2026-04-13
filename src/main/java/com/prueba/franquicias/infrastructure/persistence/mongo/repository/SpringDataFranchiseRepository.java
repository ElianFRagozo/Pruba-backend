package com.prueba.franquicias.infrastructure.persistence.mongo.repository;

import com.prueba.franquicias.infrastructure.persistence.mongo.document.FranchiseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataFranchiseRepository extends ReactiveMongoRepository<FranchiseDocument, String> {
}
