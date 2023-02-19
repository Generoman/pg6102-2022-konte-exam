package hk.cand601.processing.controller

import hk.cand601.processing.dto.FromBookingDTO
import hk.cand601.processing.service.ProcessingService
import hk.cand601.processing.exception.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/processing")
class ProcessingController(
    @Autowired private val processingService: ProcessingService,
) {

    /**
     * For testing purposes
     * TODO: remove
     */
    @GetMapping("/happy")
    fun getHappy(): ResponseEntity<Any> {
        return ResponseEntity.ok().body("Happy! :)")
    }

    @PostMapping("/order")
    fun checkOrder(@RequestBody fromBookingDto: FromBookingDTO?): ResponseEntity<Any> {
        println("In checkOrder")
        when(fromBookingDto) {
            null -> throw BadRequestException("Order is null")
            else -> {
                processingService.processOrder(fromBookingDto)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw BadRequestException("Bad isbn")
                }
            }
        }
    }

    @PostMapping("/shipped")
    fun shipBook(@RequestBody isbn: String?): ResponseEntity<Any> {
        when(isbn) {
            null -> throw BadRequestException("Isbn is null")
            else -> {
                processingService.processShipment(isbn)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw BadRequestException("Bad isbn")
                }
            }
        }
    }
}