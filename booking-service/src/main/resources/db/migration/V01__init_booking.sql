create table book_orders
(
    id                 bigserial primary key,
    user_id            bigint,
    isbn               varchar(17),
    requested_location varchar(255),
    status             varchar(15),
    created_at         timestamp,
    updated_at         timestamp
);

