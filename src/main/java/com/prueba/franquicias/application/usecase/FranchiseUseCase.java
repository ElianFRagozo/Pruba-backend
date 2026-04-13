package com.prueba.franquicias.application.usecase;

import com.prueba.franquicias.application.dto.TopStockProduct;
import com.prueba.franquicias.application.exception.NotFoundException;
import com.prueba.franquicias.application.port.in.FranchiseUseCasePort;
import com.prueba.franquicias.application.port.out.FranchiseRepositoryPort;
import com.prueba.franquicias.domain.model.Branch;
import com.prueba.franquicias.domain.model.Franchise;
import com.prueba.franquicias.domain.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class FranchiseUseCase implements FranchiseUseCasePort {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public FranchiseUseCase(FranchiseRepositoryPort franchiseRepositoryPort) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
    }

    @Override
    public Mono<Franchise> createFranchise(String franchiseName) {
        Franchise franchise = new Franchise(UUID.randomUUID().toString(), franchiseName, null);
        return franchiseRepositoryPort.save(franchise);
    }

    @Override
    public Mono<Franchise> renameFranchise(String franchiseId, String newName) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> franchise.rename(newName))
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Mono<Franchise> addBranch(String franchiseId, String branchName) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> franchise.addBranch(new Branch(UUID.randomUUID().toString(), branchName, null)))
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Mono<Franchise> renameBranch(String franchiseId, String branchId, String newName) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> {
                    Branch branch = franchise.getBranchOrThrow(branchId);
                    return franchise.updateBranch(branch.rename(newName));
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Mono<Franchise> addProduct(String franchiseId, String branchId, String productName, int stock) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> {
                    Branch branch = franchise.getBranchOrThrow(branchId);
                    Product newProduct = new Product(UUID.randomUUID().toString(), productName, stock);
                    return franchise.updateBranch(branch.addProduct(newProduct));
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Mono<Franchise> renameProduct(String franchiseId, String branchId, String productId, String newName) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> {
                    Branch branch = franchise.getBranchOrThrow(branchId);
                    validateProductExists(branch, productId);
                    return franchise.updateBranch(branch.renameProduct(productId, newName));
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Mono<Franchise> updateProductStock(String franchiseId, String branchId, String productId, int stock) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> {
                    Branch branch = franchise.getBranchOrThrow(branchId);
                    validateProductExists(branch, productId);
                    return franchise.updateBranch(branch.updateProductStock(productId, stock));
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Mono<Franchise> deleteProduct(String franchiseId, String branchId, String productId) {
        return findFranchiseOrFail(franchiseId)
                .map(franchise -> {
                    Branch branch = franchise.getBranchOrThrow(branchId);
                    validateProductExists(branch, productId);
                    return franchise.updateBranch(branch.deleteProduct(productId));
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    @Override
    public Flux<TopStockProduct> getTopStockProducts(String franchiseId) {
        return findFranchiseOrFail(franchiseId)
                .flatMapMany(franchise -> Flux.fromIterable(franchise.topStockProducts()));
    }

    private Mono<Franchise> findFranchiseOrFail(String franchiseId) {
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")));
    }

    private void validateProductExists(Branch branch, String productId) {
        if (!branch.hasProduct(productId)) {
            throw new NotFoundException("Producto no encontrado en la sucursal");
        }
    }
}
