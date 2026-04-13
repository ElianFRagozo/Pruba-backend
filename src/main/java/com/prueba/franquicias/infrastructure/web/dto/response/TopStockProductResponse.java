package com.prueba.franquicias.infrastructure.web.dto.response;

public record TopStockProductResponse(
        String branchId,
        String branchName,
        String productId,
        String productName,
        int stock
) {
}
