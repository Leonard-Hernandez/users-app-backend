create table roles(
    id int not null auto_increment,
    name varchar(20),
    primary key(id)
);

create table users_roles(
    user_id int not null,
    role_id int not null,
    primary key(user_id, role_id),
    foreign key(user_id) references users(id),
    foreign key(role_id) references roles(id)
);

insert into roles (name) values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users_roles (user_id, role_id) values
(1, 1),
(2, 2);