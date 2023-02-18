package hk.cand601.shipping.repository

import hk.cand601.shipping.model.ShipmentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface ShipmentRepo: PagingAndSortingRepository<ShipmentEntity, Long> {

    override fun findAll(): List<ShipmentEntity>

    override fun findAll(pageable: Pageable): Page<ShipmentEntity>
}