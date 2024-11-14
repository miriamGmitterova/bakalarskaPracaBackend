package bakalarka.example.bakalarka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("bakalarka.example.bakalarka.repositories")
public class VysetreniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VysetreniaApplication.class, args);
	}

}
