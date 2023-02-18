package hk.cand601.processing.service

import hk.cand601.processing.model.enum.Location
import hk.cand601.processing.model.enum.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProcessingService(@Autowired private val bookService: BookService) {

    fun processOrder(orderDTO: OrderDTO): OrderDTO? {
        bookService.getBookByIsbn(orderDTO.isbn)?.let {
            if (orderDTO.requestedLocation == it.location) {
                orderDTO.currentLocation = it.location
                orderDTO.status = OrderStatus.COMPLETED
                return orderDTO
            }
            if (it.location == Location.IN_SHIPPING) {
                orderDTO.status = OrderStatus.UNAVAILABLE
                return orderDTO
            }
            orderDTO.currentLocation = it.location
            orderDTO.status = OrderStatus.REGISTERED
            return orderDTO
        }
        return null
    }

    fun processShipment(isbn: String): ShipmentDTO? {
        bookService.getBookByIsbn(isbn)?.let {
            it.location = Location.IN_SHIPPING
            bookService.updateBook(it)!!
            return ShipmentDTO(isbn, Location.IN_SHIPPING)
        }
        return null
    }
}

data class OrderDTO(
    val id: Long,
    val isbn: String,
    var status: OrderStatus,
    val requestedLocation: Location,
    var currentLocation: Location?

)

data class ShipmentDTO(
    val isbn: String,
    val location: Location
)