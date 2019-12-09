CREATE TABLE notification
(
    id int AUTO_INCREMENT PRIMARY KEY,
    gmt_create BIGINT,
    type int,
    status int,
    replier_id int,
    by_replier_id int,
    question_id int
);