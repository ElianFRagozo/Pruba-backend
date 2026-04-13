package com.prueba.franquicias.domain.model;

import com.prueba.franquicias.domain.exception.BusinessRuleException;

public record Product(String id, String name, int stock) {

    public Product {
        if (id == null || id.isBlank()) {
            throw new BusinessRuleException("El id del producto es obligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new BusinessRuleException("El nombre del producto es obligatorio");
        }
        if (stock < 0) {
            throw new BusinessRuleException("El stock del producto no puede ser negativo");
        }
    }

    public Product rename(String newName) {
        return new Product(this.id, newName, this.stock);
    }

    public Product updateStock(int newStock) {
        return new Product(this.id, this.name, newStock);
    }
}
