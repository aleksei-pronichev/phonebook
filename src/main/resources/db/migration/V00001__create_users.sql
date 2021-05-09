create table users
(
    id bigint primary key auto_increment,
    username varchar(45) not null unique
);