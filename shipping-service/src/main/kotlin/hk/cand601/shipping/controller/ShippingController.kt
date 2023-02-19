package hk.cand601.shipping.controller

import hk.cand601.shipping.dto.OrderDTO
import hk.cand601.shipping.exception.BadRequestException
import hk.cand601.shipping.exception.EntityNotFoundException
import hk.cand601.shipping.integration.BookingRabbitListener
import hk.cand601.shipping.model.ShipmentEntity
import hk.cand601.shipping.service.ShippingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shipping")
class ShippingController(
    @Autowired private val shippingService: ShippingService,
    @Autowired private val bookingRabbitListener: BookingRabbitListener
) {
    /**
     * For testing purposes
     */
    @GetMapping("/happy")
    fun getHappyPath(): ResponseEntity<Any> {
        return ResponseEntity.ok().body("Happy! :)")
    }

    @GetMapping("")
    fun getAllShipments(): ResponseEntity<List<ShipmentEntity>> {
        return ResponseEntity.ok().body(shippingService.getShipments())
    }

    @GetMapping("/page/{page}")
    fun getShipments(@PathVariable page: Int): ResponseEntity<List<ShipmentEntity>> {
        return ResponseEntity.ok().body(shippingService.getShipments(page).toList())
    }

    @GetMapping("/{id}")
    fun getShipment(@PathVariable id: Long?): ResponseEntity<Any> {
        when(id) {
            null -> throw BadRequestException("Shipment id is null")
            else -> {
                shippingService.getShipment(id)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw EntityNotFoundException("Shipment with id $id not found")
                }
            }
        }
    }

    /**
     * Workaround because message serialization failed
     */
    @PostMapping("")
    fun postShipment(@RequestBody orderDto: OrderDTO): ResponseEntity<String> {
        bookingRabbitListener.handleMessage(orderDto)
        return ResponseEntity.ok().body("Order shipped")
    }

    @PostMapping("/delete/{id}")
    fun deleteShipment(@PathVariable id: Long): ResponseEntity<String> {
        shippingService.deleteShipment(id)
        return ResponseEntity.ok().body("Shipment with id $id deleted")
    }
}