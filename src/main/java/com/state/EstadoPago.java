package com.state;

import com.model.Pedido;

public class EstadoPago implements EstadoPedido {

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Erro: O pedido já foi pago.");
    }

    @Override
    public void enviar(Pedido pedido) {
        System.out.println("Pedido despachado para a transportadora!");
        pedido.setEstado(new EstadoEnviado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Erro: Um pedido pago já está em processamento logístico e não pode ser cancelado.");
    }
}