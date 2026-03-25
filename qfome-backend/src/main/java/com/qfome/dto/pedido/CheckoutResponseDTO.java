package com.qfome.dto.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CheckoutResponseDTO(
        Long id,
        String codigo,
        String status,
        String formaPagamento,
        LocalDateTime dataCriacao,
        BigDecimal total,
        Long clienteId
) {
}
