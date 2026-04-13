package com.prueba.franquicias.infrastructure.persistence.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "franchises")
public record FranchiseDocument(
        @Id String id,
        String name,
        List<BranchDocument> branches
) {
    public record BranchDocument(
            String id,
            String name,
            List<ProductDocument> products
    ) {
    }

    public record ProductDocument(
            String id,
            String name,
            int stock
    ) {
    }
}
