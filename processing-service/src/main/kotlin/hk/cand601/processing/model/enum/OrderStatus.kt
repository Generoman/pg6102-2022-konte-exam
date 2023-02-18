package hk.cand601.processing.model.enum

enum class OrderStatus(val status: String) {
    PENDING("Pending"),
    REGISTERED("Registered"),
    UNAVAILABLE("Unavailable"),
    COMPLETED("Completed")
}