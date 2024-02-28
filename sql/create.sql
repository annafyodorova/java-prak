create database video_host;

create schema video_host_schema;

set search_path to video_host_schema;

create type genre as enum ('Боевик', 'Драма', 'Комедия', 'Ужасы', 'Фантастика', 'Триллер', 'Другой');
create type device_type as enum ('Кассета', 'Диск');
