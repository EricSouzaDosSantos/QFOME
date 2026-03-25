package com.qfome.service.pedido;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfome.dto.pedido.PedidoStatusResponse;
import com.qfome.model.Pedido;
import com.qfome.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoAcompanhamentoService {

    private final PedidoRepository pedidoRepository;

    public List<PedidoStatusResponse> buscarHistoricoPorCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);

        return pedidos.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public PedidoStatusResponse acompanharPedidoPorCodigo(String codigo) {
        Pedido pedido = pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado com o codigo: " + codigo));

        return converterParaResponse(pedido);
    }

    private PedidoStatusResponse converterParaResponse(Pedido pedido) {
        return PedidoStatusResponse.builder()
                .id(pedido.getId())
                .codigo(pedido.getCodigo())
                .status(pedido.getStatus().name())
                .total(pedido.getTotal())
                .dataCriacao(pedido.getDataCriacao())
                .clienteId(pedido.getCliente().getId())
                .build();
    }
}
