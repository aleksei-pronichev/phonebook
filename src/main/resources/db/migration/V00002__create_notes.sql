create table notes
(
    id      bigint primary key auto_increment,
    user_id bigint references users (id),
    note    varchar(150) not null,
    phone   varchar(20)  not null
);