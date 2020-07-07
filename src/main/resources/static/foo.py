import aiohttp, asyncio, json, os
async def f():
    async with aiohttp.ClientSession() as session:
        async with session.post('https://api.github.com/repos/chaowenGUO/springboot/dispatches', data=json.dumps({'event_type':'ping'}).encode(), auth=aiohttp.BasicAuth('chaowenGUO', os.getenv('TOKEN'))) as _: pass
        async with session.post('https://api.github.com/repos/chaowenGUO/koa/dispatches', data=json.dumps({'event_type':'ping'}).encode(), auth=aiohttp.BasicAuth('chaowenGUO', os.getenv('TOKEN'))) as _: pass

asyncio.run(f())
