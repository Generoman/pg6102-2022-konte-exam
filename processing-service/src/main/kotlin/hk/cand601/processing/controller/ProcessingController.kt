package hk.cand601.processing.controller

import hk.cand601.processing.dto.FromBookingDTO
import hk.cand601.processing.service.ProcessingService
import hk.cand601.processing.exception.BadRequestException
import hk.cand601.processing.exception.EntityNotCreatedException
import hk.cand601.processing.exception.EntityNotFoundException
import hk.cand601.processing.model.BookEntity
import hk.cand601.processing.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/processing")
class ProcessingController(
    @Autowired private val processingService: ProcessingService,
    @Autowired private val bookService: BookService
) {

    /**
     * For testing purposes
     * TODO: remove
     */
    @GetMapping("/happy")
    fun getHappy(): ResponseEntity<Any> {
        return ResponseEntity.ok().body("Happy! :)")
    }

    @PostMapping("/order")
    fun checkOrder(@RequestBody fromBookingDto: FromBookingDTO?): ResponseEntity<Any> {
        println("In checkOrder")
        when(fromBookingDto) {
            null -> throw BadRequestException("Order is null")
            else -> {
                processingService.processOrder(fromBookingDto)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw BadRequestException("Bad isbn")
                }
            }
        }
    }

    @PostMapping("/shipped")
    fun shipBook(@RequestBody isbn: String?): ResponseEntity<Any> {
        when(isbn) {
            null -> throw BadRequestException("Isbn is null")
            else -> {
                processingService.processShipment(isbn)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw BadRequestException("Bad isbn")
                }
            }
        }
    }

    @GetMapping("/books")
    fun getAllBooks(): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.ok().body(bookService.getBooks())
    }

    @GetMapping("/books/page/{page}")
    fun getBooks(@PathVariable page: Int): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.ok().body(bookService.getBooks(page).toList())
    }

    @GetMapping("/books/id/{id}")
    fun getBookById(@PathVariable id: Long?): ResponseEntity<Any> {
        when(id) {
            null -> throw BadRequestException("Id is null")
            else -> {
                bookService.getBookById(id)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw EntityNotFoundException("Book with id $id not found")
                }
            }
        }
    }

    @GetMapping("/books/isbn/{isbn}")
    fun getBookByIsbn(@PathVariable isbn: String?): ResponseEntity<Any> {
        when(isbn) {
            null -> throw BadRequestException("Isbn is null")
            else -> {
                bookService.getBookByIsbn(isbn)?.let {
                    return ResponseEntity.ok().body(it)
                }.run {
                    throw EntityNotFoundException("Book with ISBN $isbn not found")
                }
            }
        }
    }

    @PostMapping("/books")
    fun postBook(@RequestBody book: BookEntity): ResponseEntity<Any> {
        bookService.addBook(book)?.let {
            return ResponseEntity.ok().body(it)
        }.run {
            throw EntityNotCreatedException("Book not added")
        }
    }

    @PostMapping("/books/delete/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<String> {
        bookService.deleteBook(id)
        return ResponseEntity.ok().body("Book with id $id deleted")
    }
}