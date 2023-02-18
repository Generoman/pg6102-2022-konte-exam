create table books
(
    id         bigserial primary key,
    isbn       varchar(17),
    name       varchar(255),
    author     varchar(255),
    location   varchar(255),
    created_at timestamp,
    updated_at timestamp
);

insert into books (isbn, name, author, location, created_at, updated_at)
values ('978-0-321-12521-7',
        'Domain-Driven Design - Tackling Complexity at the Heart of Software',
        'Eric Evans',
        'Alphaville Public Library',
        now(),
        now());

insert into books (isbn, name, author, location, created_at, updated_at)
values ('978-0-7356-1967-8',
        'Code Complete - A Practical Handbook of Software Construction',
        'Steve McConnell',
        'Betatown School Library',
        now(),
        now());

insert into books (isbn, name, author, location, created_at, updated_at)
values ('978-0-13-475759-9',
        'Refactoring - Improving the Design of Existing Code',
        'Martin Fowler',
        'Gamma City Memorial Library',
        now(),
        now());

insert into books (isbn, name, author, location, created_at, updated_at)
values ('978-0-321-14653-3',
        'Test-Driven Development - By Example',
        'Kent Beck',
        'In shipping',
        now(),
        now());