update users
set role = 'UNSPECIFIED'
where role = 'USER';

alter table artists
    drop column if exists artist_nick;