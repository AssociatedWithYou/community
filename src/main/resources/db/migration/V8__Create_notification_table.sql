CREATE TABLE notification
(
    id int AUTO_INCREMENT PRIMARY KEY,
    notifiter int NOT NULL,
    recevier int,
    type int,
    gmt_create bigint,
    status int DEFAULT 0
);