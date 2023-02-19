package hk.cand601.shipping.controller

import hk.cand601.shipping.exception.BadRequestException
import hk.cand601.shipping.exception.EntityNotFoundException
import hk.cand601.shipping.model.ShipmentEntity
import hk.cand601.shipping.service.ShippingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shipping")
class ShippingController(
    @Autowired private val shippingService: ShippingService,
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
}