DROP DATABASE IF EXISTS pmb5_test;
CREATE DATABASE pmb5_test;
USE pmb5_test;



create table bank_account (
	bank_account_id bigint not null auto_increment, 
	iban varchar(255), 
	primary key (bank_account_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;


create table connection (
	connection_id bigint not null auto_increment, 
    user_id bigint not null, 
    friend_id bigint not null, 
    primary key (connection_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;


create table transaction (
	transaction_id bigint not null auto_increment, 
    amount decimal(19,2), 
    charges decimal(19,2), 
    description varchar(255), 
    type varchar(255), 
    account_receiver_id bigint, 
    account_sender_id bigint, 
    primary key (transaction_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;


create table user_account (
	user_account_id bigint not null auto_increment, 
    balance decimal(19,2), 
    email varchar(255) unique, 
    first_name varchar(255), 
    last_name varchar(255), 
    password varchar(255), 
    bank_account_id bigint, 
    primary key (user_account_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;



alter table user_account add constraint UKhl02wv5hym99ys465woijmfib unique (email);
alter table user_account add constraint FKswc55mdn7jer4x5uka6w5uy2h 
	foreign key (bank_account_id) 
	references bank_account (bank_account_id);

alter table transaction add constraint FK72r9uw5i62me2ywokk1hpkyen 
	foreign key (account_receiver_id) 
	references user_account (user_account_id);

alter table transaction add constraint FK3580pfo6wrre763aaoee6uc82 
	foreign key (account_sender_id) 
	references user_account (user_account_id);

alter table connection add constraint FKd3nihp56qqxyidykyvlruv1yf 
	foreign key (friend_id) references user_account (user_account_id);
    
alter table connection add constraint FKr59jg6yg64eicd8t5in2qtxu2 
	foreign key (user_id) references user_account (user_account_id);


