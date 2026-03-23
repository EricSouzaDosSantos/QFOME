package com.qfome.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @PostMapping("/itens")
    public String adicionarItem(@RequestBody Map<String, Object> item){
        return "Item recebido: " + item.toString();
    }

    @PatchMapping("/itens/{id}")
    public String alterarItem(@PathVariable Long id){
        return "Alterar item " + id;
    }

    @DeleteMapping("/itens/{id}")
    public String removerItem(@PathVariable Long id){
        return "Remover item " + id;
    }

    @GetMapping("/{clienteId}")
    public String listarCarrinho(@PathVariable Long clienteId){
        return "Listar carrinho do cliente " + clienteId;
    }   

}