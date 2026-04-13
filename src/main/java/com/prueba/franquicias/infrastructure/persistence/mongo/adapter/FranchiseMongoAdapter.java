package com.prueba.franquicias.infrastructure.persistence.mongo.adapter;

import com.prueba.franquicias.application.port.out.FranchiseRepositoryPort;
import com.prueba.franquicias.domain.model.Franchise;
import com.prueba.franquicias.infrastructure.persistence.mongo.mapper.FranchiseDocumentMapper;
import com.prueba.franquicias.infrastructure.persistence.mongo.repository.SpringDataFranchiseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseMongoAdapter implements FranchiseRepositoryPort {

    private final SpringDataFranchiseRepository repository;
    private final FranchiseDocumentMapper mapper;

    public FranchiseMongoAdapter(SpringDataFranchiseRepository repository, FranchiseDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(mapper.toDocument(franchise))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String franchiseId) {
        return repository.findById(franchiseId)
                .map(mapper::toDomain);
    }
}
