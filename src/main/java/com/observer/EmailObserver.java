package com.observer;

import com.model.Pedido;

public class EmailObserver implements PedidoObserver {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[EMAIL ENVIADO] Atenção cliente, seu pedido mudou para o status: " + pedido.getStatus());
    }
}