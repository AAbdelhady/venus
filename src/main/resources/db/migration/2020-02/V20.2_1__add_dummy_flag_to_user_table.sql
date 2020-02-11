alter table users
    add column if not exists is_dummy boolean default false not null