# 📘 springai-in-action

## 📖 Capítulo 04

Este capítulo apresenta uma visão prática de como construir aplicações de Inteligência Artificial utilizando o framework Spring AI.  
O conteúdo vai desde conceitos básicos até técnicas mais avançadas, como a criação de agentes inteligentes e uso de modelos de linguagem.

---

## 🧠 Resumo

- RAG (*Retrieval-Augmented Generation*) permite que aplicações enviem prompts com informações que o LLM **não foi originalmente treinado**  
- Ajuda a reduzir **alucinações**, quando o modelo responde com informações incorretas ou fora de contexto  
- Funciona buscando **documentos semelhantes à pergunta** e adicionando isso como contexto no prompt  
- O Spring AI oferece integração com diversas **vector stores** para armazenar e consultar esses documentos  
- É possível implementar RAG manualmente ou usar recursos prontos do Spring AI, como o **`ChatClientQuestionAnswerAdvisor`**, que automatiza esse processo  

---

## ⚙️ Tópicos abordados

- Introdução ao Spring AI  
- Conceito de RAG  
- Integração com vector stores  
- Uso de advisors no Spring AI  
- Enriquecimento de prompts com contexto  
- Redução de alucinações em LLMs  
