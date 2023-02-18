package hk.cand601.booking.integration

import hk.cand601.booking.exception.EntityNotUpdatedException
import hk.cand601.booking.exception.ServiceInterruptionException
import hk.cand601.booking.service.BookingService
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@RabbitListener(queues = ["shipping_queue"])
class ShippingRabbitListener(
    @Autowired private val bookingService: BookingService,
    @Autowired private val processingIntegrationService: ProcessingIntegrationService
) {

    private val messageStart = "ShippedOrderId="

    @RabbitHandler
    fun handleMessage(message: String) {
        /**
         * Pseudo-log
         */
        println("From shipping_queue: $message")

        val id = message.substring(messageStart.length).toLong()
        bookingService.getBookOrder(id)?.let { bookOrder ->
            processingIntegrationService.updateToShipped(bookOrder)?.let {
                bookingService.updateBookOrder(it)
            }.run {
                throw EntityNotUpdatedException("Book order with id ${bookOrder.id} not updated")
            }
        }.run {
            throw ServiceInterruptionException("Processing service interrupted")
        }
    }
}