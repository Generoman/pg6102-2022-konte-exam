package hk.cand601.booking.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ResponseEntity<ErrorDTO> {
        val message = exception.message?: "Entity not found"
        val status = HttpStatus.NOT_FOUND
        return ResponseEntity.status(status).body(ErrorDTO(status, message))
    }

    @ExceptionHandler
    fun handleServiceInterruptionException(exception: ServiceInterruptionException): ResponseEntity<ErrorDTO> {
        val message = exception.message?: "Service interrupted"
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(status).body(ErrorDTO(status, message))
    }

    @ExceptionHandler
    fun handleBadRequestException(exception: BadRequestException): ResponseEntity<ErrorDTO> {
        val message = exception.message?: "Bad request"
        val status = HttpStatus.BAD_REQUEST
        return ResponseEntity.status(status).body(ErrorDTO(status, message))
    }

    @ExceptionHandler
    fun handleEntityNotUpdatedException(exception: EntityNotUpdatedException): ResponseEntity<ErrorDTO> {
        val message = exception.message?: "Error updating entity"
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(status).body(ErrorDTO(status, message))
    }

    @ExceptionHandler
    fun handleEntityNotCreatedException(exception: EntityNotCreatedException): ResponseEntity<ErrorDTO> {
        val message = exception.message?: "Error creating entity"
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(status).body(ErrorDTO(status, message))
    }
}

data class ErrorDTO(val status: HttpStatus, val message: String)