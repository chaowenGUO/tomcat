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
        config.setJdbcUrl(String.join("", "jdbc:postgresql://", System.getenv("POSTGRESQL_SERVICE_HOST"), ":", System.getenv("POSTGRESQL_SERVICE_PORT"), "/sampledb"));
        config.setUsername("postgresql");
        config.setPassword("postgresql"); 
        final var objectMapper = new ObjectMapper();
        final var arrayNode = objectMapper.createArrayNode();
        try (final var connection = new HikariDataSource(config).getConnection())
        {
            try (final var statement = connection.createStatement())
            {
                statement.executeUpdate("create table if not exists productItem (image int primary key, description text not null, price money not null)");
                System.out.println(statement.executeQuery("select count(*) from productItem").getInt(1));
                statement.executeUpdate("insert into productItem values (9543, '好先生同款墨镜孙红雷偏光男士太阳镜韩明星', 97.50), (9532, '陌森太阳眼镜男女2016偏光定制驾驶近视', 518.70)");
                try (final var resultSet = statement.executeQuery("select * from productItem"))
                {                   
                    while (resultSet.next())
                    {
                        final var metaData = resultSet.getMetaData();
                        final var objectNode = objectMapper.createObjectNode();
                        java.util.stream.IntStream.rangeClosed(1, metaData.getColumnCount()).forEach(column -> objectNode.putPOJO(metaData.getColumnName(column), resultSet.getObject(column)));
                        arrayNode.add(objectNode);
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
