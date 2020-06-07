package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@RestController
@SpringBootApplication
public class Main
{
    @PostMapping("/ajax")
    String ajax(@RequestBody String body)
    {
        return "index";
    }
    
    public static void main(String[] args)
    {
        final var config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + System.getenv("POSTGRESQL_SERVICE_HOST") + ":" + System.getenv("POSTGRESQL_SERVICE_PORT") + "/sampledb");
        config.setUsername("postgresql");
        config.setPassword("postgresql");
        final var dataSource = new HikariDataSource(config);
        //dataSource.getConnection();
        //try (final var connection = dataSource.getConnection())
        /*{
            final var statement = connection.createStatement();
            statement.executeUpdate("create table if not exists productItem (image int primary key, description text not null, price money not null)");
            statement.executeUpdate("insert into productItem values (9543, \"好先生同款墨镜孙红雷偏光男士太阳镜韩明星\", 97.50), (9532, \"陌森太阳眼镜男女2016偏光定制驾驶近视\", 518.70)");
            final var resultSet = statement.executeQuery("select * from productItem");
            while (resultSet.next())
            {
                System.out.print(resultSet.getInt("image"));
                System.out.print(resultSet.getString("description"));
                System.out.print(resultSet.getBigDecimal("price"));
                System.out.println();
            }
        }*/
        SpringApplication.run(Main.class, args);
    }
}
