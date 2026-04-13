package com.prueba.franquicias.application.port.in;

import com.prueba.franquicias.application.dto.TopStockProduct;
import com.prueba.franquicias.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseUseCasePort {
    Mono<Franchise> createFranchise(String franchiseName);
    Mono<Franchise> renameFranchise(String franchiseId, String newName);
    Mono<Franchise> addBranch(String franchiseId, String branchName);
    Mono<Franchise> renameBranch(String franchiseId, String branchId, String newName);
    Mono<Franchise> addProduct(String franchiseId, String branchId, String productName, int stock);
    Mono<Franchise> renameProduct(String franchiseId, String branchId, String productId, String newName);
    Mono<Franchise> updateProductStock(String franchiseId, String branchId, String productId, int stock);
    Mono<Franchise> deleteProduct(String franchiseId, String branchId, String productId);
    Flux<TopStockProduct> getTopStockProducts(String franchiseId);
}
