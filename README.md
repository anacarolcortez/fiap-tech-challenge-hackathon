🏥 MS-Medicamentos - Gestão de Estoque SUS

Este microserviço faz parte do sistema de gestão de medicamentos para Unidades Básicas de Saúde e farmácias populares credenciadas pelo SUS, com o intuito de notificar pacientes da rede pública de saúde sobre a dispoibilidade dos remédios de uso contínuo dos quais precisam. Desenvolvido com Java 21, Spring Boot 4 e Arquitetura Hexagonal.
🚀 Como Rodar o Projeto (Localmente)

Para facilitar a avaliação e o desenvolvimento, utilizamos Docker e Docker Compose para orquestrar a aplicação e o banco de dados.
📋 Pré-requisitos

    Docker instalado.

    Git para clonar o projeto.

🛠️ Passo a Passo

    Clone o repositório:
    Bash

    git clone https://github.com/seu-usuario/ms-medicamentos.git
    cd ms-medicamentos

    Configure as Variáveis de Ambiente: Crie um arquivo chamado .env na raiz do projeto e preencha com as credenciais (exemplo):
    Code snippet

    DB_NAME=medicamento_db
    DB_USER=postgres
    DB_PASSWORD=dracarys11

    Suba os containers:
    Bash

    docker compose up --build

    Este comando irá compilar o código Java (Multistage Build), criar a imagem Docker e iniciar o banco Postgres com os scripts de população inicial.

🏗️ Arquitetura e Infraestrutura

A aplicação foi desenhada para ser Cloud Native:

    Banco de Dados: PostgreSQL (RDS na AWS).

    Migrações: Flyway/SQL Scripts executados no init-db do Docker.

    Deploy: Dockerfile preparado para AWS App Runner / ECS.

🔌 Endpoints Principais
1. Atualizar Estoque

PUT /estoque

Request:
```
{
"uuid": "uuid-do-registro",
"quantidade": 50
}

```

2. Atualizar estoque de medicamento

PUT /estoque/{uuidRemedio}

Request:

```
{
    "uuid": "",
    "quantidade": 50
}
```

3. Listar Medicamentos por Nome

GET /estoque/{nomeRemedio}?page=0&size=10


🗃️ Dados de Teste (Mock)

Ao iniciar o banco, o sistema é populado automaticamente com:

    5 Usuários (Admin, Farmacêutico e Pacientes).

    Distribuidores pré-cadastrados.

    Medicamentos (ex: Dipirona, Amoxicilina).

    Nota: As senhas dos usuários mockados são password123.