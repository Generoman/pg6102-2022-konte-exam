package hk.cand601.booking.service

import hk.cand601.booking.model.BookOrderEntity
import hk.cand601.booking.repository.BookOrderRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BookingService(@Autowired private val repo: BookOrderRepo) {

    fun getBookOrders(): List<BookOrderEntity> {
        return repo.findAll()
    }

    fun getBookOrders(page: Int): Page<BookOrderEntity> {
        return repo.findAll(Pageable.ofSize(10).withPage(page))
    }

    fun getBookOrder(id: Long): BookOrderEntity? {
        return repo.findByIdOrNull(id)
    }

    fun registerBookOrder(bookOrderEntity: BookOrderEntity): BookOrderEntity? {
        return repo.save(bookOrderEntity)
    }

    fun updateBookOrder(bookOrderEntity: BookOrderEntity): BookOrderEntity? {
        bookOrderEntity.updatedAt = LocalDateTime.now()
        return repo.save(bookOrderEntity)
    }

    fun deleteBookOrder(id: Long) {
        repo.deleteById(id)
    }
}