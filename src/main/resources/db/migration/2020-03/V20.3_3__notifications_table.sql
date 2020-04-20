create table if not exists notifications
(
    id             bigint primary key       not null,
    receiver_id    bigint                   not null,
    sender_id      bigint,
    title          varchar(256)             not null,
    body           text                     not null,
    type           varchar(64)              not null,
    booking_id     bigint,
    appointment_id bigint,
    created        timestamp with time zone not null,
    constraint notifications_receiver_id_fk foreign key (receiver_id) references users (id) on update cascade on delete cascade,
    constraint notifications_sender_id_fk foreign key (sender_id) references users (id) on update cascade on delete cascade,
    constraint notifications_booking_id_fk foreign key (booking_id) references bookings (id) on update cascade on delete cascade,
    constraint notifications_appointment_id_fk foreign key (appointment_id) references appointments (id) on update cascade on delete cascade
);
create sequence if not exists notifications_seq increment by 50;
create index if not exists notifications_receiver_id_index on notifications (receiver_id);
create index if not exists notifications_sender_id_index on notifications (sender_id);
create index if not exists notifications_booking_id_index on notifications (booking_id);
create index if not exists notifications_appointment_id_index on notifications (appointment_id);