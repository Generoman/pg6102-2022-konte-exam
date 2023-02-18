package hk.cand601.shipping.integration

import hk.cand601.shipping.exception.EntityNotCreatedException
import hk.cand601.shipping.model.ShipmentEntity
import hk.cand601.shipping.service.ShippingService
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["booking_queue"])
class BookingRabbitListener(
    @Autowired private val shippingService: ShippingService,
    @Autowired private val shippingRabbitDispatcher: ShippingRabbitDispatcher
) {

    @RabbitHandler
    fun handleMessage(message: BookOrderDTO) {
        val shipment = ShipmentEntity(
            orderId = message.id,
            isbn = message.isbn,
            requestedLocation = message.requestedLocation,
            currentLocation = message.currentLocation
        )
        shippingService.registerShipment(shipment)?.let {
            shippingRabbitDispatcher.dispatchOrderId(it.id!!)
        }.run {
            throw EntityNotCreatedException("Shipment not created")
        }
    }
}

data class BookOrderDTO(
    val id: Long,
    val isbn: String,
    val requestedLocation: String,
    val currentLocation: String,
)