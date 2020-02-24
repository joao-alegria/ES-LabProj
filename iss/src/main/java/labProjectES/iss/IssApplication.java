package labProjectES.iss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IssApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssApplication.class, args);
	}

}
