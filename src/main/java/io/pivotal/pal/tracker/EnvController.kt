package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EnvController {

    @Value("\${PORT: NOT SET}")
    var port: String = ""

    @Value("\${MEMORY_LIMIT: NOT SET}")
    var memoryLimit: String = ""

    @Value("\${CF_INSTANCE_INDEX: NOT SET}")
    var instanceId: String = ""

    @Value("\${CF_INSTANCE_ADDR: NOT SET}")
    var instanceAddress: String = ""

    constructor()
    constructor(port: String, memoryLimit: String, instanceId: String, instanceAddress: String) {
        this.port = port
        this.memoryLimit = memoryLimit
        this.instanceId = instanceId
        this.instanceAddress = instanceAddress
    }

    @GetMapping("/env")
    fun getEnv(): Map<String, String> {
        return mapOf<String, String>(
                Pair("PORT", port),
                Pair("MEMORY_LIMIT",memoryLimit),
                Pair("CF_INSTANCE_INDEX", instanceId),
                Pair("CF_INSTANCE_ADDR", instanceAddress)
        )
    }
}
