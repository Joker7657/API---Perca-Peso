# 🏃‍♀️ Perca Peso API

API para gerenciamento de metas de emagrecimento e acompanhamento de tarefas relacionadas à saúde.

## 🚀 Como Executar a Aplicação

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6 ou superior

### Passos para Executar

1. **Clone o repositório** (se ainda não tiver feito):
```bash
git clone https://github.com/Joker7657/API---Perca-Peso.git
cd API---Perca-Peso
```

2. **Navegue até o diretório do projeto**:
```bash
cd health-track-api
```

3. **Execute a aplicação**:
```bash
mvn spring-boot:run
```

4. **Acesse a aplicação**:
   - A API estará disponível em: `http://localhost:8080`
   - Página inicial: `http://localhost:8080/index.html`
   - Documentação da API: `http://localhost:8080/swagger-ui.html` (se configurado)

### Para Encerrar a Aplicação
Pressione `Ctrl + C` no terminal onde a aplicação está rodando, ou execute:
```bash
pkill -f "spring-boot:run"
```

---




📋 Resumo
Este projeto tem como objetivo desenvolver uma API voltada para o gerenciamento de metas de emagrecimento e acompanhamento de tarefas relacionadas à saúde. A solução permitirá que o usuário registre atividades, monitore peso e medidas, acompanhe hábitos diários e organize sua rotina de forma mais eficiente. Ao centralizar informações importantes do processo de emagrecimento, a API busca oferecer uma ferramenta prática, estruturada e útil, sistema completo para acompanhamento de progresso.
Problema 
Pessoas que buscam emagrecimento precisam acompanhar exercícios, alimentação, humor, peso, medidas e hábitos diários. Porém, muitas vezes utilizam várias ferramentas separadas (anotações, planilhas, apps isolados), o que dificulta:
organizar informações.
acompanhar evolução.
calcular IMC, TMB, calorias e progresso.
manter motivação e consistência.
Para resolver isso, a API centraliza todas as informações de saúde, hábitos e progresso em um único sistema, permitindo controle completo, cálculo automático, relatórios personalizados e gamificação para manter o usuário motivado.

Público-Alvo
2.1  Pessoas que buscam emagrecimento e melhoria de hábitos:
Quem são: indivíduos em processo de perda de peso, melhoria da composição corporal, controle de hábitos alimentares e adesão a rotinas de atividade física.
Objetivos comuns: reduzir peso, melhorar IMC, aumentar energia, criar hábitos duradouros, ganhar autoconfiança.
Necessidades-chave: registro diário simples de alimentação, exercícios, sono, hidratação; feedbacks automáticos; visualização clara de progresso; recomendações personalizadas.

2.2  Profissionais de saúde:
            ⇒ Nutricionistas:
Função: planejar dietas, monitorar adesão, ajustar metas energéticas e macros com base em dados consolidados.
Necessidades: acesso a histórico alimentar, evolução de peso, métricas bioquímicas quando disponíveis, ferramentas para criar planos e acompanhar clientes.

⇒  Personal Trainers:
Função: prescrever treinos, monitorar progressos físicos, ajustar cargas e objetivos.
Necessidades: registro de atividades, evolução de medidas corporais, víeis de recuperação e sono, integração com apps de treino.

Funcionalidades Principais

3.1.  Gestão de Usuários
Cadastro com nome, e-mail, senha, idade, gênero, altura, peso atual, meta de peso e condições de saúde.
Cálculo automático de IMC e Taxa Metabólica Basal (TMB).

3.2. Tarefas e Objetivos
Criação de tarefas classificadas em:
Exercícios físicos
Alimentação
Hábitos saudáveis
Campos completos: título, descrição, categoria, dificuldade, calorias estimadas, tempo necessário, data/hora, repetição, lembrete.
Marcar conclusão e registrar progresso.

3.3. Sistema de Acompanhamento Diário
Registro de peso, medidas
Registro de humor, energia e disposição.

3.4. Módulo Nutricional
Registro de refeições e horários.
Contagem automática de calorias e macronutrientes.
Sugestões saudáveis de substituição.

3.5. Sistema de Motivação e Recompensas
Pontos por tarefas concluídas.
Medalhas, conquistas e streaks de consistência.
Feedback automático baseado no progresso.

A Decisão do Projeto
A ideia para o desenvolvimento da API é um Sistema de Gerenciamento de Metas de Emagrecimento com Acompanhamento de Tarefas. A escolha deste tema se justifica por oferecer um desafio adequado ao portfólio, permitindo explorar uma lógica de negócios. O projeto envolve regras de saúde reais, como acompanhamento de peso, cálculo de metas, organização de tarefas, registro de hábitos e análise de progresso, tornando a solução mais completa, interessante e aplicável.
O sistema integra exercícios, alimentação, métricas corporais e motivação, exigindo planejamento cuidadoso das entidades, relações e funcionalidades, e demonstrando a capacidade de estruturar um backend de forma organizada e coerente. O tema é atual e de grande aplicabilidade prática, com potencial para evoluir para um aplicativo real no futuro.
Essa escolha reflete relevância, lógica concreta, raciocínio além do básico, boa modelagem e entrega de um resultado útil. Trata-se de uma solução robusta para o portfólio, com propósito claro, complexidade equilibrada e potencial de expansão. Se desejarem, posso adaptar o texto a requisitos acadêmicos específicos ou incluir referências teóricas que fortalecem a fundamentação.
Stack de Tecnologia

Framework: Spring Boot 3+ 
 Linguagem: Java 17+
Documentação: Swagger / OpenAPI para documentação automática dos endpoints e suporte ao uso da API
Persistência de Dados
Repositório em Memória (durante a execução da API)
    Durante o funcionamento da aplicação, os dados serão mantidos diretamente na memória da aplicação utilizando estruturas de dados do Java, como:
HashMap – para armazenar entidades identificadas por ID (ex.: usuários, tarefas, refeições).
ArrayList – para listas internas e registros associados (ex.: fotos, itens alimentares, métricas diárias).
Os dados ficam disponíveis enquanto o servidor está em execução. Essa abordagem oferece rapidez na leitura e escrita, facilitando testes e desenvolvimento. Ao encerrar a aplicação, os dados armazenados apenas em memória são descartados.

Persistência em Arquivo (armazenamento permanente). A aplicação utiliza armazenamento local em arquivos JSON para gravar e recuperar dados de forma simples e organizada. No início, lê os arquivos JSON para reconstruir os objetos em memória. Sempre que houver criação, atualização ou remoção de dados, o arquivo JSON correspondente é regravado com o estado atualizado. Ao encerrar, a versão mais recente permanece salva, mantendo as informações disponíveis para futuras execuções.

Testes básicos utilizando JUnit para verificar o comportamento das principais funcionalidades
Controle de Versão: Git e GitHub para organização do código, commits e evolução do projeto
