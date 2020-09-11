package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
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
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Main
{
    @Component
    private static final class Jdbc
    {
        private JdbcTemplate jdbcTemplate;
        private Jdbc() throws Exception
        {
            final var config = new HikariConfig();
            final var dbUri = java.net.URI.create(System.getenv().containsKey("DATABASE_URL") ? System.getenv("DATABASE_URL") : "");
            config.setJdbcUrl(System.getenv().containsKey("DATABASE_URL") ? String.join("", "jdbc:postgresql://", dbUri.getHost(), ":", String.valueOf(dbUri.getPort()), dbUri.getPath()) : "jdbc:postgresql://echo.db.elephantsql.com:5432/yhsgqmtv");
            config.setUsername(System.getenv().containsKey("DATABASE_URL") ? dbUri.getUserInfo().split(":")[0] : "yhsgqmtv");
            config.setPassword(System.getenv().containsKey("DATABASE_URL") ? dbUri.getUserInfo().split(":")[1] : "TNksBpYp-8oJnWg2xIG_56zoDuDJ7y06");
            this.jdbcTemplate = new JdbcTemplate(new HikariDataSource(config));
            try (final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(new ClassPathResource("database.sql").getInputStream(), java.nio.charset.StandardCharsets.UTF_8)))
            {
                this.jdbcTemplate.update(reader.lines().collect(java.util.stream.Collectors.joining("\n")));
            }
        }
        
        public JdbcTemplate get()
        {
            return this.jdbcTemplate;
        }
        
        @PreDestroy
        private void shutdown() throws Exception
        {
            this.jdbcTemplate.update("drop table productItem, productUnit, productReview");
        }
    }
    
    @Autowired private Jdbc jdbcTemplate;
    
    @GetMapping("/") 
    private static ModelAndView main()
    {
        return new ModelAndView("login.html");
    }
    
    @PostMapping("/ajax") 
    private final java.util.List<java.util.Map<String, Object>> ajax(@RequestBody final java.util.Map<String, String> body) throws Exception
    {
        return this.jdbcTemplate.get().queryForList("select * from" + body.entrySet().stream().map($ -> String.join(" ", $.getKey(), $.getValue())).collect(java.util.stream.Collectors.joining(" ")).replace("table", ""));
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
                for (final var $: this.sessions) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("disconnect", ""), java.util.Map.entry("name", session.getAttributes().get("name"))))));
            }
            @Override
            protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception
            {
                if (java.util.Objects.isNull(session.getAttributes().get("name")))
                {
                    session.getAttributes().put("name", message.getPayload());
                    for (final var $: this.sessions) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("join", ""), java.util.Map.entry("name", session.getAttributes().get("name"))))));
                    this.sessions.add(session);
                }
                else
                {
                    final var json = objectMapper.readTree(message.getPayload());
                    if (json.has(""))
                    {
                        for (final var $: this.sessions)
                            if ($ != session) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("", json.get("")), java.util.Map.entry("name", session.getAttributes().get("name"))))));
                    }
                    else if (json.has("offer"))
                    {
                        for (final var $: this.sessions)
                            if ($.getAttributes().get("name").equals(json.get("name").asText())) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("offer", json.get("offer")), java.util.Map.entry("name", session.getAttributes().get("name"))))));
                    }
                    else if (json.has("answer"))
                    {
                        for (final var $: this.sessions)
                            if ($.getAttributes().get("name").equals(json.get("name").asText())) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("answer", json.get("answer"))))));
                    }
                    else if (json.has("candidate"))
                    {
                        for (final var $: this.sessions)
                            if ($.getAttributes().get("name").equals(json.get("name").asText())) $.sendMessage(new TextMessage(objectMapper.writeValueAsString(java.util.Map.ofEntries(java.util.Map.entry("candidate", json.get("candidate"))))));
                    }   
                }
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
