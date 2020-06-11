package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
    
@RestController
@SpringBootApplication
public class Main
{
    @Component
    private static final class DataSource
    {
        private HikariDataSource dataSource;
        private DataSource() throws Exception
        {
            final var config = new HikariConfig();
            config.setJdbcUrl(String.join("", "jdbc:postgresql://", System.getenv("POSTGRESQL_SERVICE_HOST"), ":", System.getenv("POSTGRESQL_SERVICE_PORT"), "/sampledb"));
            config.setUsername("postgresql");
            config.setPassword("postgresql");
            this.dataSource = new HikariDataSource(config);
            try (final var connection = this.dataSource.getConnection())
            {
                try (final var statement = connection.createStatement())
                {
                    statement.executeUpdate(new java.io.BufferedReader(new java.io.InputStreamReader(new ClassPathResource("database.sql").getInputStream(), java.nio.charset.StandardCharsets.UTF_8)).lines().collect(java.util.stream.Collectors.joining("\n")));
                }
            }             
        }
        
        public HikariDataSource get()
        {
            return this.dataSource;
        }
        
        @PreDestroy
        private void shutdown() throws Exception
        {
            try (final var connection = this.dataSource.getConnection())
            {
                try (final var statement = connection.createStatement())
                {
                    statement.executeUpdate("drop table productItem");
                }
            }
        }
    }
    
    @Autowired private DataSource dataSource;
    
    @PostMapping("/ajax") 
    private final java.util.ArrayList<java.util.HashMap<String, Object>> ajax(@RequestBody final String body) throws Exception
    {
        
        //final var objectMapper = new ObjectMapper();
        //final var arrayNode = objectMapper.createArrayNode();
        final var list = new java.util.ArrayList<java.util.HashMap<String, Object>>();
        try (final var connection = this.dataSource.get().getConnection())
        {
            try (final var statement = connection.createStatement())
            {
                try (final var resultSet = statement.executeQuery("select * from productItem"))
                {                   
                    while (resultSet.next())
                    {
                        final var metaData = resultSet.getMetaData();
                        //final var objectNode = objectMapper.createObjectNode();
                        final var map = new java.util.HashMap<String, Object>();
                        for (final var column: (Iterable<Integer>)java.util.stream.IntStream.rangeClosed(1, metaData.getColumnCount())::iterator) //objectNode.putPOJO(metaData.getColumnName(column), resultSet.getObject(column));
                            map.putIfAbsent(metaData.getColumnName(column), resultSet.getObject(column));
                        list.add(map);
                    }
                }
            }
        }
        //return objectMapper.writeValueAsString(arrayNode);
        return list;
        //return new ObjectMapper().readTree(body).get("name").asText() + "index";
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }
}
