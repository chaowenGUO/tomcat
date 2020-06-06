globalThis.onmessage = async e =>
{
    const resultObject = await WebAssembly.instantiateStreaming(fetch('add.wasm'))
    globalThis.postMessage(e.data.reduce(resultObject.instance.exports._Z6simpleii))
}

//https://mbebenita.github.io/WasmExplorer/
//c++ code in the first red box//compile//pay attention to export in the second purple box//download as wasm file
//about:flag//Experimental Web Platform features//Parallel downloading
