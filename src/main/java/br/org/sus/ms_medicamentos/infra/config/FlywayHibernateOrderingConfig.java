package br.org.sus.ms_medicamentos.infra.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class FlywayHibernateOrderingConfig {

	@Bean
	static BeanFactoryPostProcessor entityManagerFactoryDependsOnFlywayInitializer() {
		return beanFactory -> {
			if (!beanFactory.containsBeanDefinition("entityManagerFactory")) {
				return;
			}

			String flywayInitializerBeanName = null;
			if (beanFactory.containsBeanDefinition("flyway")) {
				flywayInitializerBeanName = "flyway";
			} else if (beanFactory.containsBeanDefinition("flywayInitializer")) {
				flywayInitializerBeanName = "flywayInitializer";
			} else if (beanFactory.containsBeanDefinition("flywayMigrationInitializer")) {
				flywayInitializerBeanName = "flywayMigrationInitializer";
			}
			if (flywayInitializerBeanName == null) {
				return;
			}

			BeanDefinition entityManagerFactory = beanFactory.getBeanDefinition("entityManagerFactory");
			String[] currentDependsOn = entityManagerFactory.getDependsOn();

			List<String> dependsOn = new ArrayList<>();
			if (currentDependsOn != null && currentDependsOn.length > 0) {
				dependsOn.addAll(Arrays.asList(currentDependsOn));
			}
			if (!dependsOn.contains(flywayInitializerBeanName)) {
				dependsOn.add(flywayInitializerBeanName);
			}

			entityManagerFactory.setDependsOn(dependsOn.toArray(String[]::new));
		};
	}
}
