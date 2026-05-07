package com.strategy;

public class FreteAereo implements EstrategiaFrete {
    @Override
    public double calcular(double valorPedido) {
        return valorPedido * 0.10;
    }
}