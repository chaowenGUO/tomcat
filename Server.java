/*import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import com.fasterxml.jackson.databind.ObjectMapper;*/

/*import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
    
@org.springframework.boot.autoconfigure.SpringBootApplication*/
//@org.springframework.boot.autoconfigure.EnableAutoConfiguration(exclude={org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
/*public class Main
{*/
    /*@Component
    private static final class R2dbc
    {
        private JdbcTemplate jdbcTemplate;
        private Jdbc() throws Exception
        {
            final var config = new com.zaxxer.hikari.HikariConfig();
            final var dbUri = java.net.URI.create(System.getenv().containsKey("DATABASE_URL") ? System.getenv("DATABASE_URL") : "");
            config.setJdbcUrl(System.getenv().containsKey("DATABASE_URL") ? String.join("", "jdbc:postgresql://", dbUri.getHost(), ":", String.valueOf(dbUri.getPort()), dbUri.getPath()) : "jdbc:postgresql://echo.db.elephantsql.com:5432/yhsgqmtv");
            config.setUsername(System.getenv().containsKey("DATABASE_URL") ? dbUri.getUserInfo().split(":")[0] : "yhsgqmtv");
            config.setPassword(System.getenv().containsKey("DATABASE_URL") ? dbUri.getUserInfo().split(":")[1] : "TNksBpYp-8oJnWg2xIG_56zoDuDJ7y06");
            this.jdbcTemplate = new JdbcTemplate(new com.zaxxer.hikari.HikariDataSource(config));
            try (final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(new org.springframework.core.io.ClassPathResource("database.sql").getInputStream(), java.nio.charset.StandardCharsets.UTF_8)))
            {
                this.jdbcTemplate.update(reader.lines().collect(java.util.stream.Collectors.joining("\n")));
            }
        }
        
        public JdbcTemplate get()
        {
            return this.jdbcTemplate;
        }
        
        @javax.annotation.PreDestroy
        private void shutdown() throws Exception
        {
            this.jdbcTemplate.update("drop table productItem, productUnit, productReview");
        }
    }
    
    @Autowired private Jdbc jdbcTemplate;*/
    
    /*@PostMapping("/ajax") 
    private final java.util.List<java.util.Map<String, Object>> ajax(@RequestBody final java.util.Map<String, String> body) throws Exception
    {
        return this.jdbcTemplate.get().queryForList("select * from" + body.entrySet().stream().map($ -> String.join(" ", $.getKey(), $.getValue())).collect(java.util.stream.Collectors.joining(" ")));
    }
    
    @org.springframework.web.socket.config.annotation.EnableWebSocket
    private static final class WebSocketConfig implements org.springframework.web.socket.config.annotation.WebSocketConfigurer
    {
        private static final class WebSocker extends org.springframework.web.socket.handler.TextWebSocketHandler implements AutoCloseable
        {
            private static final java.util.Set<WebSocketSession> sessions = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());
            private static final ObjectMapper objectMapper = new ObjectMapper();
            @Override
            public void afterConnectionClosed(final WebSocketSession session, final org.springframework.web.socket.CloseStatus status) throws Exception
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
        public void registerWebSocketHandlers(final org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry registry)
        {
            try (final var webSocker = new WebSocker())
            {
                registry.addHandler(webSocker, "/ws");
            }
            catch (Exception error){}
        }
    }*/
    
    /*private static Mono<ServerResponse> main(final org.springframework.web.reactive.function.server.ServerRequest request)
    {
        try (final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(new org.springframework.core.io.ClassPathResource("static/login.html").getInputStream(), java.nio.charset.StandardCharsets.UTF_8)))
        {
            return ServerResponse.ok().contentType(org.springframework.http.MediaType.TEXT_HTML).bodyValue(reader.lines().collect(java.util.stream.Collectors.joining("\n")));
        }
        catch (Exception error){return Mono.empty();}
    }
    
    @org.springframework.context.annotation.Bean
    public org.springframework.web.reactive.function.server.RouterFunction<ServerResponse> route()
    {
        return org.springframework.web.reactive.function.server.RouterFunctions.route().GET("/", Main::main).build();
    }
    
    public static void main(final String[] args)
    {
        final var app = new org.springframework.boot.SpringApplication(Main.class);
        app.setDefaultProperties(java.util.Collections.singletonMap("server.port", System.getenv("PORT")));
        app.run(args);
    }
}*/

public class Server
{
    public static void main(final String[] args) throws Exception
    {
        final var vertx = io.vertx.core.Vertx.vertx();
        final var router = io.vertx.ext.web.Router.router(vertx);
        router.route("/").handler(request -> request.response().sendFile("login.html"));
        final var client = io.vertx.pgclient.PgPool.pool(vertx, System.getenv("DATABASE_URL").replace("postgres", "postgresql"));
        vertx.fileSystem().readFile("database.sql", ar -> client.query(ar.result().toString()).execute());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> client.query("drop table productItem, productUnit, productReview").execute()));
        router.route().handler(io.vertx.ext.web.handler.BodyHandler.create());
        router.route("/ajax").handler(request -> {
            client.query("select * from" + request.getBodyAsJson().stream().map($ -> String.join(" ", $.getKey(), $.getValue().toString())).collect(java.util.stream.Collectors.joining(" "))).execute(ar -> {
                final var json = new io.vertx.core.json.JsonArray();
                for (final var $: ar.result()) json.add($.toJson());
                request.json(json);});});
        router.route("/ws").handler(request -> request.request().toWebSocket(ar -> {
            ar.result().writeTextMessage("fuck you");}));
        router.route("/*").handler(io.vertx.ext.web.handler.StaticHandler.create("."));
        vertx.createHttpServer().requestHandler(router).listen(Integer.parseInt(System.getenv("PORT")));
    }
}

/*import aiohttp.web, asyncpg, json, aredis, aiokafka, asyncio, uvloop
asyncio.set_event_loop_policy(uvloop.EventLoopPolicy())

async def database(app):
    app.setdefault('database', await asyncpg.create_pool(host='postgres', user='postgres', database='postgres', password='postgres'))
    app.setdefault('cache', aredis.StrictRedis('redis').cache('cache'))
    #producer = aiokafka.AIOKafkaProducer(bootstrap_servers='kafka')
    #await producer.start()
    #await producer.send_and_wait('topic', b"Super message")
    #await producer.stop()
    yield
    await app.get('database').close()

async def post(request):
    body = await request.json()
    body = ' '.join(' '.join((key, str(value))) for key,value in body.items())
    records = await request.app.get('cache').get(body)
    if not records:
        async with request.app.get('database').acquire() as connection: records = json.dumps([*map(dict, await connection.fetch(f'select * from{body}'))], default=str)
        await request.app.get('cache').set(body, records)
    return aiohttp.web.Response(text=records)

app = aiohttp.web.Application()
app.add_routes([aiohttp.web.post('/ajax', post)])
app.cleanup_ctx.append(database)
aiohttp.web.run_app(app)*/
