import Vue from 'https://cdn.jsdelivr.net/npm/vue/dist/vue.esm.browser.min.js'

class A
{
    #a
    constructor(a)
    {
        this.#a = a
    }
    get a()
    {
        return this.#a
    }
    set a(a) 
    {
        this.#a = a
    }
}   

new Vue({
    el: '#app',
    data:
    {
        name: 'first',
        left: 0,
        right: 0,
        result: 0
    },
    methods:
    {
        async loadDoc()
        {
            const response = await fetch('/ajax', {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify({name:this.name})})
            const text = response.ok ? await response.text() : ''
            this.name = text
        },
        add()
        {
            const worker = new Worker('worker.js', {type: 'module'})
            worker.postMessage([this.left, this.right])
            worker.onmessage = e => this.result = e.data
        }
    }
})
