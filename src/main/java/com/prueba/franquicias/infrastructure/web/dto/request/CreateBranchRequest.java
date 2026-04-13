package com.prueba.franquicias.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateBranchRequest(
        @NotBlank(message = "El nombre de la sucursal es obligatorio")
        String name
) {
}
