package com.state;

import com.model.Pedido;

public class EstadoEnviado implements EstadoPedido {

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Ação inválida: Pedido já foi enviado.");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("Ação inválida: Pedido já foi enviado.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Erro: Pedidos já enviados não podem ser cancelados.");
    }
}