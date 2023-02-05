create table if not exists categories
(
    category_id bigint generated by default as identity
        primary key,
    name        varchar(255) not null
        constraint uk_name
            unique
);

create table if not exists compilations
(
    compilation_id bigint generated by default as identity
        primary key,
    pinned         boolean,
    title          varchar(255) not null
);


create table if not exists users
(
    user_id bigint generated by default as identity primary key,
    email   varchar(255) not null
        constraint uk_email unique,
    name    varchar(255) not null
);

create table if not exists events
(
    event_id           bigint generated by default as identity
        primary key,
    annotation         varchar(2000) not null,
    confirmed_requests integer       not null,
    created_on         timestamp,
    description        varchar(7000) not null,
    event_date         timestamp     not null,
    location_lat       real          not null,
    location_lon       real          not null,
    paid               boolean       not null,
    participant_limit  integer       not null,
    published_on       timestamp,
    request_moderation boolean       not null,
    state              varchar(30)   not null,
    title              varchar(120)  not null,
    category_id        bigint        not null
        constraint fk_category
            references categories,
    initiator_id       bigint
        constraint fk_users_events
            references users
);

create table if not exists compilations_events
(
    compilation_id bigint not null
        constraint fk_compilations
            references compilations,
    event_id       bigint not null
        constraint fk_events_compilations
            references events,
    primary key (compilation_id, event_id)
);

create table if not exists requests
(
    request_id   bigint generated by default as identity
        primary key,
    created      timestamp,
    status       integer,
    event_id     bigint
        constraint fk_events_requests
            references events,
    requester_id bigint
        constraint fk_users_requests
            references users,
    constraint uk_event_user_unique
        unique (event_id, requester_id)
);