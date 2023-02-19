package hk.cand601.shipping.integration

import hk.cand601.shipping.dto.OrderDTO
import hk.cand601.shipping.exception.EntityNotCreatedException
import hk.cand601.shipping.model.ShipmentEntity
import hk.cand601.shipping.service.ShippingService
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@RabbitListener(queues = ["booking_queue"])
class BookingRabbitListener(
    @Autowired private val shippingService: ShippingService,
    @Autowired private val shippingRabbitDispatcher: ShippingRabbitDispatcher
) {

    @RabbitHandler
    fun handleMessage(message: OrderDTO) {
        val shipment = createShipmentFromDto(message)

        shippingService.registerShipment(shipment)?.let {
            shippingRabbitDispatcher.dispatchOrderId(it.id!!)
        }.run {
            throw EntityNotCreatedException("Shipment not created")
        }
    }

    fun createShipmentFromDto(dto: OrderDTO): ShipmentEntity {
        return ShipmentEntity(
            orderId = dto.id,
            userId = dto.userId,
            isbn = dto.isbn,
            requestedLocation = dto.requestedLocation,
            currentLocation = dto.currentLocation,
            timeOrderedAt = dto.timeOrderedAt
        )
    }
}