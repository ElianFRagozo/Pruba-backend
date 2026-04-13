package com.prueba.franquicias.infrastructure.web.mapper;

import com.prueba.franquicias.application.dto.TopStockProduct;
import com.prueba.franquicias.domain.model.Branch;
import com.prueba.franquicias.domain.model.Franchise;
import com.prueba.franquicias.domain.model.Product;
import com.prueba.franquicias.infrastructure.web.dto.response.FranchiseResponse;
import com.prueba.franquicias.infrastructure.web.dto.response.TopStockProductResponse;
import org.springframework.stereotype.Component;

@Component
public class FranchiseResponseMapper {

    public FranchiseResponse toResponse(Franchise franchise) {
        return new FranchiseResponse(
                franchise.id(),
                franchise.name(),
                franchise.branches().stream().map(this::toBranchResponse).toList()
        );
    }

    public TopStockProductResponse toTopStockResponse(TopStockProduct item) {
        return new TopStockProductResponse(
                item.branchId(),
                item.branchName(),
                item.productId(),
                item.productName(),
                item.stock()
        );
    }

    private FranchiseResponse.BranchResponse toBranchResponse(Branch branch) {
        return new FranchiseResponse.BranchResponse(
                branch.id(),
                branch.name(),
                branch.products().stream().map(this::toProductResponse).toList()
        );
    }

    private FranchiseResponse.ProductResponse toProductResponse(Product product) {
        return new FranchiseResponse.ProductResponse(product.id(), product.name(), product.stock());
    }
}
