package com.controller;

import com.model.Pedido;
import com.repository.PedidoRepository;
import com.strategy.*;
import com.observer.EmailObserver;
import com.adapter.PayPalAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @PostMapping
    public Pedido criar(@RequestBody Map<String, Object> payload) {
        Pedido pedido = new Pedido();
        pedido.setValorPedido(Double.parseDouble(payload.get("valorPedido").toString()));

        String tipo = payload.get("tipoFrete").toString();
        pedido.setTipoFrete(tipo.equalsIgnoreCase("aereo") ? new FreteAereo() : new FreteTerrestre());

        pedido.adicionarObserver(new EmailObserver());
        pedido.calcularFrete();
        return repository.save(pedido);
    }

    @GetMapping
    public List<Pedido> listar() {
        return repository.findAll();
    }


    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        return repository.findById(id).map(pedido -> {
            if (payload.containsKey("valorPedido")) {
                pedido.setValorPedido(Double.parseDouble(payload.get("valorPedido").toString()));
            }

            if (payload.containsKey("tipoFrete")) {
                String tipo = payload.get("tipoFrete").toString();
                pedido.setTipoFrete(tipo.equalsIgnoreCase("aereo") ? new FreteAereo() : new FreteTerrestre());
            }

            pedido.adicionarObserver(new EmailObserver());
            pedido.calcularFrete();
            return repository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }


    @PutMapping("/{id}/pagar")
    public Pedido pagar(@PathVariable Long id) {
        return repository.findById(id).map(pedido -> {
            pedido.adicionarObserver(new EmailObserver());
            pedido.confirmarPagamento(new PayPalAdapter());
            return repository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }


    @PutMapping("/{id}/enviar")
    public Pedido enviar(@PathVariable Long id) {
        return repository.findById(id).map(pedido -> {
            pedido.adicionarObserver(new EmailObserver());
            pedido.enviar();
            return repository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }


    @PutMapping("/{id}/cancelar")
    public Pedido cancelar(@PathVariable Long id) {
        return repository.findById(id).map(pedido -> {
            pedido.adicionarObserver(new EmailObserver());
            pedido.cancelar();
            return repository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}