from aiohttp import web, WSMsgType
import os, pathlib, asyncpg, json

async def database(app):
    app.setdefault('database', await asyncpg.create_pool(os.environ.get('DATABASE_URL')))
    async with app.get('database').acquire() as connection: await connection.execute(pathlib.Path(__file__).resolve().parent.joinpath('database.sql').read_text())
    yield
    async with app.get('database').acquire() as connection: await connection.execute('drop table productItem, productUnit, productReview')
    await app.get('database').close()

async def post(request):
    body = await request.json()
    body = ' '.join(' '.join((key, str(value))) for key,value in body.items()).replace('table', '')
    async with request.app.get('database').acquire() as connection: records = await connection.fetch(f'select * from{body}')
    return web.Response(text=json.dumps([*map(dict, records)], default=str))

async def websocket(app):
    app.setdefault('websocket', set())
    yield
    for _ in app.get('websocket'): await _.close()
    
async def chat(request):
    websocket = web.WebSocketResponse()
    await websocket.prepare(request)
    name = await websocket.receive_str()
    for _ in request.app.get('websocket'): await _.send_str(json.dumps({'action':'join', 'name':name}))
    request.app.get('websocket').add(websocket)
    async for message in websocket:
        if message.type == WSMsgType.TEXT:
            for _ in request.app.get('websocket'):
                if _ is not websocket: await _.send_str(json.dumps({'action':'send', 'name': name, 'text':message.data}))
        else: break
    request.app.get('websocket').discard(websocket)
    for _ in request.app.get('websocket'): await _.send_str(json.dumps({'action':'disconnect', 'name':name}))
    return websocket

app = web.Application()
app.add_routes([web.get('/', lambda _: web.FileResponse(pathlib.Path(__file__).resolve().parent / 'login.html')),
                web.post('/ajax', post),
                web.get('/ws', chat),
                web.static('/', pathlib.Path(__file__).resolve().parent)])
app.cleanup_ctx.extend((database, websocket))
web.run_app(app, port=os.getenv('PORT'))
