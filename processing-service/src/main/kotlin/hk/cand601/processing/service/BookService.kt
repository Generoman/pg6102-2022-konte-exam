package hk.cand601.processing.service

import hk.cand601.processing.model.BookEntity
import hk.cand601.processing.repository.BookRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BookService(@Autowired val repo: BookRepo) {
    fun getBookByIsbn(isbn: String): BookEntity? {
        return repo.findByIsbn(isbn)
    }

    fun updateBook(bookEntity: BookEntity): BookEntity? {
        bookEntity.updatedAt = LocalDateTime.now()
        return repo.save(bookEntity)
    }
}