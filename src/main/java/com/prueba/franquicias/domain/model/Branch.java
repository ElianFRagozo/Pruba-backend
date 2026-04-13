package com.prueba.franquicias.domain.model;

import com.prueba.franquicias.domain.exception.BusinessRuleException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record Branch(String id, String name, List<Product> products) {

    public Branch {
        if (id == null || id.isBlank()) {
            throw new BusinessRuleException("El id de la sucursal es obligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new BusinessRuleException("El nombre de la sucursal es obligatorio");
        }
        products = products == null ? List.of() : List.copyOf(products);
    }

    public Branch rename(String newName) {
        return new Branch(this.id, newName, this.products);
    }

    public Branch addProduct(Product product) {
        List<Product> updated = new ArrayList<>(this.products);
        updated.add(product);
        return new Branch(this.id, this.name, updated);
    }

    public Branch renameProduct(String productId, String newName) {
        return new Branch(this.id, this.name, this.products.stream()
                .map(product -> product.id().equals(productId) ? product.rename(newName) : product)
                .toList());
    }

    public Branch updateProductStock(String productId, int stock) {
        return new Branch(this.id, this.name, this.products.stream()
                .map(product -> product.id().equals(productId) ? product.updateStock(stock) : product)
                .toList());
    }

    public Branch deleteProduct(String productId) {
        return new Branch(this.id, this.name, this.products.stream()
                .filter(product -> !product.id().equals(productId))
                .toList());
    }

    public boolean hasProduct(String productId) {
        return products.stream().anyMatch(product -> product.id().equals(productId));
    }

    public Optional<Product> topStockProduct() {
        return products.stream()
                .max(Comparator.comparingInt(Product::stock));
    }
}
