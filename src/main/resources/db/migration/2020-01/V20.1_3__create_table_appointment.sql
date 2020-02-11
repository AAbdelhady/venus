create table if not exists appointments
(
    id               bigint PRIMARY KEY       not null,
    customer_id      bigint                   not null,
    artist_id        bigint                   not null,
    appointment_time timestamp with time zone not null,
    created          timestamp with time zone not null,
    modified         timestamp with time zone not null,
    constraint appointments_customer_id_fk foreign key (customer_id) references customers (user_id) on update cascade on delete cascade,
    constraint appointments_artist_id_fk foreign key (artist_id) references artists (user_id) on update cascade on delete cascade
);
create sequence if not exists appointments_seq increment by 50;
create index if not exists appointments_customer_id_index on appointments (customer_id);
create index if not exists appointments_artist_id_index on appointments (artist_id);