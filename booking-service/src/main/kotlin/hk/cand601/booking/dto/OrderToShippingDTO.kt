package hk.cand601.booking.dto

import java.time.LocalDateTime

data class OrderToShippingDTO(
    val id: Long,
    val userId: Long,
    val isbn: String,
    val requestedLocation: String,
    val currentLocation: String,
    val timeOrderedAt: LocalDateTime
)
