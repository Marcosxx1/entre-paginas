-- auto-generated definition
create table comments
(
    id         varchar(255) not null
        primary key,
    content    text         not null,
    date       timestamp(6) with time zone,
    post_id    varchar(255)
        constraint fkbqnvawwwv4gtlctsi3o7vs131
            references post,
    usuario_id varchar(255)
        constraint fk7knwj4s3k0uvoabwmo94lggc6
            references usuario
);

alter table comments
    owner to postgres;

