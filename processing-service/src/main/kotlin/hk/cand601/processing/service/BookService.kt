package hk.cand601.processing.service

import hk.cand601.processing.model.BookEntity
import hk.cand601.processing.repository.BookRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BookService(@Autowired val repo: BookRepo) {

    fun getBooks(): List<BookEntity> {
        return repo.findAll()
    }

    fun getBooks(page: Int): Page<BookEntity> {
        return repo.findAll(Pageable.ofSize(10).withPage(page))
    }

    fun getBookById(id: Long): BookEntity? {
        return repo.findByIdOrNull(id)
    }

    fun getBookByIsbn(isbn: String): BookEntity? {
        return repo.findByIsbn(isbn)
    }

    fun addBook(book: BookEntity): BookEntity? {
        return repo.save(book)
    }

    fun updateBook(bookEntity: BookEntity): BookEntity? {
        bookEntity.updatedAt = LocalDateTime.now()
        return repo.save(bookEntity)
    }

    fun deleteBook(id: Long) {
        repo.deleteById(id)
    }
}