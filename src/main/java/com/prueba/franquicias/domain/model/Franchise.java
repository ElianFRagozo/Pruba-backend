package com.prueba.franquicias.domain.model;

import com.prueba.franquicias.application.dto.TopStockProduct;
import com.prueba.franquicias.domain.exception.BusinessRuleException;
import com.prueba.franquicias.domain.exception.DomainNotFoundException;

import java.util.ArrayList;
import java.util.List;

public record Franchise(String id, String name, List<Branch> branches) {

    public Franchise {
        if (id == null || id.isBlank()) {
            throw new BusinessRuleException("El id de la franquicia es obligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new BusinessRuleException("El nombre de la franquicia es obligatorio");
        }
        branches = branches == null ? List.of() : List.copyOf(branches);
    }

    public Franchise rename(String newName) {
        return new Franchise(this.id, newName, this.branches);
    }

    public Franchise addBranch(Branch branch) {
        List<Branch> updated = new ArrayList<>(this.branches);
        updated.add(branch);
        return new Franchise(this.id, this.name, updated);
    }

    public Franchise updateBranch(Branch branchToUpdate) {
        return new Franchise(this.id, this.name, this.branches.stream()
                .map(branch -> branch.id().equals(branchToUpdate.id()) ? branchToUpdate : branch)
                .toList());
    }

    public boolean hasBranch(String branchId) {
        return branches.stream().anyMatch(branch -> branch.id().equals(branchId));
    }

    public Branch getBranchOrThrow(String branchId) {
        return branches.stream()
                .filter(branch -> branch.id().equals(branchId))
                .findFirst()
                .orElseThrow(() -> new DomainNotFoundException("Sucursal no encontrada"));
    }

    public List<TopStockProduct> topStockProducts() {
        return branches.stream()
                .map(branch -> branch.topStockProduct()
                        .map(product -> new TopStockProduct(
                                branch.id(),
                                branch.name(),
                                product.id(),
                                product.name(),
                                product.stock()
                        ))
                        .orElse(null))
                .filter(item -> item != null)
                .toList();
    }
}
