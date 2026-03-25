package com.qfome.service.pedido;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.qfome.dto.pedido.CheckoutRequestDTO;
import com.qfome.dto.pedido.CheckoutResponseDTO;
import com.qfome.model.Cliente;
import com.qfome.model.Pedido;
import com.qfome.model.enums.FormaPagamento;
import com.qfome.model.enums.StatusPedido;
import com.qfome.repository.ClienteRepository;
import com.qfome.repository.PedidoRepository;

@Service
public class CheckoutService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public CheckoutService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    public CheckoutResponseDTO fecharPedido(CheckoutRequestDTO request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

        Pedido pedido = Pedido.builder()
                .codigo(gerarCodigoPedido())
                .cliente(cliente)
                .formaPagamento(mapearFormaPagamento(request.formaPagamento()))
                .status(StatusPedido.RECEBIDO)
                .total(request.total() != null ? request.total() : BigDecimal.ZERO)
                .build();

        Pedido salvo = pedidoRepository.save(pedido);

        return new CheckoutResponseDTO(
                salvo.getId(),
                salvo.getCodigo(),
                salvo.getStatus().name().toLowerCase(),
                salvo.getFormaPagamento().name().toLowerCase(),
                salvo.getDataCriacao(),
                salvo.getTotal(),
                salvo.getCliente().getId()
        );
    }

    private FormaPagamento mapearFormaPagamento(String formaPagamento) {
        String valor = formaPagamento.trim().toLowerCase();
        return switch (valor) {
            case "pix" -> FormaPagamento.PIX;
            case "cartao" -> FormaPagamento.CARTAO;
            case "dinheiro" -> FormaPagamento.DINHEIRO;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento invalida");
        };
    }

    private String gerarCodigoPedido() {
        // Gera codigo QF-123456 unico
        for (int tentativa = 0; tentativa < 30; tentativa++) {
            int numero = ThreadLocalRandom.current().nextInt(100000, 1000000);
            String codigo = "QF-" + numero;
            if (pedidoRepository.findByCodigo(codigo).isEmpty()) {
                return codigo;
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nao foi possivel gerar codigo do pedido");
    }
}
