package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController {

    @Value("\${WELCOME_MESSAGE:Hello from test}")
    private var message: String = ""

    constructor() {}

    constructor(message: String) {
        this.message = message
    }

    @GetMapping("/")
    fun sayHello(): String {
        return message
    }
}
