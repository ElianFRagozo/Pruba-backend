package com.prueba.franquicias.application.dto;

public record TopStockProduct(
        String branchId,
        String branchName,
        String productId,
        String productName,
        int stock
) {
}
