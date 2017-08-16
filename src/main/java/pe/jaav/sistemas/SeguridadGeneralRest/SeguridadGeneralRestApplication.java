package pe.jaav.sistemas.SeguridadGeneralRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication(scanBasePackages={"pe.jaav.sistemas"})
//@EntityScan(basePackages={"pe.royalsystems.springerp.model"})
@EnableConfigurationProperties
@EnableScheduling
@EnableCaching
public class SeguridadGeneralRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeguridadGeneralRestApplication.class, args);
	}
}