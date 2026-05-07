package com.state;

import com.model.Pedido;

public class EstadoAguardandoPagamento implements EstadoPedido {

    @Override
    public void pagar(Pedido pedido) {
        System.out.println("Pagamento aprovado!");
        pedido.setEstado(new EstadoPago());
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new IllegalStateException("Erro: O pedido precisa ser pago antes de ser enviado.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Pedido cancelado pelo cliente.");
        pedido.setEstado(new EstadoCancelado());
    }
}