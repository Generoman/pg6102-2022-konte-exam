package hk.cand601.booking.model

import hk.cand601.booking.dto.OrderToProcessingDTO
import hk.cand601.booking.dto.OrderToShippingDTO
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class BookOrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(
        name = "order_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "isbn")
    val isbn: String,

    @Column(name = "requested_location")
    val requestedLocation: String,

    @Column(name = "status")
    var status: String = "Pending",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun toProcessingDto(): OrderToProcessingDTO {
        return OrderToProcessingDTO(isbn, requestedLocation)
    }

    fun toShippingDto(currentLocation: String): OrderToShippingDTO {
        return OrderToShippingDTO(id!!, userId, isbn, requestedLocation, currentLocation, createdAt)
    }
}