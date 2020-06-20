(async function()
{
    let response = await globalThis.fetch('footer.html')
    response = response.ok ? await response.text() : ''
    response = new globalThis.DOMParser().parseFromString(response, 'text/html').body
    globalThis.customElements.define('footer-description',
        class extends globalThis.HTMLElement
        {
            constructor()
	    {
                super()
                this.attachShadow({mode:'closed'}).appendChild(response.querySelector('template').content)
	        this.querySelectorAll('span').forEach(_ => _.style.display = 'contents')
	        this.querySelector('span[slot=""]').innerHTML = ['<a>', this.querySelector('span[slot=""]').textContent.replace(/ /g, '</a><a>'), '</a>'].join('')
	    }
        })
    response = response.children
    while (response.length) globalThis.document.body.appendChild(response[0])
})()
