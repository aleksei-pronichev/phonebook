create table notes
(
    id      bigint primary key auto_increment,
    user_id bigint references users (id),
    name    varchar(150) not null,
    phone   varchar(20)  not null,
    UNIQUE KEY (user_id, name)
);