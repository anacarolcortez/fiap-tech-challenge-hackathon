package br.org.sus.ms_medicamentos.infra.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Flyway.class, DataSource.class })
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ManualFlywayMigrationConfig {
	private static final Logger log = LoggerFactory.getLogger(ManualFlywayMigrationConfig.class);

	/**
	 * Fallback para garantir execução do Flyway antes do Hibernate.
	 *
	 * Em alguns cenários o FlywayAutoConfiguration não está sendo aplicado; com
	 * isso o Hibernate (ddl-auto=validate) falha antes das migrações.
	 */
	@Bean(name = "flyway")
	Flyway flyway(DataSource dataSource, Environment environment) {
		log.info("Running Flyway migrations (manual config)");

		Boolean baselineOnMigrate = environment.getProperty("spring.flyway.baseline-on-migrate", Boolean.class);
		String locationsProperty = environment.getProperty("spring.flyway.locations");
		String schemasProperty = environment.getProperty("spring.flyway.schemas");
		String defaultSchemaProperty = environment.getProperty("spring.flyway.default-schema");
		String tableProperty = environment.getProperty("spring.flyway.table");

		FluentConfiguration configuration = Flyway.configure()
				.dataSource(dataSource)
				.baselineOnMigrate(Boolean.TRUE.equals(baselineOnMigrate));

		if (tableProperty != null && !tableProperty.isBlank()) {
			configuration.table(tableProperty);
		}

		if (locationsProperty != null && !locationsProperty.isBlank()) {
			String[] locations = locationsProperty.split("\\s*,\\s*");
			configuration.locations(locations);
		} else {
			configuration.locations("classpath:db/migration");
		}

		if (schemasProperty != null && !schemasProperty.isBlank()) {
			String[] schemas = schemasProperty.split("\\s*,\\s*");
			configuration.schemas(schemas);
		}

		if (defaultSchemaProperty != null && !defaultSchemaProperty.isBlank()) {
			configuration.defaultSchema(defaultSchemaProperty);
		}

		Flyway flyway = configuration.load();
		flyway.migrate();
		return flyway;
	}
}
