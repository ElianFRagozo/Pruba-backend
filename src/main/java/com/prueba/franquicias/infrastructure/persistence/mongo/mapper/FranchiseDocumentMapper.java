package com.prueba.franquicias.infrastructure.persistence.mongo.mapper;

import com.prueba.franquicias.domain.model.Branch;
import com.prueba.franquicias.domain.model.Franchise;
import com.prueba.franquicias.domain.model.Product;
import com.prueba.franquicias.infrastructure.persistence.mongo.document.FranchiseDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FranchiseDocumentMapper {

    public FranchiseDocument toDocument(Franchise franchise) {
        return new FranchiseDocument(
                franchise.id(),
                franchise.name(),
                franchise.branches().stream().map(this::toBranchDocument).toList()
        );
    }

    public Franchise toDomain(FranchiseDocument franchiseDocument) {
        return new Franchise(
                franchiseDocument.id(),
                franchiseDocument.name(),
                safe(franchiseDocument.branches()).stream().map(this::toBranch).toList()
        );
    }

    private FranchiseDocument.BranchDocument toBranchDocument(Branch branch) {
        return new FranchiseDocument.BranchDocument(
                branch.id(),
                branch.name(),
                branch.products().stream().map(this::toProductDocument).toList()
        );
    }

    private Branch toBranch(FranchiseDocument.BranchDocument branchDocument) {
        return new Branch(
                branchDocument.id(),
                branchDocument.name(),
                safe(branchDocument.products()).stream().map(this::toProduct).toList()
        );
    }

    private FranchiseDocument.ProductDocument toProductDocument(Product product) {
        return new FranchiseDocument.ProductDocument(product.id(), product.name(), product.stock());
    }

    private Product toProduct(FranchiseDocument.ProductDocument productDocument) {
        return new Product(productDocument.id(), productDocument.name(), productDocument.stock());
    }

    private <T> List<T> safe(List<T> values) {
        return values == null ? List.of() : values;
    }
}
