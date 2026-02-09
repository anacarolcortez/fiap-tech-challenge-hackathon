package br.org.sus.ms_medicamentos.infra.mapper;

import br.org.sus.ms_medicamentos.domain.DistribuidorSUS;
import br.org.sus.ms_medicamentos.domain.TipoDistribuidor;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.entity.JpaDistribuidorEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DistribuidorMapperTest {

    @Test
    @DisplayName("Deve mapear JpaDistribuidorEntity para DistribuidorSUS com sucesso")
    void deveMapearEntityParaDomainComSucesso() {
        // Given
        UUID uuid = UUID.randomUUID();
        String nome = "Farmácia Central";
        String cnpj = "12.345.678/0001-99";
        String telefone = "(11) 99999-9999";
        String horarioFuncionamento = "08:00 às 18:00";
        TipoDistribuidor tipo = TipoDistribuidor.FARMACIA_POPULAR;
        String logradouro = "Rua das Flores";
        String bairro = "Centro";
        String cidade = "São Paulo";
        String estado = "SP";
        String cep = "01234-567";

        JpaDistribuidorEntity entity = new JpaDistribuidorEntity(
                uuid, nome, cnpj, telefone, horarioFuncionamento,
                tipo, logradouro, bairro, cidade, estado, cep
        );

        // When
        DistribuidorSUS domain = DistribuidorMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(uuid, domain.getUuid());
        assertEquals(nome, domain.getNome());
        assertEquals(cnpj, domain.getCNPJ());
        assertEquals(telefone, domain.getTelefone());
        assertEquals(horarioFuncionamento, domain.getHorarioFuncionamento());
        assertEquals(tipo, domain.getTipo());
        assertEquals(logradouro, domain.getLogradouro());
        assertEquals(bairro, domain.getBairro());
        assertEquals(cidade, domain.getCidade());
        assertEquals(estado, domain.getEstado());
        assertEquals(cep, domain.getCep());
    }

    @Test
    @DisplayName("Deve mapear DistribuidorSUS para JpaDistribuidorEntity com sucesso")
    void deveMapearDomainParaEntityComSucesso() {
        // Given
        UUID uuid = UUID.randomUUID();
        String nome = "Posto de Saúde Municipal";
        String cnpj = "98.765.432/0001-88";
        String telefone = "(21) 88888-8888";
        String horarioFuncionamento = "07:00 às 19:00";
        TipoDistribuidor tipo = TipoDistribuidor.UBS;
        String logradouro = "Av. Principal";
        String bairro = "Jardim";
        String cidade = "Rio de Janeiro";
        String estado = "RJ";
        String cep = "23456-789";

        DistribuidorSUS domain = new DistribuidorSUS(
                uuid, nome, cnpj, telefone, horarioFuncionamento,
                tipo, logradouro, bairro, cidade, estado, cep
        );

        // When
        JpaDistribuidorEntity entity = DistribuidorMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(uuid, entity.getUuid());
        assertEquals(nome, entity.getNome());
        assertEquals(cnpj, entity.getCNPJ());
        assertEquals(telefone, entity.getTelefone());
        assertEquals(horarioFuncionamento, entity.getHorarioFuncionamento());
        assertEquals(tipo, entity.getTipo());
        assertEquals(logradouro, entity.getLogradouro());
        assertEquals(bairro, entity.getBairro());
        assertEquals(cidade, entity.getCidade());
        assertEquals(estado, entity.getEstado());
        assertEquals(cep, entity.getCep());
    }

    @Test
    @DisplayName("Deve mapear corretamente quando campos opcionais são nulos")
    void deveMapearCorretamenteQuandoCamposOpcionaisSaoNulos() {
        // Given
        UUID uuid = UUID.randomUUID();
        String nome = "Farmácia Básica";
        String cnpj = "11.222.333/0001-44";
        String telefone = "(31) 77777-7777";
        String horarioFuncionamento = "08:00 às 17:00";
        TipoDistribuidor tipo = TipoDistribuidor.FARMACIA_CREDENCIADA;
        String logradouro = null;
        String bairro = null;
        String cidade = null;
        String estado = null;
        String cep = "34567-890";

        JpaDistribuidorEntity entity = new JpaDistribuidorEntity(
                uuid, nome, cnpj, telefone, horarioFuncionamento,
                tipo, logradouro, bairro, cidade, estado, cep
        );

        // When
        DistribuidorSUS domain = DistribuidorMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(uuid, domain.getUuid());
        assertEquals(nome, domain.getNome());
        assertEquals(cnpj, domain.getCNPJ());
        assertEquals(telefone, domain.getTelefone());
        assertEquals(horarioFuncionamento, domain.getHorarioFuncionamento());
        assertEquals(tipo, domain.getTipo());
        assertNull(domain.getLogradouro());
        assertNull(domain.getBairro());
        assertNull(domain.getCidade());
        assertNull(domain.getEstado());
        assertEquals(cep, domain.getCep());
    }

    @Test
    @DisplayName("Deve manter consistência no mapeamento bidirecional")
    void deveManterConsistenciaNoMapeamentoBidirecional() {
        // Given
        UUID uuid = UUID.randomUUID();
        String nome = "Centro de Distribuição";
        String cnpj = "55.666.777/0001-22";
        String telefone = "(41) 66666-6666";
        String horarioFuncionamento = "06:00 às 22:00";
        TipoDistribuidor tipo = TipoDistribuidor.FARMACIA_POPULAR;
        String logradouro = "Rua do Comércio";
        String bairro = "Comercial";
        String cidade = "Curitiba";
        String estado = "PR";
        String cep = "45678-901";

        DistribuidorSUS originalDomain = new DistribuidorSUS(
                uuid, nome, cnpj, telefone, horarioFuncionamento,
                tipo, logradouro, bairro, cidade, estado, cep
        );

        // When
        JpaDistribuidorEntity entity = DistribuidorMapper.toEntity(originalDomain);
        DistribuidorSUS mappedDomain = DistribuidorMapper.toDomain(entity);

        // Then
        assertEquals(originalDomain.getUuid(), mappedDomain.getUuid());
        assertEquals(originalDomain.getNome(), mappedDomain.getNome());
        assertEquals(originalDomain.getCNPJ(), mappedDomain.getCNPJ());
        assertEquals(originalDomain.getTelefone(), mappedDomain.getTelefone());
        assertEquals(originalDomain.getHorarioFuncionamento(), mappedDomain.getHorarioFuncionamento());
        assertEquals(originalDomain.getTipo(), mappedDomain.getTipo());
        assertEquals(originalDomain.getLogradouro(), mappedDomain.getLogradouro());
        assertEquals(originalDomain.getBairro(), mappedDomain.getBairro());
        assertEquals(originalDomain.getCidade(), mappedDomain.getCidade());
        assertEquals(originalDomain.getEstado(), mappedDomain.getEstado());
        assertEquals(originalDomain.getCep(), mappedDomain.getCep());
    }

    @Test
    @DisplayName("Deve mapear corretamente todos os tipos de distribuidor")
    void deveMapearCorretamenteTodosOsTiposDistribuidor() {
        UUID uuid = UUID.randomUUID();

        // Test UBS
        JpaDistribuidorEntity entityUBS = new JpaDistribuidorEntity(
                uuid, "UBS Teste", "11.111.111/0001-11", "(11) 1111-1111",
                "08:00 às 17:00", TipoDistribuidor.UBS, "Rua A", "Bairro A",
                "Cidade A", "Estado A", "11111-111"
        );
        DistribuidorSUS domainUBS = DistribuidorMapper.toDomain(entityUBS);
        assertEquals(TipoDistribuidor.UBS, domainUBS.getTipo());

        // Test FARMACIA_POPULAR
        JpaDistribuidorEntity entityPopular = new JpaDistribuidorEntity(
                uuid, "Farmácia Popular", "22.222.222/0001-22", "(22) 2222-2222",
                "08:00 às 18:00", TipoDistribuidor.FARMACIA_POPULAR, "Rua B", "Bairro B",
                "Cidade B", "Estado B", "22222-222"
        );
        DistribuidorSUS domainPopular = DistribuidorMapper.toDomain(entityPopular);
        assertEquals(TipoDistribuidor.FARMACIA_POPULAR, domainPopular.getTipo());

        // Test FARMACIA_CREDENCIADA
        JpaDistribuidorEntity entityCredenciada = new JpaDistribuidorEntity(
                uuid, "Farmácia Credenciada", "33.333.333/0001-33", "(33) 3333-3333",
                "08:00 às 19:00", TipoDistribuidor.FARMACIA_CREDENCIADA, "Rua C", "Bairro C",
                "Cidade C", "Estado C", "33333-333"
        );
        DistribuidorSUS domainCredenciada = DistribuidorMapper.toDomain(entityCredenciada);
        assertEquals(TipoDistribuidor.FARMACIA_CREDENCIADA, domainCredenciada.getTipo());
    }

    @Test
    @DisplayName("Deve mapear corretamente campos vazios")
    void deveMapearCorretamenteCamposVazios() {
        // Given
        UUID uuid = UUID.randomUUID();
        JpaDistribuidorEntity entity = new JpaDistribuidorEntity(
                uuid, "", "", "", "", TipoDistribuidor.UBS,
                "", "", "", "", ""
        );

        // When
        DistribuidorSUS domain = DistribuidorMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(uuid, domain.getUuid());
        assertEquals("", domain.getNome());
        assertEquals("", domain.getCNPJ());
        assertEquals("", domain.getTelefone());
        assertEquals("", domain.getHorarioFuncionamento());
        assertEquals(TipoDistribuidor.UBS, domain.getTipo());
        assertEquals("", domain.getLogradouro());
        assertEquals("", domain.getBairro());
        assertEquals("", domain.getCidade());
        assertEquals("", domain.getEstado());
        assertEquals("", domain.getCep());
    }
}