import requests, json, os
requests.post('https://api.github.com/repos/chaowenGUO/springboot/dispatches', data=json.dumps({'event_type':'ping'}), auth=('chaowenGUO', os.getenv('TOKEN')))
