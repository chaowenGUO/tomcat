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
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + System.getenv("POSTGRESQL_SERVICE_HOST") + ":" + System.getenv("POSTGRESQL_SERVICE_PORT") + "/" + System.getenv("POSTGRESQL_DATABASE"));
        config.setUsername(System.getenv("POSTGRESQL_USER"));
        config.setPassword(System.getenv("POSTGRESQL_PASSWORD"));
        final HikariDataSource ds = new HikariDataSource(config);
        SpringApplication.run(Main.class, args);
    }
}
