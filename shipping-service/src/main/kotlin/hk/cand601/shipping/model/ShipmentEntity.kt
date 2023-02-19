package hk.cand601.shipping.model

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

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "isbn")
    val isbn: String,

    @Column(name = "requested_location")
    val requestedLocation: String,

    @Column(name = "current_location")
    val currentLocation: String,

    @Column(name = "time_ordered_at")
    val timeOrderedAt: LocalDateTime,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
}