create table Customer( 
customer_id integer primary key,
first_name varchar not null,
last_name varchar  not null,
password varchar not null,
email  varchar unique,
phone_number varchar not null,
is_demo boolean default false
);

create table City(
city_id integer primary key,
name varchar not null,
county varchar,
state varchar
)

CREATE SEQUENCE customer_seq
START WITH 1
INCREMENT BY 50;

CREATE SEQUENCE customer_role_seq
START WITH 1
INCREMENT BY 50;

CREATE SEQUENCE customer_city_seq
START WITH 1
INCREMENT BY 50;

CREATE SEQUENCE customer_hometype_seq
START WITH 1
INCREMENT BY 50;

CREATE SEQUENCE scheduled_task_seq
START WITH 1
INCREMENT BY 50;

CREATE TYPE hometype AS ENUM ('HOUSE', 'TOWNHOUSE', 'CONDO', 'MULTI_FAMILY', 'CO_OP', 'LAND','MOBILE', 'OTHER');
CREATE TYPE bedcount AS ENUM ('0', '1','2','3','4','5','100');
CREATE TYPE bathcount as ENUM ('1','1.5','2','2.5','3','4','100');
CREATE TYPE roletype as ENUM('CUSTOMER', 'ADMIN');
CREATE TYPE statustype AS ENUM ('PENDING', 'REJECTED','APPROVED','EXPIRED', 'CANCELED','DONE');

create table customer_preference(
customer_id integer primary key references customer, 
min_price integer,
max_price integer,
min_bed bedcount,
max_bed bedcount,
min_bath bathcount,
min_square_feet integer,
max_square_feet integer,
CONSTRAINT fk_customer_preference_ref
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
        ON DELETE CASCADE
);

create table customer_hometype(
id bigint  primary key,
customer_id integer,
hometype hometype,
CONSTRAINT fk_customer_hometype_ref
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
        ON DELETE CASCADE
);
create table customer_city(
id bigint  primary key, 
customer_id integer,
city_id integer,
CONSTRAINT fk_customer_hometype_customer_ref
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
        ON DELETE CASCADE,
CONSTRAINT fk_customer_hometype_city_ref
        FOREIGN KEY (city_id)
        REFERENCES city (city_id)
        ON DELETE CASCADE
);

create sequence customer_seq start 1002 increment by 1;

create table role(
role_id integer primary key,
name roletype
);

create table customer_role(
id integer primary key,
customer_id integer,
role_id integer,
CONSTRAINT fk_customer_role_customer_ref
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
        ON DELETE CASCADE,
CONSTRAINT fk_customer_role_role_ref
        FOREIGN KEY (role_id)
        REFERENCES role (role_id)
        ON DELETE CASCADE
);

create table permission(
permission_id integer primary key,
name varchar not null
);


create table role_permissions(
id integer primary key,
role_id integer,
permission_id integer,
CONSTRAINT fk_role_permission_role_ref
        FOREIGN KEY (role_id)
       REFERENCES role (role_id)
       ON DELETE CASCADE,
CONSTRAINT fk_role_permission_permission_ref
        FOREIGN KEY (permission_id)
       REFERENCES permission (permission_id)
       ON DELETE CASCADE
);

create table email_code(
email varchar primary key,
code varchar not null,
update_time timestamp not null
);

create table scheduled_task(
	id bigint primary key,
	customer_id integer,
	task_date date,
	start_time time,
	address varchar not null,
	city varchar not null,
	state varchar not null,
	zipcode varchar,
	comment varchar, 
	note varchar,
	agent varchar,
	status statustype,
	CONSTRAINT fk_schedule_task_customer_ref
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
        ON DELETE CASCADE
);