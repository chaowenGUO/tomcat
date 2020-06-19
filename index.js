(async function()
{
    let response = await globalThis.fetch('footer.html')
    response = response.ok ? await response.text() : ''
    response = new globalThis.DOMParser().parseFromString(response, 'text/html').body.children
    while (response.length) globalThis.document.body.appendChild(response[0])
})()
