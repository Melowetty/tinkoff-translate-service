package ru.melowetty.tinkofftranslateservice.controller

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ErrorResponse {
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.message!!)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ErrorResponse {
        return ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: "")
    }
}