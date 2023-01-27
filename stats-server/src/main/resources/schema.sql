drop table if exists hits;
create table if not exists hits
(
    hit_id  int generated by default as identity primary key,
    app     varchar(255) not null,
    uri     varchar(255) not null,
    ip      varchar(255) not null,
    created timestamp    not null
);