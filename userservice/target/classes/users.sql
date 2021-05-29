drop table if exists user_entity CASCADE
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table user_entity (id integer not null, email varchar(50) not null, encrypted_pwd varchar(255) not null, name varchar(50) not null, user_id varchar(255) not null, primary key (id))
