package hk.cand601.booking.integration

import hk.cand601.booking.model.BookOrderDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class BookingRabbitDispatcher() {

    // Could not autowire
    private val rabbitTemplate = RabbitTemplate()

    fun dispatchBookOrder(bookOrderDTO: BookOrderDTO) {
        rabbitTemplate.convertAndSend("booking_queue", bookOrderDTO)
    }
}