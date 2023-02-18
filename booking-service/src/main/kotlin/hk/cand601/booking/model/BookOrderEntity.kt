package hk.cand601.booking.model

import hk.cand601.booking.model.enum.OrderStatus
import hk.cand601.booking.model.enum.Location
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class BookOrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_order_id_seq")
    @SequenceGenerator(
        name = "book_order_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "isbn")
    val isbn: String,

    @Column(name = "requested_location")
    val requestedLocation: Location,

    @Column(name = "status")
    var status: OrderStatus = OrderStatus.PENDING,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDto(): BookOrderDTO {
        return BookOrderDTO(id, isbn, status, requestedLocation)
    }
}

data class BookOrderDTO(
    val id: Long?,
    val isbn: String,
    var status: OrderStatus,
    val requestedLocation: Location,
    var currentLocation: Location? = null
)