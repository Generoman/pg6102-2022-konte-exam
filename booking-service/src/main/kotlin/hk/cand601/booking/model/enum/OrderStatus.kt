package hk.cand601.booking.model.enum

enum class OrderStatus(val status: String) {
    PENDING("Pending"),
    REGISTERED("Registered"),
    UNAVAILABLE("Unavailable"),
    SHIPPED("Shipped"),
    COMPLETED("Completed")
}