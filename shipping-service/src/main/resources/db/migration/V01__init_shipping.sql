create table shipments
(
    id                 bigserial primary key,
    order_id           bigint,
    isbn               varchar(17),
    requested_location varchar(255),
    current_location   varchar(255),
    created_at         timestamp,
    updated_at         timestamp
)