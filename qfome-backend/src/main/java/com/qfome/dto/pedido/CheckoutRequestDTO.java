package com.qfome.dto.pedido;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequestDTO(
        @NotNull Long clienteId,
        @NotBlank String formaPagamento,
        @DecimalMin("0.0") BigDecimal total
) {
}
