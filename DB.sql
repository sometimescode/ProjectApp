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

-- testbed code
-- use app;
-- create table testbed (
-- id int not null auto_increment,
-- multipleSelect VARCHAR(100) not null,
-- img BLOB,
-- PRIMARY KEY (id)
-- );
-- testbed code

create table book_entries (
id int not null auto_increment,
title VARCHAR(100) not null,
authors VARCHAR(500) not null,
cover BLOB,
isbn VARCHAR(13) not null,
page_count INT not null,
publisher VARCHAR(100) not null,
published_date DATE not null,
genre ENUM('Fiction', 'Non-Fiction') not null,
total_copies INT not null default 0,
available_copies INT not null default 0,
PRIMARY KEY (id)
);
-- ff fields not found in javascript model yeT: 
-- copies INT not null default 0,
-- available_copies INT not null default 0

create table book_copies (
id int not null auto_increment,
book_entry_id int not null,
current_checkout_record_id int,
checked_out BOOLEAN default false,
purchase_price int not null,
available BOOLEAN default true,
PRIMARY KEY (id),
FOREIGN KEY(book_entry_id) references book_entries(id)
);

create table checkout_records (
id int not null auto_increment,
book_copy_id int not null,
borrower_id int not null,
checkout_date DATE not null,
expected_return_date DATE not null,
actual_return_date DATE,
checkout_status ENUM('Checked Out', 'Checked In', 'Renewed', 'Lost') not null default 'Checked Out',
renewed_count INT not null default 0,
late_return BOOLEAN,
damaged_return BOOLEAN,
fine_status ENUM('Paid', 'Unpaid'),
fine INT,
record_notes VARCHAR(500),
PRIMARY KEY (id),
FOREIGN KEY(book_copy_id) references book_copies(id)
);


