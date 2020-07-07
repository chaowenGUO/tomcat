let websocket = null
       function log(msg) {
         const control = globalThis.document.querySelector('div')
         control.innerHTML = control.innerHTML + new globalThis.Date + msg + '<br>'
         //control.scrollTop(control.scrollTop() + 1000)
       }

globalThis.document.querySelector('button').onclick = () =>
{
    if (globalThis.Object.is(websocket, null))
    {
        websocket = new globalThis.WebSocket(globalThis.location.origin.replace('http', 'ws') + '/ws')
        websocket.onopen = () =>
        {
            const name = globalThis.document.querySelector('input')
            websocket.send(name.value)
            log('Connected as ' + name.value)
            name.disabled = true
            globalThis.document.querySelector('button').textContent = 'Disconnect'
            globalThis.document.querySelector('div+input+button').disabled = false
            globalThis.document.querySelector('div+input').disabled = false
        }
        websocket.onmessage = event =>
        {
            const data = globalThis.JSON.parse(event.data)
            switch (data.action)
            {
                case 'disconnect':
                    log('Disconnected ' + data.name); break
                case 'join':
                    log('Joined ' + data.name); break
                case 'send':
                    log(data.name + ': ' + data.text); break
            }
        }
        websocket.onclose = () =>
        {
            log('Disconnected.')
            websocket = null
            const name = globalThis.document.querySelector('input')
            name.disabled = false
            name.value = ''
            globalThis.document.querySelector('button').textContent = 'Connect'
            globalThis.document.querySelector('div+input+button').disabled = true
            globalThis.document.querySelector('div+input').disabled = true
        }
    }
    else websocket.close()
})
    
globalThis.document.querySelector('div+input+button').onclick = () =>
{
    const text = globalThis.document.querySelector('div+input')
    log(text.value)
    websocket.send(text.value)
    text.value = ''
    text.focus()
})
