create table check_book (id bigint not null, check_amount integer not null, check_type varchar(255), currency varchar(255), first_check integer not null, last_check integer not null, order_number integer not null, status varchar(255), client_id bigint not null, primary key (id)) engine=InnoDB
create table client (id bigint not null auto_increment, client_id varchar(255), first_name varchar(255), last_name varchar(255), marital_status varchar(255), contact_info bigint, primary key (id)) engine=InnoDB
create table contact_info (id bigint not null auto_increment, apartment varchar(255), building varchar(255), cellphone varchar(255), city varchar(255), distribution_agreement integer not null, email varchar(255), entrance varchar(255), postal_box varchar(255), postal_code varchar(255), street varchar(255), telephone varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
alter table check_book add constraint FK3q14uuvom4700y1urpapptha3 foreign key (client_id) references client (id)
alter table client add constraint FK9t0x5bw1qujptw301rnyiusa0 foreign key (contact_info) references contact_info (id)
