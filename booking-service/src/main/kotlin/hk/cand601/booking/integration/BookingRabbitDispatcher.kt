package hk.cand601.booking.integration

import hk.cand601.booking.dto.OrderToShippingDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class BookingRabbitDispatcher() {

    /**
     * Could not autowire
     */
    private val rabbitTemplate = RabbitTemplate()

    fun dispatchBookOrder(orderToShippingDTO: OrderToShippingDTO) {
        rabbitTemplate.convertAndSend("booking_queue", orderToShippingDTO)
    }
}