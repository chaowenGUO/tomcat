package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@RestController
@SpringBootApplication
public class Main
{
    @PostMapping("/ajax")
    String ajax(@RequestBody final String body) throws Exception
    {
        return new ObjectMapper().readTree(body).get("name").asText() + "index";
    }
    
    public static void main(String[] args) throws Exception
    {
        final var config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + System.getenv("POSTGRESQL_SERVICE_HOST") + ":" + System.getenv("POSTGRESQL_SERVICE_PORT") + "/sampledb");
        config.setUsername("postgresql");
        config.setPassword("postgresql"); 
        final var dataSource = new HikariDataSource(config);
        try (final var connection = dataSource.getConnection())
        {
            try (final var statement = connection.createStatement())
            {
                statement.executeUpdate("create table if not exists productItem (image int primary key, description int not null)");
                //statement.executeUpdate("insert into productItem values (9543, 1234), (9532, 5678)");
                try (final var resultSet = statement.executeQuery("select * from productItem"))
                {
                    while (resultSet.next())
                    {
                        final var metaData = resultSet.getMetaData();
                        final var objectNode = new ObjectMapper().createObjectNode();
                        for (var column = 1; column != metaData.getColumnCount() + 1; ++column) objectNode.put(metaData.getColumnName(column), resultSet.getInt(column));
                        System.out.println(new ObjectMapper.writeValueAsString(objectNode));
                    }
                }
            }
        }
        SpringApplication.run(Main.class, args);
    }
}
