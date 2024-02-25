create database video_host;

create schema video_host_schema;

set search_path to video_host_schema;

create type genre as enum ('Боевик', 'Драма', 'Комедия', 'Ужасы', 'Фантастика', 'Триллер', 'Другой');
create type device_type as enum ('Кассета', 'Диск');

create table clients(
	client_id serial primary key,
	email varchar(255) not null,
	user_password varchar(255) not null,
	full_name varchar(255) not null,
	address varchar(255),
	phone_number varchar(20)
);

create table films(
	film_id serial primary key,
	film_title varchar(255) not null,
	film_company varchar(255) not null,
	director varchar(255) not null,
	year_of_premiere integer not null,
	film_genre genre not null,
	description text
);

create table storage_device(
	storage_device_id serial primary key,
	type device_type not null
);


create table storage_info(
	storage_info_id serial primary key, 
	storage_device_id integer references storage_device,
	full_amount integer not null,
	free_amount integer not null
);

create table film_copies(
	film_copies_id serial primary key,
	film_id integer references films,
	storage_device_id integer references storage_device,
	client_id integer references clients,
	status varchar(20) not null,
	date_of_issue date,
	return_date date,
	price decimal(10,2)
);


