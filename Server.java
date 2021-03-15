public class Server
{
    public static void main(final String[] args) throws Exception
    {
        final var vertx = io.vertx.core.Vertx.vertx();
        final var router = io.vertx.ext.web.Router.router(vertx);
        router.route("/ajax").handler(request -> request.response().sendFile("login.html"));
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
