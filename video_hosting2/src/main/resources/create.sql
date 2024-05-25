-- create database video_host;

-- create schema if not exists video_host_schema;

-- set search_path to video_host_schema;

drop type if exists genre;
drop type if exists device_type;
drop type if exists copy_status;

create type genre as enum ('Боевик', 'Драма', 'Комедия', 'Ужасы', 'Фантастика', 'Триллер', 'Другой', 'Мюзикл');
create type device_type as enum ('Кассета', 'Диск');
create type copy_status as enum ('Выдан', 'Не выдан');

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
                      film_genre varchar(20) not null,
                      description text
);

create table film_copies(
                            film_copies_id serial primary key,
                            status copy_status not null
);

create table storage_info(
                             storage_info_id serial primary key,
                             film_id integer references films,
                             film_copies integer[],
                             storage_device_type varchar(8) not null,
                             full_amount integer not null,
                             free_amount integer not null,
                             price decimal(10,2) not null
);


create table history(
                        id serial primary key,
                        client_id integer references clients,
                        film_copy_id integer references film_copies,
                        date_of_issue date not null,
                        date_of_return date not null,
                        unique(client_id, film_copy_id, date_of_issue)
);