package com.observer;

import com.model.Pedido;

public interface PedidoObserver {
    void atualizar(Pedido pedido);
}