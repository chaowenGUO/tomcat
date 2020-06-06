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
        config.setJdbcUrl("jdbc:postgresql://172.30.22.106:5432/sampledb");
        config.setUsername("postgresql");
        config.setPassword("postgresql");
        final HikariDataSource ds = new HikariDataSource(config);
        SpringApplication.run(Main.class, args);
    }
}
