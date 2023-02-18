package hk.cand601.booking.controller

import hk.cand601.booking.exception.BadRequestException
import hk.cand601.booking.exception.EntityNotCreatedException
import hk.cand601.booking.exception.EntityNotFoundException
import hk.cand601.booking.exception.ServiceInterruptionException
import hk.cand601.booking.integration.BookingRabbitDispatcher
import hk.cand601.booking.integration.ProcessingIntegrationService
import hk.cand601.booking.model.BookOrderEntity
import hk.cand601.booking.service.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/booking")
class BookingController(
    @Autowired private val bookingService: BookingService,
    @Autowired private val processingIntegrationService: ProcessingIntegrationService,
    @Autowired private val bookingRabbitDispatcher: BookingRabbitDispatcher
) {
    /**
     * For testing purposes
     */
    @GetMapping("/happy")
    fun getHappyPath(): ResponseEntity<Any> {
        if(processingIntegrationService.isHappy()) {
            return ResponseEntity.ok().body("Happy! :)")
        }
        return ResponseEntity.ok().body("Not happy. :(")
    }

    @PostMapping("/echo")
    fun getEcho(@RequestBody any: Any): ResponseEntity<Any> {
        return ResponseEntity.ok().body(any)
    }

    @GetMapping("")
    fun getAllOrders(): ResponseEntity<List<BookOrderEntity>> {
        return ResponseEntity.ok().body(bookingService.getBookOrders())
    }

    @GetMapping("/page/{page}")
    fun getOrders(@PathVariable page: Int): ResponseEntity<List<BookOrderEntity>> {
        return ResponseEntity.ok().body(bookingService.getBookOrders(page).toList())
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long?): ResponseEntity<Any> {
        when(id) {
            null -> throw BadRequestException("Id is null")
            else -> {
                bookingService.getBookOrder(id)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw EntityNotFoundException("Order with id $id not found")
                }
            }
        }
    }

    @PostMapping("")
    fun createOrder(@RequestBody bookOrder: BookOrderEntity?): ResponseEntity<Any> {
        when(bookOrder) {
            null -> throw BadRequestException("Order is null")
            else -> {
                bookingService.registerBookOrder(bookOrder)?.let { registeredBookOrder ->
                    processingIntegrationService.checkAvailability(registeredBookOrder)?.let {
                        registeredBookOrder.status = it.status
                        val updatedBookOrder = bookingService.updateBookOrder(registeredBookOrder)
                        bookingRabbitDispatcher.dispatchBookOrder(it)
                        return ResponseEntity.ok().body(updatedBookOrder)
                    }.run {
                        throw ServiceInterruptionException("Something went wrong")
                    }
                }.run {
                    throw EntityNotCreatedException("Order not created")
                }
            }
        }
    }
}