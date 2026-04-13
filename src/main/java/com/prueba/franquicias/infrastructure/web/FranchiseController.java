package com.prueba.franquicias.infrastructure.web;

import com.prueba.franquicias.application.port.in.FranchiseUseCasePort;
import com.prueba.franquicias.infrastructure.web.dto.request.CreateBranchRequest;
import com.prueba.franquicias.infrastructure.web.dto.request.CreateFranchiseRequest;
import com.prueba.franquicias.infrastructure.web.dto.request.CreateProductRequest;
import com.prueba.franquicias.infrastructure.web.dto.request.UpdateNameRequest;
import com.prueba.franquicias.infrastructure.web.dto.request.UpdateStockRequest;
import com.prueba.franquicias.infrastructure.web.dto.response.FranchiseResponse;
import com.prueba.franquicias.infrastructure.web.dto.response.TopStockProductResponse;
import com.prueba.franquicias.infrastructure.web.mapper.FranchiseResponseMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseUseCasePort useCase;
    private final FranchiseResponseMapper mapper;

    public FranchiseController(FranchiseUseCasePort useCase, FranchiseResponseMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return useCase.createFranchise(request.name()).map(mapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<FranchiseResponse> renameFranchise(
            @PathVariable String franchiseId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return useCase.renameFranchise(franchiseId, request.name()).map(mapper::toResponse);
    }

    @PostMapping("/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> addBranch(
            @PathVariable String franchiseId,
            @Valid @RequestBody CreateBranchRequest request
    ) {
        return useCase.addBranch(franchiseId, request.name()).map(mapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/name")
    public Mono<FranchiseResponse> renameBranch(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return useCase.renameBranch(franchiseId, branchId, request.name()).map(mapper::toResponse);
    }

    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> addProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return useCase.addProduct(franchiseId, branchId, request.name(), request.stock()).map(mapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/name")
    public Mono<FranchiseResponse> renameProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return useCase.renameProduct(franchiseId, branchId, productId, request.name()).map(mapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<FranchiseResponse> updateStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return useCase.updateProductStock(franchiseId, branchId, productId, request.stock()).map(mapper::toResponse);
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<FranchiseResponse> deleteProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId
    ) {
        return useCase.deleteProduct(franchiseId, branchId, productId).map(mapper::toResponse);
    }

    @GetMapping("/{franchiseId}/top-stock-products")
    public Flux<TopStockProductResponse> topStockProducts(@PathVariable String franchiseId) {
        return useCase.getTopStockProducts(franchiseId).map(mapper::toTopStockResponse);
    }
}
