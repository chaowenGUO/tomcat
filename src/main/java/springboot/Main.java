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
        final var config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + System.getenv("POSTGRESQL_SERVICE_HOST") + ":" + System.getenv("POSTGRESQL_SERVICE_PORT") + "/sampledb");
        config.setUsername("postgresql");
        config.setPassword("postgresql"); 
        final var objectMapper = new ObjectMapper();
        final var arrayNode = objectMapper.createArrayNode();
        try (final var connection = new HikariDataSource(config).getConnection())
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
                        final var objectNode = objectMapper.createObjectNode();
                        for (var column = 1; column != metaData.getColumnCount() + 1; ++column) objectNode.putPOJO(metaData.getColumnName(column), resultSet.getObject(column)));
                        arrayNode.add(objectNode)
                    }
                }
            }
        }
        return objectMapper.writeValueAsString(arrayNode);
        //return new ObjectMapper().readTree(body).get("name").asText() + "index";
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }
}
