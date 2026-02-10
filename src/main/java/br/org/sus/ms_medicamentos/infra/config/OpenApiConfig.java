package br.org.sus.ms_medicamentos.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Tem Remédio?",
                version = "v1",
                description = "Documentação da API que busca medicamentos e configura notificações de remédios nos distribuidores SUS para pacientes do sistema"
        )
)
public class OpenApiConfig {
}

