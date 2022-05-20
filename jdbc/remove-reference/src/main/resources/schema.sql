

create table address
(
    id identity primary key,
    text varchar(200)
);

create table person
(
    id identity primary key,
    name varchar(200)
);

create table person_address
(
    person_id bigint references person(id),
    address_id bigint references address(id)
);
