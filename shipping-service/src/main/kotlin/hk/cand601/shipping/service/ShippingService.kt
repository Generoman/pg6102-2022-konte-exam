package hk.cand601.shipping.service

import hk.cand601.shipping.model.ShipmentEntity
import hk.cand601.shipping.repository.ShipmentRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ShippingService(@Autowired private val repo: ShipmentRepo) {

    fun getShipments(): List<ShipmentEntity> {
        return repo.findAll()
    }

    fun getShipments(page: Int): Page<ShipmentEntity> {
        return repo.findAll(Pageable.ofSize(10).withPage(page))
    }

    fun getShipment(id: Long): ShipmentEntity? {
        return repo.findByIdOrNull(id)
    }

    fun registerShipment(shipment: ShipmentEntity): ShipmentEntity? {
        return repo.save(shipment)
    }

    fun updateShipment(shipment: ShipmentEntity): ShipmentEntity? {
        shipment.updatedAt = LocalDateTime.now()
        return repo.save(shipment)
    }

    fun deleteShipment(id: Long) {
        repo.deleteById(id)
    }
}