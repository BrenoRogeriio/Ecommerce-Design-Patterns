package com.state;

import com.model.Pedido;

public class EstadoCancelado implements EstadoPedido {

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Erro: Não é possível pagar um pedido cancelado.");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("Erro: Não é possível enviar um pedido cancelado.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Ação inválida: O pedido já está cancelado.");
    }
}