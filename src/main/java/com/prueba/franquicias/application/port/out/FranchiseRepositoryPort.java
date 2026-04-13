package com.prueba.franquicias.application.port.out;

import com.prueba.franquicias.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String franchiseId);
}
