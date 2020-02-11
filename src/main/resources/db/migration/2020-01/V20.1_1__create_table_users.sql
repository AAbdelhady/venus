-- users table --
create table if not exists users
(
    id                  BIGINT PRIMARY KEY       not null,
    login_id            varchar(64)              not null,
    first_name          varchar(64)              not null,
    last_name           varchar(64)              not null,
    role                varchar(64)              not null,
    auth_provider       varchar(64)              not null,
    email               varchar(64)              not null,
    phone               varchar(64),
    profile_picture_url varchar(256),
    created             timestamp with time zone not null,
    modified            timestamp with time zone not null,
    constraint users_login_id_unique unique (login_id)
);
create sequence if not exists users_seq increment by 50;
create unique index if not exists users_login_id_uindex on users (login_id);
create index if not exists users_modified_index on users (modified);


-- customers table --
create table if not exists customers
(
    user_id bigint primary key not null,
    constraint customers_user_id_fk foreign key (user_id) references users (id) on update cascade on delete cascade
);
create unique index if not exists customers_id_uindex on customers (user_id);


-- artists table --
create table if not exists artists
(
    user_id bigint primary key not null,
    active  boolean            not null default false,
    constraint artists_user_id_fk foreign key (user_id) references users (id) on update cascade on delete cascade
);
create unique index if not exists artists_id_uindex on customers (user_id);