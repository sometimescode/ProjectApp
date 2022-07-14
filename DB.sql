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
drop table book_entries;
create table book_entries (
id int not null auto_increment,
title VARCHAR(100) not null,
authors VARCHAR(500) not null,
cover BLOB,
isbn VARCHAR(13) unique not null,
page_count INT not null,
publisher VARCHAR(100) not null,
published_date DATE not null,
genre ENUM('Fiction', 'Non-Fiction') not null,
-- total_copies INT not null default 0,
-- available_copies INT not null default 0,
PRIMARY KEY (id)
);
-- ff fields not found in javascript model yeT: 
-- copies INT not null default 0,
-- available_copies INT not null default 0
drop table book_copies;
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
-- current_checkout_record_id; rename to recent_checkout_record_id
-- available BOOLEAN default true; rename to archived, because checked_out being false already implies availability

drop table online_checkout_requests;
create table online_checkout_requests (
id int not null auto_increment,
requester_id int not null,
book_to_checkout_id int not null,
status ENUM('Pending', 'Approved', 'Rejected', 'Canceled') not null default 'Pending',
request_date DATE not null default (current_date),
status_update_date DATE,
checkout_record_id int,
PRIMARY KEY (id),
FOREIGN KEY(requester_id) references accounts(id),
FOREIGN KEY(book_to_checkout_id) references book_entries(id)
);
use app;
drop table checkout_records;
create table checkout_records (
id int not null auto_increment,
book_entry_id int not null,
book_copy_id int not null,
borrower_id int not null,
online_checkout_request_id int,
checkout_date DATE not null default (current_date),
expected_return_date DATE not null default (current_date + 7),
actual_return_date DATE,
status ENUM('Checked Out', 'Checked In', 'Lost') not null default 'Checked Out',
fine int not null default 0,
fine_paid boolean not null default false,
PRIMARY KEY (id),
FOREIGN KEY(book_entry_id) references book_entries(id),
FOREIGN KEY(book_copy_id) references book_copies(id),
FOREIGN KEY(borrower_id) references accounts(id),
FOREIGN KEY(online_checkout_request_id) references online_checkout_requests(id)
);
-- checkout_status ENUM('Checked Out', 'Checked In', 'Renewed', 'Lost') not null default 'Checked Out',
-- renewed_count INT not null default 0,
-- late_return BOOLEAN,
-- damaged_return BOOLEAN,
-- fine_status ENUM('Paid', 'Unpaid'),
-- fine INT,
-- record_notes VARCHAR(500),

-- note 9780735211308: null author