alter table bookings
    add column if not exists speciality_id bigint not null;
alter table bookings
    add constraint bookings_speciality_id_fk foreign key (speciality_id) references specialities (id) on update cascade on delete cascade;
alter table bookings
    add column if not exists booking_date date not null;
alter table bookings
    add column if not exists status varchar(64) not null;