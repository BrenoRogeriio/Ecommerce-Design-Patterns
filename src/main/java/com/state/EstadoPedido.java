package com.state;

import com.model.Pedido;

public interface EstadoPedido {
    void pagar(Pedido pedido);
    void enviar(Pedido pedido);
    void cancelar(Pedido pedido);
}