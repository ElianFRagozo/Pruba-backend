package com.prueba.franquicias.application.usecase;

import com.prueba.franquicias.application.dto.TopStockProduct;
import com.prueba.franquicias.application.exception.NotFoundException;
import com.prueba.franquicias.application.port.out.FranchiseRepositoryPort;
import com.prueba.franquicias.domain.model.Branch;
import com.prueba.franquicias.domain.model.Franchise;
import com.prueba.franquicias.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FranchiseUseCaseTest {

    private FranchiseRepositoryPort repositoryPort;
    private FranchiseUseCase useCase;

    @BeforeEach
    void setup() {
        repositoryPort = Mockito.mock(FranchiseRepositoryPort.class);
        useCase = new FranchiseUseCase(repositoryPort);
    }

    @Test
    void shouldCreateFranchise() {
        when(repositoryPort.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(useCase.createFranchise("Franquicia Centro"))
                .assertNext(franchise -> {
                    assertEquals("Franquicia Centro", franchise.name());
                    assertTrue(franchise.id() != null && !franchise.id().isBlank());
                })
                .verifyComplete();
    }

    @Test
    void shouldAddBranchToFranchise() {
        Franchise current = new Franchise("f-1", "Franquicia 1", List.of());
        when(repositoryPort.findById("f-1")).thenReturn(Mono.just(current));
        when(repositoryPort.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(useCase.addBranch("f-1", "Sucursal Norte"))
                .assertNext(updated -> {
                    assertEquals(1, updated.branches().size());
                    assertEquals("Sucursal Norte", updated.branches().get(0).name());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateProductStock() {
        Product product = new Product("p-1", "Producto 1", 5);
        Branch branch = new Branch("b-1", "Sucursal 1", List.of(product));
        Franchise current = new Franchise("f-1", "Franquicia 1", List.of(branch));

        when(repositoryPort.findById("f-1")).thenReturn(Mono.just(current));
        when(repositoryPort.save(any())).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(useCase.updateProductStock("f-1", "b-1", "p-1", 20))
                .assertNext(updated -> {
                    Product updatedProduct = updated.branches().get(0).products().get(0);
                    assertEquals(20, updatedProduct.stock());
                })
                .verifyComplete();
    }

    @Test
    void shouldFailWhenProductDoesNotExist() {
        Branch branch = new Branch("b-1", "Sucursal 1", List.of());
        Franchise current = new Franchise("f-1", "Franquicia 1", List.of(branch));

        when(repositoryPort.findById("f-1")).thenReturn(Mono.just(current));

        StepVerifier.create(useCase.deleteProduct("f-1", "b-1", "missing-product"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void shouldReturnTopStockProductsByBranch() {
        Branch b1 = new Branch("b-1", "Sucursal 1", List.of(
                new Product("p-1", "A", 10),
                new Product("p-2", "B", 50)
        ));
        Branch b2 = new Branch("b-2", "Sucursal 2", List.of(
                new Product("p-3", "C", 15)
        ));
        Franchise franchise = new Franchise("f-1", "Franquicia 1", List.of(b1, b2));

        when(repositoryPort.findById("f-1")).thenReturn(Mono.just(franchise));

        StepVerifier.create(useCase.getTopStockProducts("f-1"))
                .expectNextMatches(item -> item.branchId().equals("b-1") && item.productId().equals("p-2"))
                .expectNextMatches(item -> item.branchId().equals("b-2") && item.productId().equals("p-3"))
                .verifyComplete();
    }
}
