package hk.cand601.booking.dto

data class OrderToProcessingDTO(
    val isbn: String,
    val requestedLocation: String
)
