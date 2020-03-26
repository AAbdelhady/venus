create table if not exists offerings
(
    id         bigint primary key       not null,
    booking_id bigint                   not null,
    time       time with time zone      not null,
    created    timestamp with time zone not null,
    constraint offerings_booking_id_fk foreign key (booking_id) references bookings (id) on update cascade on delete cascade
);
create sequence if not exists offerings_seq increment by 50;
create index if not exists offerings_booking_id_index on offerings (booking_id);