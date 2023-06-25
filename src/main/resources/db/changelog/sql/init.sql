--liquibase formatted sql

-- changeset alexeym75:1
CREATE TABLE IF NOT EXISTS socks
(
    id          BIGSERIAL PRIMARY KEY,
    color       VARCHAR,
    cottonPart  INT,
    quantity    INT
);
