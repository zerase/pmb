INSERT INTO `user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('147.89', 'john@test.com', 'John', 'Doe', 'test', '1');
INSERT INTO `user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('3000', 'hayley@test.com', 'Hayley', 'James', 'test', '2');
INSERT INTO `user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('100', 'clara@test.com', 'Clara', 'Belle', 'test', '3');
INSERT INTO `user_account` (`balance`, `email`, `first_name`, `last_name`, `password`, `bank_account_id`) VALUES ('7.77', 'jane@test.com', 'Jane', 'Doe', 'test', '4');

INSERT INTO `connection` (`user_id`, `friend_id`) VALUES ('1', '2');
INSERT INTO `connection` (`user_id`, `friend_id`) VALUES ('1', '3');
INSERT INTO `connection` (`user_id`, `friend_id`) VALUES ('1', '4');
INSERT INTO `connection` (`user_id`, `friend_id`) VALUES ('4', '1');

INSERT INTO `transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('1000', '0.5', 'My first transfer', 'BANK_TO_USER', '1', '1');
INSERT INTO `transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('80', '0.5', 'Restaurant bill share', 'USER_TO_USER', '2', '1');
INSERT INTO `transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('80', '0.5', 'Gift', 'USER_TO_USER', '2', '1');
INSERT INTO `transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('10', '0.5', 'Movie tickets', 'USER_TO_USER', '3', '1');
INSERT INTO `transaction` (`amount`, `charges`, `description`, `type`, `account_receiver_id`, `account_sender_id`) VALUES ('24.99', '0.5', 'Trip money', 'USER_TO_USER', '1', '4');

INSERT INTO `bank_account` (`iban`) VALUES ('FR123john');
INSERT INTO `bank_account` (`iban`) VALUES ('OTH123hayley');
INSERT INTO `bank_account` (`iban`) VALUES ('MV123clara');
INSERT INTO `bank_account` (`iban`) VALUES ('FR123jane');

