package hu.bme.aut.blogapi.exception

import com.mongodb.MongoWriteException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [EntityNotFoundException::class])
    protected fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
                ex,
                ResponseErrorModel(ex.message, BlogApiErrors.ENTITY_NOT_FOUND_ERROR),
                HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request)
    }

    @ExceptionHandler(value = [MongoWriteException::class])
    protected fun handleMongoWriteException(ex: MongoWriteException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
                ex,
                ResponseErrorModel(ex.message, BlogApiErrors.MONGO_DB_ERROR),
                HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request)
    }
}