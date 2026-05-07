package com.strategy;

public class FreteTerrestre implements EstrategiaFrete {
    @Override
    public double calcular(double valorPedido) {
        return valorPedido * 0.05;
    }
}