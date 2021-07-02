CREATE DATABASE IF NOT EXISTS soundTok;
USE soundTok;
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL,
    email    VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS clips
(
    id      SERIAL,
    user    BIGINT unsigned NOT NULL,
    caption TEXT,
    `date`  DATETIME        NOT NULL,
    audio   MEDIUMBLOB      NOT NULL,
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS views
(
    user BIGINT unsigned,
    clip BIGINT unsigned,
    FOREIGN KEY (user) REFERENCES users (id) ON DELETE SET NULL,
    FOREIGN KEY (clip) REFERENCES clips (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes
(
    user BIGINT unsigned,
    clip BIGINT unsigned,
    FOREIGN KEY (user) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (clip) REFERENCES clips (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments
(
    id      SERIAL,
    user    BIGINT unsigned,
    content TEXT,
    `date`  DATETIME
);

CREATE TABLE IF NOT EXISTS answers
(
    parent BIGINT unsigned,
    answer BIGINT unsigned,
    FOREIGN KEY (parent) REFERENCES comments (id),
    FOREIGN KEY (answer) REFERENCES comments (id)
);

CREATE TABLE IF NOT EXISTS clip_comments
(
    clip    BIGINT unsigned,
    comment BIGINT unsigned,
    FOREIGN KEY (clip) REFERENCES clips (id),
    FOREIGN KEY (comment) REFERENCES comments (id)
);
