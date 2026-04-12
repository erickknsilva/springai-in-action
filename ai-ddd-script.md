# 🤖 AI EXECUTION SCRIPT - ORDER DOMAIN (DDD)

## 📌 Contexto
Sistema de pedidos de um e-commerce.

Aplicação deve seguir princípios de DDD (Domain-Driven Design), separando:
- Domain
- Application
- Infrastructure
- Interface (API)

---

# 🧠 Regras de Negócio (CORE)

- Pedido deve ter pelo menos 1 item
- Item deve ter:
    - nome
    - preço > 0
    - quantidade > 0
- Total do pedido = soma dos itens (price * quantity)
- Aplicar desconto:
    - 10% se total > 100
- Pedido nasce com status: CREATED

---

# 🧩 Etapa 1 - Domain Layer

## Criar entidade Order (Aggregate Root)

Campos:
- id (UUID)
- createdAt (LocalDateTime)
- status (ENUM: CREATED, PAID, CANCELLED)
- items (List<OrderItem>)
- total (BigDecimal)

Regras dentro da entidade:
- método addItem()
- método calculateTotal()
- método applyDiscount()
- método validate()

## Criar entidade OrderItem

Campos:
- id
- name
- price
- quantity

Regra:
- método getTotal()

---

# 🧩 Etapa 2 - Repository (Interface de domínio)

Criar interface:

OrderRepository

Métodos:
- save(Order order)
- findById(UUID id)

IMPORTANTE:
- Não usar Spring aqui (somente interface)

---

# 🧩 Etapa 3 - Application Layer (Service)

Criar OrderService

Método principal:

createOrder(CreateOrderCommand command)

Responsabilidades:
- Criar entidade Order
- Adicionar itens
- Validar pedido
- Calcular total
- Aplicar desconto
- Salvar via repository
- Retornar resultado

---

# 🧩 Etapa 4 - DTO / Command

Criar:

CreateOrderCommand
- items (name, price, quantity)

OrderResponse
- id
- total
- status

---

# 🧩 Etapa 5 - Infrastructure

Criar implementação:

JpaOrderRepository

- usar Spring Data JPA
- mapear entidade para banco

---

# 🧩 Etapa 6 - API Layer (Controller)

Criar:

OrderController

Endpoint:

POST /orders

Fluxo:
- Recebe JSON
- Converte para Command
- Chama OrderService
- Retorna response

---

# 🧩 Etapa 7 - Tratamento de Erros

Criar:
- DomainException
- GlobalExceptionHandler

---

# 🧩 Etapa 8 - Logging

- Usar MDC
- Gerar correlationId
- Logar entrada e saída

---

# 🧩 Etapa 9 - Teste

Criar teste de integração:

- Criar pedido válido
- Validar retorno

---

# ⚙️ Requisitos Técnicos

- Java 17+
- Spring Boot
- JPA
- PostgreSQL
- Clean Architecture
- SOLID

---

# 🚀 Instrução Final para IA

Execute cada etapa separadamente.

Priorize:
- regras no domínio (não no service)
- código limpo
- separação de responsabilidades
- padrão DDD

Nunca colocar regra de negócio no controller.