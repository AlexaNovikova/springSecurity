create table users (
  id                    bigserial,
  username              varchar(30) not null unique,
  password              varchar(80) not null,
  email                 varchar(50) unique,
  primary key (id)
);

create table roles (
  id                    serial,
  name                  varchar(50) not null,
  primary key (id)
);

create table authorities (
  id                    serial,
  name                  varchar(50) not null,
  role_id               int not null,
  primary key (id),
  foreign key (role_id) references roles (id)
);

CREATE TABLE users_roles (
  user_id               bigint not null,
  role_id               int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN');

insert into authorities (name, role_id)
values
('Create_items',2), ('Delete_items',2), ('Put_items_in_cart', 1), ('View_items', 1);

insert into users (username, password, email)
values
('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com'),
('user',  '$2b$06$gTxJszGdZ1OUghlxK30PtesYG1e4Jup.EKvKaxA7h6lnL61Qd0jkG', 'user@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(2, 1);