create table users (
    id int not null auto_increment,
    name varchar(45),
    lastname varchar(45),
    email varchar(70) unique,
    username varchar(45)  not null unique,
    password varchar(60) not null,
    primary key (id)
);