package de.schauderhaft.poc.oracle.storedprocedure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.testcontainers.containers.OracleContainer;

import javax.sql.DataSource;
import java.sql.SQLException;

@EnableJdbcAuditing
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	private static final Log LOG = LogFactory.getLog(Application.class);

	private static OracleContainer ORACLE_CONTAINER;

	@Bean
	DataSource dataSource() {

		if (ORACLE_CONTAINER == null) {

			LOG.info("Oracle starting...");
			OracleContainer container = new OracleContainer("gvenzl/oracle-xe:21.3.0-slim").withReuse(true);
			container.start();
			LOG.info("Oracle started");

			ORACLE_CONTAINER = container;
		}

		initDb();

		final DriverManagerDataSource dataSource = new DriverManagerDataSource(ORACLE_CONTAINER.getJdbcUrl(), ORACLE_CONTAINER.getUsername(),
				ORACLE_CONTAINER.getPassword());

		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
		populator.setIgnoreFailedDrops(true);
		populator.setSeparator("#");
		populator.execute(dataSource);

		return dataSource;
	}

	private void initDb() {

		final DriverManagerDataSource dataSource = new DriverManagerDataSource(ORACLE_CONTAINER.getJdbcUrl(), "SYSTEM",
				ORACLE_CONTAINER.getPassword());
		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("GRANT ALL PRIVILEGES TO " + ORACLE_CONTAINER.getUsername());
	}

}
