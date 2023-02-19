package hk.cand601.processing.service

import hk.cand601.processing.dto.FromBookingDTO
import hk.cand601.processing.dto.LocationChangeDTO
import hk.cand601.processing.dto.ToBookingDTO
import hk.cand601.processing.exception.EntityNotFoundException
import hk.cand601.processing.exception.EntityNotUpdatedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProcessingService(@Autowired private val bookService: BookService) {

    fun processOrder(fromBookingDTO: FromBookingDTO): ToBookingDTO? {
        val toBookingDTO = ToBookingDTO(null, null)
        bookService.getBookByIsbn(fromBookingDTO.isbn)?.let {

            toBookingDTO.currentLocation = it.location

            if (fromBookingDTO.requestedLocation == it.location) {
                toBookingDTO.status = "Completed"
                return toBookingDTO
            }

            if (it.location == "In shipping") {
                toBookingDTO.status = "Unavailable"
                return toBookingDTO
            }

            toBookingDTO.status = "Registered"

            return toBookingDTO
        }
        return null
    }

    fun processShipment(isbn: String): LocationChangeDTO? {
        bookService.getBookByIsbn(isbn)?.let { book ->
            book.location = "In shipping"
            bookService.updateBook(book)?.let {
                return LocationChangeDTO(it.isbn, "In shipping")
            }.run {
                throw EntityNotUpdatedException("Book not updated")
            }
        }.run {
            throw EntityNotFoundException("Book with isbn $isbn not found")
        }
    }
}