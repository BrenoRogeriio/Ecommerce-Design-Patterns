# 🛒 E-commerce API (Design Patterns na Prática)

Este projeto é uma API REST desenvolvida em **Spring Boot** para gerenciar pedidos de um e-commerce. A ideia principal aqui foi ir além do CRUD básico e aplicar padrões de projeto (**Design Patterns) para resolver problemas reais de lógica de negócio, como cálculo de frete, controle de status e notificações.

Com essa arquitetura, o código ficou totalmente desacoplado, fácil de testar e pronto para crescer sem virar um "monstro" cheio de `if/else`.

---

## 🏗️ Como os Padrões Resolveram o Nosso Problema

### 1. Strategy (Cálculo de Frete)
- **O Problema:** Se usasse `if (tipo == "aereo")` dentro da classe `Pedido`, o código quebraria o princípio de responsabilidade única. Cada vez que a empresa criasse um frete novo , teríamos que mexer e arriscar quebrar o código que já funciona.
- **A Solução:** criar a interface `EstrategiaFrete`. Agora, as classes `FreteAereo` (10%) e `FreteTerrestre` (5%) cuidam da sua própria matemática. O pedido só manda calcular, sem querer saber como é feito por baixo dos panos.

### 2. State (A Máquina de Estados do Pedido)
- **O Problema:** Controlar o fluxo do pedido (Aguardando Pagamento -> Pago -> Enviado) usando variáveis comuns pode gerar bugs, como um usuário conseguir cancelar um produto que já saiu para a entrega.
- **A Solução:** Cada status do pedido virou uma classe separada (`EstadoAguardandoPagamento`, `EstadoPago`, etc.). Se você tentar forçar uma ação inválida, o próprio estado barra a requisição e joga uma exceção. O banco de dados fica 100% protegido contra estados impossíveis.

### 3. Observer (Notificações Sem Acoplamento)
- **O Problema:** Quando o pedido é pago ou enviado, precisamos avisar o cliente (por e-mail, SMS, etc.). Colocar a lógica de envio de e-mail dentro da classe `Pedido` iria dar um nó no código.
- **A Solução:** O pedido avisa que mudou de estado, e quem estiver "escutando" (como `EmailObserver`) faz o seu trabalho de forma independente. Se amanhã quisermos colocar avisos por WhatsApp, é só criar um novo Observer e plugar no sistema.

### 4. Adapter & Template Method (Gateway de Pagamento)
- **Template Method:** A classe abstrata `ProcessadorPagamento` dita a regra rígida do que é um pagamento seguro (validar dados -> rodar cobrança -> registrar log). 
- **Adapter:** Como a API do PayPal usa métodos com nomes totalmente diferentes do nosso sistema, o `PayPalAdapter` faz o papel de "tradutor". Ele envelopa o `ServicoPayPal` para que o nosso sistema converse com ele sem precisar mudar nossos próprios métodos. Se mudarmos para a Stripe amanhã, o impacto no código é zero.

---

## 🚦 Endpoints para Testar no Postman

### Gerenciamento Principal
* `POST /api/pedidos` - Cria um pedido e já calcula o frete inicial.
    * *Exemplo de JSON:* `{ "valorPedido": 1000.0, "tipoFrete": "aereo" }`
* `GET /api/pedidos` - Lista todos os pedidos direto do banco H2.
* `PUT /api/pedidos/{id}` - Altera o valor ou muda o frete (o *Strategy* recalcula tudo na hora).
* `DELETE /api/pedidos/{id}` - Deleta o pedido.

### Gatilhos do State Pattern (Ações do Pedido)
* `PUT /api/pedidos/{id}/pagar` - Aprova o pagamento via *Adapter* e joga o status para `PAGO`.
* `PUT /api/pedidos/{id}/enviar` - Tenta despachar. (Só funciona se o status anterior for `PAGO`).
* `PUT /api/pedidos/{id}/cancelar` - Cancela o pedido (O sistema vai barrar se o status já for `ENVIADO`).

---

## 🧠 Retornos de Erro Limpos
Para não poluir o Postman com aquele textão de erro do Java (Stacktrace), criamos um tratador global (`RestExceptionHandler`). Quando o *State Pattern* bloqueia uma ação, o usuário recebe um JSON direto e limpo:

```json
{
  "erro": "Erro: O pedido precisa ser pago antes de ser enviado.",
  "status": 400
}
