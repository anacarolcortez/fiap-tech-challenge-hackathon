-- Extensão para UUID no postgres
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-------------------------------------------------
-- Tabela: distribuidor
-------------------------------------------------
CREATE TABLE distribuidor (
                              uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              nome VARCHAR(255) NOT NULL,
                              cnpj VARCHAR(20) NOT NULL,
                              tipo VARCHAR(50) NOT NULL,
                              logradouro VARCHAR(255),
                              bairro VARCHAR(255),
                              cidade VARCHAR(255),
                              estado VARCHAR(2),
                              cep VARCHAR(20) NOT NULL
);

-------------------------------------------------
-- Tabela: medicamento
-------------------------------------------------
CREATE TABLE medicamento (
                             uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             nome VARCHAR(255) NOT NULL,
                             lote VARCHAR(100) NOT NULL,
                             validade DATE NOT NULL
);

-------------------------------------------------
-- Tabela: usuario
-------------------------------------------------
CREATE TABLE usuario (
                         uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         cpf VARCHAR(14) NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         tipo VARCHAR(50) NOT NULL
);

-------------------------------------------------
-- Tabela: medicamento_distribuidor
-------------------------------------------------
CREATE TABLE medicamento_distribuidor (
                                          uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                          medicamento_id UUID NOT NULL,
                                          distribuidor_id UUID NOT NULL,
                                          quantidade INT NOT NULL,
                                          ultima_atualizacao DATE NOT NULL,

                                          CONSTRAINT fk_md_medicamento
                                              FOREIGN KEY (medicamento_id)
                                                  REFERENCES medicamento (uuid)
                                                  ON DELETE CASCADE,

                                          CONSTRAINT fk_md_distribuidor
                                              FOREIGN KEY (distribuidor_id)
                                                  REFERENCES distribuidor (uuid)
                                                  ON DELETE CASCADE,

                                          CONSTRAINT uk_md UNIQUE (medicamento_id, distribuidor_id)
);

-------------------------------------------------
-- Tabela: usuario_medicamento
-------------------------------------------------
CREATE TABLE usuario_medicamento (
                                     uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     usuario_id UUID NOT NULL,
                                     medicamento_id UUID NOT NULL,
                                     notificacao_ativa BOOLEAN NOT NULL,
                                     data_cadastro DATE NOT NULL,

                                     CONSTRAINT fk_um_usuario
                                         FOREIGN KEY (usuario_id)
                                             REFERENCES usuario (uuid)
                                             ON DELETE CASCADE,

                                     CONSTRAINT fk_um_medicamento
                                         FOREIGN KEY (medicamento_id)
                                             REFERENCES medicamento (uuid)
                                             ON DELETE CASCADE,

                                     CONSTRAINT uk_um UNIQUE (usuario_id, medicamento_id)
);
