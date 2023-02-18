package hk.cand601.booking.model

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
    fun toDto(): BookOrderDTO {
        return BookOrderDTO(id, isbn, status, requestedLocation)
    }
}

data class BookOrderDTO(
    val id: Long?,
    val isbn: String,
    var status: String,
    val requestedLocation: String,
    var currentLocation: String? = null
)