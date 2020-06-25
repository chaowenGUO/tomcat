package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
    
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
                    statement.executeUpdate("drop table productItem, productUnit, productReview");
                }
            }
        }
    }
    
    @Autowired private DataSource dataSource;
    
    @GetMapping("/") 
    private static ModelAndView main()
    {
        return new ModelAndView("login.html");
    }
    
    @PostMapping("/ajax") 
    private final java.util.List<java.util.Map<String, Object>> ajax(@RequestBody final java.util.Map<String, String> body) throws Exception
    {
   
        final var array = new java.util.ArrayList<java.util.Map<String, Object>>();
        try (final var connection = this.dataSource.get().getConnection())
        {
            try (final var statement = connection.createStatement())
            {
                try (final var resultSet = statement.executeQuery("select * from" + body.entrySet().stream().map($ -> String.join(" ", $.getKey(), $.getValue())).collect(java.util.stream.Collectors.joining(" ")).replace("table", "")))
                {                   
                    while (resultSet.next())
                    {
                        final var metaData = resultSet.getMetaData();
                        final var object = new java.util.LinkedHashMap<String, Object>();
                        for (final var column: (Iterable<Integer>)java.util.stream.IntStream.rangeClosed(1, metaData.getColumnCount())::iterator) object.putIfAbsent(metaData.getColumnName(column), resultSet.getObject(column));
                        array.add(object);
                    }
                }
            }
        }
        return array;
    }
    
    @EnableWebSocket
    private static final class WebSocketConfig implements WebSocketConfigurer
    {
        @Override
        private void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
        {
            registry.addHandler(
                new TextWebSocketHandler()
                {
                    private static final java.util.Map<String, WebSocketSession> sessions = new java.util.concurrent.ConcurrentHashMap<>();
                    private static String name;
                    @Override
                    private void afterConnectionClosed(WebSocketSession session, CloseStatus status)
                    {
                        this.sessions.remove(session.getId());
                        this.sessions.values().stream().forEach(session -> session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("action", "disconnect"), java.util.Map.entry("name", this.name))))));
                    }
                    @Override
                    private void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
                    {
                        if (!this.sessions.containsKey(session.getId()))
                        {
                            this.name = message.getPayload();                      
                            this.sessions.values().stream().forEach(session -> session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("action", "join"), java.util.Map.entry("name", this.name))))));
                            this.sessions.put(session.getId(), session);
                        }
                        else this.sessions.values().stream().filter($ -> $.getId() != session.getId()).forEach(session -> session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("action", "sent"), java.util.Map.entry("name", this.name), java.util.Map.entry("text", message.getPayload()))))));
                    }
                }, "/ws");
        }
    }
    
    public static void main(String[] args)this.sessions.values().stream().forEach(session -> session.sendMessage(new TextMessage({'action':'join', 'name':name})));
    {
        SpringApplication.run(Main.class, args);
    }
}
