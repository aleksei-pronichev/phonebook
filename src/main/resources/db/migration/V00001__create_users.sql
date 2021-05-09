create table users
(
    id    bigint primary key auto_increment,
    login varchar(45) not null unique
);