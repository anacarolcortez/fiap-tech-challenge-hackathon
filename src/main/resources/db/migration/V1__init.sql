-- Extensão para UUID no postgres
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-------------------------------------------------
-- Tabela: distribuidor
-------------------------------------------------
CREATE TABLE distribuidor (
                              uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              nome VARCHAR(255) NOT NULL,
                              cnpj VARCHAR(20) NOT NULL,
                              horario_funcionamento VARCHAR(50) NOT NULL,
                              telefone VARCHAR(20) NOT NULL,
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
                         email VARCHAR(100) NOT NULL,
                         telefone VARCHAR(20) NOT NULL,
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

-------------------------------------------------
-- Mocks
------------------------------------------------
INSERT INTO distribuidor (nome, cnpj, horario_funcionamento, telefone, tipo, cep)
VALUES ('Farmácia Popular Centro', '12.345.678/0001-90', '08:00 - 22:00', '11 4002-8922', 'FARMACIA_POPULAR', '01001-000'),
       ('Farmácia Popular Sul', '35.054.812/0001-02', '08:00 - 22:00', '11 4002-8978', 'FARMACIA_POPULAR', '01002-000'),
       ('Drogaraia', '25.594.213/0001-10', '06:00 - 23:00', '11 4002-0014', 'FARMACIA_CREDENCIADA', '01003-000'),
       ('UBS Barra Funda', '36.974.231/0001-43', '07:00 - 20:00', '11 4002-7874', 'UBS', '01004-000'),
       ('UBS Santana', '20.070.140/0001-43', '07:00 - 20:00', '11 4002-2289', 'UBS', '01005-000'),
       ('UBS Interlagos', '08.934.255/0001-33', '07:00 - 20:00', '11 4002-6050', 'UBS', '01006-000'),
       ('UBS Patriarca', '07.087.174/0001-82', '07:00 - 20:00', '11 4002-1678', 'UBS', '01007-000');

INSERT INTO medicamento (nome, lote, validade)
VALUES ('Dipirona', 'LOTE123', '2026-12-31'),
       ('Amoxicilina', 'LOTE9990', '2029-06-20'),
       ('Fluoxetina', 'LOTE5455', '2027-06-20'),
       ('Losartana', 'LOTE97899', '2028-06-20'),
       ('Enalapril', 'LOTE99559', '2027-06-20'),
       ('Olanzapina', 'LOTE78415', '2028-06-20'),
       ('Levodopa', 'LOTE123456', '2026-12-20');

INSERT INTO usuario (cpf, email, telefone, senha, tipo)
VALUES
    ('111.111.111-11', 'admin.sus@saude.gov.br', '11 98888-8888', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMCEZteuPzZ.', 'ADMIN'),
    ('222.222.222-22', 'joao.silva@gmail.com', '11 97777-7777', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMCEZteuPzZ.', 'PACIENTE'),
    ('333.333.333-33', 'maria.oliveira@outlook.com', '21 96666-6666', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMCEZteuPzZ.', 'PACIENTE'),
    ('444.444.444-44', 'farmaceutico.central@sus.br', '31 95555-5555', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMCEZteuPzZ.', 'PACIENTE'),
    ('555.555.555-55', 'ana.souza@yahoo.com.br', '41 94444-4444', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMCEZteuPzZ.', 'PACIENTE');

-- Amoxicilina em múltiplas UBSs
INSERT INTO medicamento_distribuidor (medicamento_id, distribuidor_id, quantidade, ultima_atualizacao)
VALUES
    ((SELECT uuid FROM medicamento WHERE nome = 'Amoxicilina'), (SELECT uuid FROM distribuidor WHERE nome = 'UBS Santana'), 250, CURRENT_DATE),
    ((SELECT uuid FROM medicamento WHERE nome = 'Amoxicilina'), (SELECT uuid FROM distribuidor WHERE nome = 'UBS Interlagos'), 120, CURRENT_DATE);

-- Dipirona (Distribuída em quase todos os lugares)
INSERT INTO medicamento_distribuidor (medicamento_id, distribuidor_id, quantidade, ultima_atualizacao)
SELECT m.uuid, d.uuid, 1000, CURRENT_DATE
FROM medicamento m, distribuidor d
WHERE m.nome = 'Dipirona' AND d.tipo IN ('UBS', 'FARMACIA_POPULAR');

-- Remédios de Uso Contínuo (Losartana e Enalapril) nas UBSs
INSERT INTO medicamento_distribuidor (medicamento_id, distribuidor_id, quantidade, ultima_atualizacao)
SELECT m.uuid, d.uuid, 800, CURRENT_DATE
FROM medicamento m, distribuidor d
WHERE m.nome IN ('Losartana', 'Enalapril') AND d.tipo = 'UBS';

-- Remédios Controlados (Fluoxetina e Olanzapina) em distribuidores específicos
INSERT INTO medicamento_distribuidor (medicamento_id, distribuidor_id, quantidade, ultima_atualizacao)
VALUES
    ((SELECT uuid FROM medicamento WHERE nome = 'Fluoxetina'), (SELECT uuid FROM distribuidor WHERE nome = 'Drogaraia'), 50, CURRENT_DATE),
    ((SELECT uuid FROM medicamento WHERE nome = 'Olanzapina'), (SELECT uuid FROM distribuidor WHERE nome = 'UBS Barra Funda'), 30, CURRENT_DATE),
    ((SELECT uuid FROM medicamento WHERE nome = 'Levodopa'), (SELECT uuid FROM distribuidor WHERE nome = 'UBS Patriarca'), 100, CURRENT_DATE);