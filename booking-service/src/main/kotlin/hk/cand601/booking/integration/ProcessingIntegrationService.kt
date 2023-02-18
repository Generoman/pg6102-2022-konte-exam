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
            println(bookOrderDTO)
            try {
                println("Trying to contact ProcessService...")
                val response = restTemplate.postForEntity(url, bookOrderDTO, BookOrderDTO::class.java)
                val responseBody = response.body
                println(responseBody)
                responseBody?.let {
                    return it
                }
            } catch (e: Exception) {
                throw ServiceInterruptionException("Processing Service could not be reached. Message: ${e.message}")
            }
        }.run {
            throw BadRequestException("Entity missing id")
        }
    }

    fun updateToShipped(bookOrder: BookOrderEntity): BookOrderEntity? {
        val url = "$baseUrl/shipped"

        try {
            val response = restTemplate.postForEntity(url, bookOrder.isbn, ShipmentDTO::class.java)
            val responseBody = response.body

            responseBody?.let {
                bookOrder.status = "Shipped"
                return bookOrder
            }
        } catch (e: Exception){
            throw ServiceInterruptionException("Processing Service interrupted. Message: ${e.message}")
        }

        return null
    }
}

data class ShipmentDTO(
    val isbn: String,
    val location: String
)