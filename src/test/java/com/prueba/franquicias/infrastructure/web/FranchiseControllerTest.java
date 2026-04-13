package com.prueba.franquicias.infrastructure.web;

import com.prueba.franquicias.application.dto.TopStockProduct;
import com.prueba.franquicias.application.port.in.FranchiseUseCasePort;
import com.prueba.franquicias.domain.model.Franchise;
import com.prueba.franquicias.infrastructure.web.mapper.FranchiseResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseController.class)
@Import({FranchiseResponseMapper.class, GlobalExceptionHandler.class})
class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FranchiseUseCasePort useCase;

    @Test
    void shouldCreateFranchise() {
        Franchise franchise = new Franchise("f-1", "Franquicia 1", List.of());
        when(useCase.createFranchise("Franquicia 1")).thenReturn(Mono.just(franchise));

        webTestClient.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"name":"Franquicia 1"}
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("f-1")
                .jsonPath("$.name").isEqualTo("Franquicia 1");
    }

    @Test
    void shouldReturnTopStockProducts() {
        when(useCase.getTopStockProducts("f-1")).thenReturn(Flux.just(
                new TopStockProduct("b-1", "Sucursal 1", "p-1", "Producto 1", 100)
        ));

        webTestClient.get()
                .uri("/api/franchises/f-1/top-stock-products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].branchId").isEqualTo("b-1")
                .jsonPath("$[0].productName").isEqualTo("Producto 1")
                .jsonPath("$[0].stock").isEqualTo(100);
    }
}
