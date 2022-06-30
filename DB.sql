create database app;

use app;

drop table accounts;
-- drop table account_roles;

create table accounts (
id int not null auto_increment,
username VARCHAR(20) unique not null,
password VARCHAR(20) not null,
first_name VARCHAR(50) not null,
last_name VARCHAR(50) not null,
email VARCHAR(50) not null,
contact_number VARCHAR(20) not null,
role ENUM('Regular', 'Admin') default 'Regular',
PRIMARY KEY (id)
-- FOREIGN KEY(account_role_id) references account_roles(id)
);

-- create table account_roles (
-- id int not null primary key auto_increment,
-- title VARCHAR(20)
-- );

-- insert into account_roles (title) values ('Regular'), ('Admin');

