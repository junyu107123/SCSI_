package scsi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories({
    "scsi.demo",
})
@ComponentScan({
	 "scsi.demo",
})
@EntityScan({
	 "scsi.demo",
})
public class ScsiSpring2Application extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ScsiSpring2Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ScsiSpring2Application.class, args);
	}
	
}
