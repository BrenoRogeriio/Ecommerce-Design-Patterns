package com.adapter;

public abstract class ProcessadorPagamento {

    public final void processarPagamento(double valor) {
        validarValor(valor);
        executarCobranca(valor);
        gerarComprovativo();
    }

    private void validarValor(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor inválido!");
        System.out.println("Validando valor: R$ " + valor);
    }

    protected abstract void executarCobranca(double valor);

    private void gerarComprovativo() {
        System.out.println("Comprovativo gerado com sucesso.");
    }
}