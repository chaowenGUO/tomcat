globalThis.onmessage = async _ =>
{
    const resultObject = await globalThis.WebAssembly.instantiateStreaming(globalThis.fetch('add.wasm'))
    globalThis.postMessage(_.data.reduce(resultObject.instance.exports._Z6simpleii))
}

//https://mbebenita.github.io/WasmExplorer/
//c++ code in the first red box//compile//pay attention to export in the second purple box//download as wasm file
//about:flag//Experimental Web Platform features//Parallel downloading
