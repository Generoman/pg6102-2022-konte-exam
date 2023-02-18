package hk.cand601.booking.integration

import hk.cand601.booking.exception.BadRequestException
import hk.cand601.booking.exception.ServiceInterruptionException
import hk.cand601.booking.model.BookOrderDTO
import hk.cand601.booking.model.BookOrderEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ProcessingIntegrationService() {

    /**
     * Could not autowire
     */
    private val restTemplate = RestTemplate()
    val baseUrl = "http://processing-service/api/processing"

    fun checkAvailability(bookOrder: BookOrderEntity): BookOrderDTO? {
        bookOrder.id?.let {
            val url = "$baseUrl/order"
            val bookOrderDTO = bookOrder.toDto()

            try {
                restTemplate.postForEntity(url, bookOrderDTO, BookOrderDTO::class.java).body?.let {
                    return it
                }
            } catch (e: Exception) {
                throw ServiceInterruptionException("Processing Service could not be reached. Message: ${e.message}")
            }
        }.run {
            throw BadRequestException("Order missing id")
        }
    }

    fun updateToShipped(bookOrder: BookOrderEntity): BookOrderEntity {
        val url = "$baseUrl/shipped"

        try {
            restTemplate.postForEntity(url, bookOrder.isbn, Any::class.java).let {
                bookOrder.status = "Shipped"
                return bookOrder
            }
        } catch (e: Exception){
            throw ServiceInterruptionException("Processing Service could not be reached. Message: ${e.message}")
        }
    }

    fun isHappy(): Boolean {
        val url = "$baseUrl/happy"

        try {
            restTemplate.getForEntity(url, Any::class.java).let {
                return true
            }
        } catch (e: Exception) {
            return false
        }
    }
}