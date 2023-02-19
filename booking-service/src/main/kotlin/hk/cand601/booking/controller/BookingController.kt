package hk.cand601.booking.controller

import hk.cand601.booking.dto.FromProcessingDTO
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
     * TODO: remove
     */
    @GetMapping("/happy")
    fun getHappyPath(): ResponseEntity<Any> {
        return ResponseEntity.ok().body("Happy! :)")
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
    fun createOrder(@RequestBody newOrder: BookOrderEntity?): ResponseEntity<Any> {
        when(newOrder) {
            null -> throw BadRequestException("Order is null")
            else -> {
                bookingService.registerBookOrder(newOrder)?.let { registeredOrder ->
                    processingIntegrationService.checkAvailability(registeredOrder)?.let {
                        registeredOrder.status = it.status
                        val updatedOrder = bookingService.updateBookOrder(registeredOrder)

                        if(it.status == "Registered") {
                            val orderForShippingDto = updatedOrder!!.toShippingDto(it.currentLocation)
                            bookingRabbitDispatcher.dispatchBookOrder(orderForShippingDto)
                        }

                        return ResponseEntity.ok().body(updatedOrder)
                    }.run {
                        throw ServiceInterruptionException("Something went wrong")
                    }
                }.run {
                    throw EntityNotCreatedException("Order not created")
                }
            }
        }
    }

    /**
     * Workaround because calls between services didn't work
     * Should strictly be UPDATE, not PUT, with how I'm using it
     * Still not working because of serialization problems
     */
    @PutMapping("/ship/{id}")
    fun shipOrder(@RequestBody fromProcessingDTO: FromProcessingDTO, @PathVariable id: Long): ResponseEntity<String> {
        bookingService.getBookOrder(id)?.let {
            it.status = fromProcessingDTO.status
            val updatedOrder = bookingService.updateBookOrder(it)!!
            if(it.status == "Registered") {
                val orderForShippingDto = updatedOrder.toShippingDto(fromProcessingDTO.currentLocation)
                bookingRabbitDispatcher.dispatchBookOrder(orderForShippingDto)
                return ResponseEntity.ok().body("Order sent to shipping")
            }
            return ResponseEntity.ok().body("Order could not be shipped")
        }.run {
            throw EntityNotFoundException("Order with id $id not found")
        }
    }

    @PostMapping("/delete/{id}")
    fun deleteOrder(@PathVariable id: Long): ResponseEntity<String> {
        bookingService.deleteBookOrder(id)
        return ResponseEntity.ok().body("Order with id $id deleted")
    }
}