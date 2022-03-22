DROP DATABASE IF EXISTS pmb5_dev;
CREATE DATABASE pmb5_dev;
USE pmb5_dev;



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



alter table connection add constraint FKd3nihp56qqxyidykyvlruv1yf 
	foreign key (friend_id) references user_account (user_account_id);
    
alter table connection add constraint FKr59jg6yg64eicd8t5in2qtxu2 
	foreign key (user_id) references user_account (user_account_id);



INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('147.89', 'john@test.com', 'John', 'Doe', 'test123', '1');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('3000', 'picsou@test.com', 'Balthazar', 'Picsou', 'test123', '2');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('100', 'donald@test.com', 'Donald', 'Duck', 'test123', '3');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('7.77', 'gontran@test.com', 'Gontran', 'Bonheur', 'test123', '4');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('0', 'bugs@test.com', 'Bugs', 'Bunny', 'test123', '5');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('0', 'tex@test.com', 'Tex', 'Avery', 'test123', '6');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('50', 'pat@test.com', 'Pat', 'Hibulaire', 'test123', '7');

INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('1', '2');
INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('1', '3');
INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('1', '4');
INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('6', '5');

INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('1000', '0.5', 'Mon premier versement sur l\'appli', 'BANK_TO_USER', '1', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('80', '0.5', 'Loyer jan', 'USER_TO_USER', '2', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('80', '0.5', 'Loyer fév', 'USER_TO_USER', '2', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('10', '0.5', 'Restaurant', 'USER_TO_USER', '3', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('24.99', '0.5', 'Voyage', 'USER_TO_USER', '5', '6');

INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('FR123john');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('DV123picsou');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('DV123donald');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('DV123gontran');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('WB123bugs');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('WB123tex');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('WD123pat');


