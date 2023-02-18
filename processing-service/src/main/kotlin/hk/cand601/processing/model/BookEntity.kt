package hk.cand601.processing.model

import hk.cand601.processing.model.enum.Location
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "books")
class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(
        name = "book_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "isbn")
    val isbn: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "author")
    val author: String,

    @Column(name = "location")
    var location: Location,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
}