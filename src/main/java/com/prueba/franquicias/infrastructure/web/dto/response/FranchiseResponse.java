package com.prueba.franquicias.infrastructure.web.dto.response;

import java.util.List;

public record FranchiseResponse(
        String id,
        String name,
        List<BranchResponse> branches
) {
    public record BranchResponse(
            String id,
            String name,
            List<ProductResponse> products
    ) {
    }

    public record ProductResponse(
            String id,
            String name,
            int stock
    ) {
    }
}
