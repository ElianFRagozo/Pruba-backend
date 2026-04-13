package com.prueba.franquicias.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest(
        @NotBlank(message = "El nombre del producto es obligatorio")
        String name,
        @Min(value = 0, message = "El stock no puede ser negativo")
        int stock
) {
}
