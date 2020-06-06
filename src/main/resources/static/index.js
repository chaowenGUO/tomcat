import Vue from 'https://cdn.jsdelivr.net/npm/vue/dist/vue.esm.browser.min.js'
import * as lodash from 'https://cdn.jsdelivr.net/npm/lodash-es/lodash.min.js'

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
            const response = await fetch('/ajax', {method:'POST', body:JSON.stringify({name:this.name})})
            const text = response.ok ? await response.text() : ''
            this.name = text
        },
        add()
        {
            console.assert(lodash.isEqual([1, 2], [1, 2]))
            const worker = new Worker('worker.js', {type: 'module'})
            worker.postMessage([this.left, this.right])
            worker.onmessage = e => this.result = e.data
        }
    }
})
