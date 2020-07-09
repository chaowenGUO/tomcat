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
import org.springframework.web.socket.CloseStatus;
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
            //config.setJdbcUrl(String.join("", "jdbc:postgresql://", System.getenv("POSTGRESQL_SERVICE_HOST"), ":", System.getenv("POSTGRESQL_SERVICE_PORT"), "/sampledb"));
            //config.setUsername("postgresql");
            //config.setPassword("postgresql");"jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
            final var config = new HikariConfig();
            final var dbUri = java.net.URI.create(System.getenv("DATABASE_URL"));
            config.setJdbcUrl(String.join("", "jdbc:postgresql://", dbUri.getHost(), ":", String.valueOf(dbUri.getPort()), dbUri.getPath()));
            config.setUsername(dbUri.getUserInfo().split(":")[0]);
            config.setPassword(dbUri.getUserInfo().split(":")[1]);
            this.dataSource = new HikariDataSource(config);
            try (final var connection = this.dataSource.getConnection())
            {
                try (final var statement = connection.createStatement())
                {
                    try (final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(new ClassPathResource("database.sql").getInputStream(), java.nio.charset.StandardCharsets.UTF_8)))
                    {
                        statement.executeUpdate(reader.lines().collect(java.util.stream.Collectors.joining("\n")));
                    }
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
        private static final class WebSocker extends TextWebSocketHandler implements AutoCloseable
        {
            private static final java.util.Set<WebSocketSession> sessions = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());
            private static final ObjectMapper objectMapper = new ObjectMapper();
            @Override
            public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception
            {
                this.sessions.remove(session);
                for (final var $: this.sessions) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("action", "disconnect"), java.util.Map.entry("name", session.getAttributes().get("name"))))));
            }
            @Override
            protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception
            {
                if (java.util.Objects.isNull(session.getAttributes().get("name")))
                {
                    session.getAttributes().put("name", message.getPayload());
                    for (final var $: this.sessions) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("action", "join"), java.util.Map.entry("name", session.getAttributes().get("name"))))));
                    this.sessions.add(session);
                }
                else
                    for (final var $: this.sessions)
                        if ($ != session) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("action", "send"), java.util.Map.entry("name", session.getAttributes().get("name")), java.util.Map.entry("text", message.getPayload())))));
            }
            @Override
            public void close() throws Exception
            {
                for (final var $: this.sessions) $.close();
            }
        }
        @Override
        public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry)
        {
            try (final var webSocker = new WebSocker())
            {
                registry.addHandler(webSocker, "/ws");
            }
            catch (Exception error){}
        }
    }
    
    public static void main(final String[] args)
    {
        final var app = new SpringApplication(Main.class);
        app.setDefaultProperties(java.util.Collections.singletonMap("server.port", System.getenv("PORT")));
        app.run(args);
    }
}
