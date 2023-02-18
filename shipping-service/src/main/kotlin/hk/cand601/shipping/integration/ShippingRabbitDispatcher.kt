package hk.cand601.shipping.integration

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class ShippingRabbitDispatcher() {

    /**
     * Could not autowire
     */
    private val rabbitTemplate = RabbitTemplate()

    private val messageStart = "ShippedOrderId="

    fun dispatchOrderId(id: Long) {
        val message = "$messageStart$id"
        rabbitTemplate.convertAndSend("shipping_queue", message)
    }
}