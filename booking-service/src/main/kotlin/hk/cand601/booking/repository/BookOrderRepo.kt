package hk.cand601.booking.repository

import hk.cand601.booking.model.BookOrderEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface BookOrderRepo: PagingAndSortingRepository<BookOrderEntity, Long> {

    override fun findAll(): List<BookOrderEntity>

    override fun findAll(pageable: Pageable): Page<BookOrderEntity>
}