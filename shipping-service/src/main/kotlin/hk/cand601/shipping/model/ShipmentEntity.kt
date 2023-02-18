package hk.cand601.shipping.model

import hk.cand601.shipping.model.enum.Location
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "shipments")
class ShipmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_id_seq")
    @SequenceGenerator(
        name = "shipment_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "isbn")
    val isbn: String,

    @Column(name = "requested_location")
    val requestedLocation: Location,

    @Column(name = "current_location")
    val currentLocation: Location,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
}