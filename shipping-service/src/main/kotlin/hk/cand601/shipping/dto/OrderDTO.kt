package hk.cand601.shipping.dto

import java.time.LocalDateTime

data class OrderDTO(
    val id: Long,
    val userId: Long,
    val isbn: String,
    val requestedLocation: String,
    val currentLocation: String,
    val timeOrderedAt: LocalDateTime
)
