# 🤖 AI Prompt - Sistema de Pedidos em Java

## Contexto
Estou construindo uma API backend em Java para gerenciamento de pedidos.

## Objetivo
Criar um endpoint para criação de pedidos.

## Entrada
- Lista de itens:
    - nome
    - preço
    - quantidade

## Regras de Negócio
- Pedido deve ter pelo menos 1 item
- Calcular o valor total automaticamente
- Aplicar desconto de 10% se total > 100

## Saída Esperada
- Retornar pedido com:
    - lista de itens
    - valor total calculado

## Tecnologias
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL

## Arquitetura
- Controller
- Service
- Repository

## Extras
- Logging com correlationId
- Tratamento de erro global
- Teste de integração

## Instrução para IA
Gere o código completo seguindo boas práticas, separando em camadas e explicando cada parte.