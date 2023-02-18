package hk.cand601.processing.repository

import hk.cand601.processing.model.BookEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

interface BookRepo: PagingAndSortingRepository<BookEntity, Long> {

    override fun findAll(): List<BookEntity>

    override fun findAll(pageable: Pageable): Page<BookEntity>

    fun findByIsbn(isbn: String): BookEntity?
}