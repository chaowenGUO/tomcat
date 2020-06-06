package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@RestController
@EnableAutoConfiguration
public class Main
{
    public static void main(String[] args)
    {
        final var config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + System.getenv("POSTGRESQL_SERVICE_HOST") + ":" + System.getenv("POSTGRESQL_SERVICE_PORT") + "/sampledb");
        config.setUsername("postgresql");
        config.setPassword("postgresql");
        final var ds = new HikariDataSource(config);
        SpringApplication.run(Main.class, args);
    }
}
