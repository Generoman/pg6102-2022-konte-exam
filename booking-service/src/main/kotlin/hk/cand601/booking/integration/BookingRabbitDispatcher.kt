package hk.cand601.booking.integration

import hk.cand601.booking.dto.OrderToShippingDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.stereotype.Service

@Service
class BookingRabbitDispatcher() {

    /**
     * Could not autowire
     */
    private val rabbitTemplate = RabbitTemplate()

    fun dispatchBookOrder(orderToShippingDTO: OrderToShippingDTO) {
        /**
         * Not sure if this is necessary
         * Does not work with, does not work without
         */
        rabbitTemplate.messageConverter = Jackson2JsonMessageConverter()

        rabbitTemplate.convertAndSend("booking_queue", orderToShippingDTO)
    }
}