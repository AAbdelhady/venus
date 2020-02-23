alter table artists
    add column if not exists category varchar(64) not null;

create table if not exists specialities
(
    id        bigint primary key       not null,
    artist_id bigint                   not null,
    name      varchar(255)             not null,
    price     numeric(8, 2)            not null,
    created   timestamp with time zone not null,
    modified  timestamp with time zone not null,
    constraint specialties_artist_id_fk foreign key (artist_id) references artists (user_id) on update cascade on delete cascade,
    constraint specialties_artist_id_name_unique unique (artist_id, name)
);
create sequence if not exists specialties_seq increment by 50;
create index if not exists specialties_user_id_uindex on specialities (artist_id);