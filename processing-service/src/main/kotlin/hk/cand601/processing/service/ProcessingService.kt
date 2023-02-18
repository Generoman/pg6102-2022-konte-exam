package hk.cand601.processing.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProcessingService(@Autowired private val bookService: BookService) {

    fun processOrder(orderDTO: OrderDTO): OrderDTO? {
        bookService.getBookByIsbn(orderDTO.isbn)?.let {
            if (orderDTO.requestedLocation == it.location) {
                orderDTO.currentLocation = it.location
                orderDTO.status = "Completed"
                return orderDTO
            }
            if (it.location == "In shipping") {
                orderDTO.status = "Unavailable"
                return orderDTO
            }
            orderDTO.currentLocation = it.location
            orderDTO.status = "Registered"
            return orderDTO
        }
        return null
    }

    fun processShipment(isbn: String): ShipmentDTO? {
        bookService.getBookByIsbn(isbn)?.let {
            it.location = "In shipping"
            bookService.updateBook(it)!!
            return ShipmentDTO(isbn, "In shipping")
        }
        return null
    }
}

data class OrderDTO(
    val id: Long,
    val isbn: String,
    var status: String,
    val requestedLocation: String,
    var currentLocation: String?

)

data class ShipmentDTO(
    val isbn: String,
    val location: String
)