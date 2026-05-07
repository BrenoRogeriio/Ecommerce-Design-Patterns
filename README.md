# 🛒 E-commerce Design Patterns API

Este projeto é uma API REST desenvolvida em **Spring Boot** para gerenciar o ciclo de vida de pedidos em um e-commerce.
O diferencial desta implementação é o uso estratégico de **Design Patterns (Padrões de Projeto)** do GoF para garantir um código limpo, 
extensível e de fácil manutenção.

---

## 🛠️ Design Patterns Utilizados

### 1. Strategy (Cálculo de Frete)
Utilizado para encapsular diferentes algoritmos de cálculo de logística.
- **Problema:** Evitar múltiplos `if/else` ou `switch` dentro da classe de Pedido toda vez que um novo frete for adicionado.
- **Solução:** O `Pedido` recebe uma interface `EstrategiaFrete`. Podemos trocar entre `FreteAereo` e `FreteTerrestre` em tempo de execução.

### 2. State (Estados do Pedido)
Gerencia o comportamento do pedido conforme seu status muda.
- **Problema:** Impedir ações inválidas (ex: cancelar um pedido que já foi enviado).
- **Solução:** Cada estado (`AguardandoPagamento`, `Pago`, `Enviado`) é uma classe que decide quais métodos podem ser executados, blindando a regra de negócio.

### 3. Observer (Sistema de Notificações)
Notifica interessados sobre mudanças de estado sem acoplamento.
- **Problema:** O Pedido não deve saber "como" enviar um e-mail ou SMS; ele apenas deve avisar que mudou.
- **Solução:** O `EmailObserver` se inscreve no `Pedido` e reage automaticamente a cada atualização de status.

### 4. Adapter & Template Method (Pagamento)
- **Adapter:** Adapta a interface do `PayPal` para a nossa interface interna `ProcessadorPagamento`.
- **Template Method:** Define a ordem obrigatória de execução do pagamento (validar -> cobrar -> log), permitindo que subclasses customizem apenas os passos necessários.

---

## 🚀 Endpoints Principais

### Pedidos
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/pedidos` | Cria um pedido (Body: `valorPedido`, `tipoFrete`) |
| `GET` | `/api/pedidos` | Lista todos os pedidos |
| `PUT` | `/api/pedidos/{id}` | Altera valor ou troca a estratégia de frete |
| `DELETE` | `/api/pedidos/{id}` | Remove um pedido |

### Fluxo de Estado (Máquina de Estados)
| Método | Endpoint | Ação do State Pattern |
|---|---|---|
| `PUT` | `/api/pedidos/{id}/pagar` | Valida pagamento e muda para **PAGO** |
| `PUT` | `/api/pedidos/{id}/enviar` | Despacha se estiver pago. Muda para **ENVIADO** |
| `PUT` | `/api/pedidos/{id}/cancelar` | Cancela se não tiver sido enviado. Muda para **CANCELADO** |

---

