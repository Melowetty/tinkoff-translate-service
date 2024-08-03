package ru.melowetty.tinkofftranslateservice.bean

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class Beans {
    @Bean
    fun restTemplateBean(): RestTemplate {
        return RestTemplate()
    }
}