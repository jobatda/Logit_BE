package logit.logit_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@OpenAPIDefinition(
		servers = {
				@Server(url = "https://52.79.117.183/api", description = "Default Server URL")
		}
)
@SpringBootApplication
@EnableJpaAuditing
public class LogitBackendApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		SpringApplication.run(LogitBackendApplication.class, args);
	}

}
