package com.adapter;

public class PayPalAdapter extends ProcessadorPagamento {
    private ServicoPayPal payPal = new ServicoPayPal();

    @Override
    protected void executarCobranca(double valor) {
        payPal.fazerPagamentoRapido(valor);
    }
}