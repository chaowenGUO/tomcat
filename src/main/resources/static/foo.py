import aiohttp, asyncio, json, os
async def f():
    async with aiohttp.ClientSession() as session:
        for _ in ('springboot', 'koa'):
            async with session.post(f'https://api.github.com/repos/chaowenGUO/{_}/dispatches', data=json.dumps({'event_type':'ping'}).encode(), auth=aiohttp.BasicAuth('chaowenGUO', os.getenv('GITHUB'))) as _: pass
       

asyncio.run(f())
