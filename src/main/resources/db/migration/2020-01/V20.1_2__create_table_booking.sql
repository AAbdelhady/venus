create table if not exists bookings
(
    id          bigint primary key       not null,
    customer_id bigint                   not null,
    artist_id   bigint                   not null,
    message     varchar(255)             not null,
    created     timestamp with time zone not null,
    modified    timestamp with time zone not null,
    constraint bookings_customer_id_fk foreign key (customer_id) references customers (user_id) on update cascade on delete cascade,
    constraint bookings_artist_id_fk foreign key (artist_id) references artists (user_id) on update cascade on delete cascade
);
create sequence if not exists bookings_seq increment by 50;
create index if not exists bookings_customer_id_index ON bookings (customer_id);
create index if not exists bookings_artist_id_index ON bookings (artist_id);