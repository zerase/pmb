DROP DATABASE IF EXISTS pmb5_dev;
CREATE DATABASE pmb5_dev;
USE pmb5_dev;



CREATE TABLE bank_account (
    bank_account_id bigint not null auto_increment, 
    iban varchar(34), 
    primary key (bank_account_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;


CREATE TABLE connection (
    connection_id bigint not null auto_increment, 
    user_id bigint not null, 
    friend_id bigint not null, 
    primary key (connection_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;


CREATE TABLE transaction (
    transaction_id bigint not null auto_increment, 
    amount decimal(19,2), 
    charges decimal(19,2), 
    description varchar(255), 
    type varchar(20), 
    account_receiver_id bigint, 
    account_sender_id bigint, 
    primary key (transaction_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;


CREATE TABLE user_account (
    user_account_id bigint not null auto_increment, 
    balance decimal(19,2), 
    email varchar(255) unique, 
    first_name varchar(50), 
    last_name varchar(50), 
    password varchar(60), 
    bank_account_id bigint, 
    primary key (user_account_id)
) engine=InnoDB default charset utf8mb4 collate utf8mb4_unicode_ci;



INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('147.89', 'john@test.com', 'John', 'Doe', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '1');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('3000', 'isaac@test.com', 'Isaac', 'Newton', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '2');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('100', 'michael@test.com', 'Michael', 'Faraday', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '3');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('7.77', 'albert@test.com', 'Albert', 'Einstein', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '4');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('0', 'antoine@test.com', 'Antoine', 'de Lavoisier', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '5');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('0', 'marie@test.com', 'Marie', 'Curie', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '6');
INSERT INTO `pmb5_dev`.`user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('50', 'thomas@test.com', 'Thomas', 'Edison', '$2a$10$MaF8sdxa5METiRlrSsNZueJWZNxWIr/5SCvdJi.DZ9dIOO/AFNcNm', '7');

INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('1', '2');
INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('1', '3');
INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('1', '4');
INSERT INTO `pmb5_dev`.`connection` (`user_id`, `friend_id`) VALUES ('6', '5');

INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('1000', '0.5', 'Add money to my account', 'BANK_TO_USER', '1', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('80', '0.5', 'Rent a car', 'USER_TO_USER', '2', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('80', '0.5', 'Rent a bike', 'USER_TO_USER', '2', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('10', '0.5', 'Restaurant bill', 'USER_TO_USER', '3', '1');
INSERT INTO `pmb5_dev`.`transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('24.99', '0.5', 'Movie tickets', 'USER_TO_USER', '5', '6');

INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('FR11 2222 3333 4444');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('DV11 2222 3333 4444');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('DV22 3333 4444 5555');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('DV33 4444 5555 6666');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('WB11 2222 3333 4444');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('WB22 3333 4444 5555');
INSERT INTO `pmb5_dev`.`bank_account` (`iban`) VALUES ('WD11 2222 3333 4444');



ALTER TABLE user_account add constraint UKhl02wv5hym99ys465woijmfib unique (email);
ALTER TABLE user_account add constraint FKswc55mdn7jer4x5uka6w5uy2h 
    foreign key (bank_account_id) 
    references bank_account (bank_account_id);

ALTER TABLE transaction add constraint FK72r9uw5i62me2ywokk1hpkyen 
    foreign key (account_receiver_id) 
    references user_account (user_account_id);

ALTER TABLE transaction add constraint FK3580pfo6wrre763aaoee6uc82 
    foreign key (account_sender_id) 
    references user_account (user_account_id);

ALTER TABLE connection add constraint FKd3nihp56qqxyidykyvlruv1yf 
    foreign key (friend_id) references user_account (user_account_id);
    
ALTER TABLE connection add constraint FKr59jg6yg64eicd8t5in2qtxu2 
    foreign key (user_id) references user_account (user_account_id);

