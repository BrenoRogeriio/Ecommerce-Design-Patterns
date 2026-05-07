package com.model;

import com.strategy.EstrategiaFrete;
import com.state.*;
import com.observer.PedidoObserver;
import com.adapter.ProcessadorPagamento;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valorPedido;
    private double valorFrete;
    private String status;

    @Transient
    private EstrategiaFrete tipoFrete;

    @Transient
    private EstadoPedido estado;

    @Transient
    private List<PedidoObserver> observers = new ArrayList<>();

    public Pedido() {
        this.estado = new EstadoAguardandoPagamento();
        this.status = "AGUARDANDO_PAGAMENTO";
    }

    @PostLoad
    private void restaurarEstado() {
        if (this.status == null) return;
        switch (this.status) {
            case "PAGO": this.estado = new EstadoPago(); break;
            case "ENVIADO": this.estado = new EstadoEnviado(); break;
            case "CANCELADO": this.estado = new EstadoCancelado(); break;
            default: this.estado = new EstadoAguardandoPagamento(); break;
        }
    }


    public void calcularFrete() {
        if (this.tipoFrete != null) {
            this.valorFrete = this.tipoFrete.calcular(this.valorPedido);
        }
    }


    public double getValorTotal() {
        return this.valorPedido + this.valorFrete;
    }


    public void confirmarPagamento(ProcessadorPagamento processador) {
        processador.processarPagamento(this.getValorTotal());
        this.pagar();
    }


    public void pagar() { this.estado.pagar(this); }
    public void enviar() { this.estado.enviar(this); }
    public void cancelar() { this.estado.cancelar(this); }


    public void adicionarObserver(PedidoObserver observer) { this.observers.add(observer); }
    public void notificarObservers() {
        if (observers != null) {
            for (PedidoObserver observer : observers) { observer.atualizar(this); }
        }
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
        this.status = estado.getClass().getSimpleName().replace("Estado", "").toUpperCase();
        this.notificarObservers();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getValorPedido() { return valorPedido; }
    public void setValorPedido(double valorPedido) { this.valorPedido = valorPedido; }
    public double getValorFrete() { return valorFrete; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setTipoFrete(EstrategiaFrete tipoFrete) { this.tipoFrete = tipoFrete; }
}