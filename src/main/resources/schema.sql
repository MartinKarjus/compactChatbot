DROP SCHEMA PUBLIC CASCADE;

CREATE SCHEMA main;

CREATE
    SEQUENCE content_sequence AS INTEGER START
    WITH 1;
CREATE
  SEQUENCE team_sequence AS INTEGER START
  WITH 1;
CREATE
    SEQUENCE media_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE user_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE company_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE plan_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE time_to_send_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE platform_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE platform_to_company_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE plan_accomplished_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE answers_log_sequence AS INTEGER START
    WITH 1;


CREATE TABLE main.question_group (
   id BIGINT NOT NULL PRIMARY KEY,
   name VARCHAR(250) NOT NULL,
   company BIGINT,
   FOREIGN KEY (company)
     REFERENCES main.company on DELETE CASCADE
);

CREATE TABLE main.media
(
    id     BIGINT NOT NULL PRIMARY KEY,
    type   VARCHAR(5000),
    source VARCHAR(5000)
);

CREATE TABLE main.question
(
    id                     BIGINT NOT NULL PRIMARY KEY,
    text                   VARCHAR(5000),
    can_be_random_selected BOOLEAN DEFAULT FALSE,
    media_id               BIGINT,
    FOREIGN KEY (media_id)
        REFERENCES main.media (id) ON DELETE CASCADE
);

CREATE TABLE main.content
(
    id                   BIGINT NOT NULL PRIMARY KEY,
    leads_to_content_id  BIGINT,
    leads_to_question_id BIGINT,
    text                 VARCHAR(5000),
    media_id             BIGINT,
    FOREIGN KEY (leads_to_content_id)
        REFERENCES main.content (id) ON DELETE CASCADE,
    FOREIGN KEY (media_id)
        REFERENCES main.media (id) ON DELETE CASCADE,
    FOREIGN KEY (leads_to_question_id)
        REFERENCES main.question (id) ON DELETE CASCADE
);

CREATE TABLE main.question_query
(
    id                  BIGINT NOT NULL PRIMARY KEY,
    query_text          VARCHAR(5000),
    query_response      VARCHAR(5000),
    leads_to_content_id BIGINT,
    media_id            BIGINT,
    question_id         BIGINT,
    FOREIGN KEY (question_id)
        REFERENCES main.question (id) ON DELETE CASCADE,
    FOREIGN KEY (leads_to_content_id)
        REFERENCES main.content (id) ON DELETE CASCADE,
    FOREIGN KEY (media_id)
        REFERENCES main.media (id) ON DELETE CASCADE
);

CREATE TABLE main.time_to_send
(
    id                BIGINT NOT NULL PRIMARY KEY,
    time_to_send  DATE
);

CREATE TABLE main.plan
(
    id              BIGINT NOT NULL PRIMARY KEY,
    content_id      BIGINT,
    time_to_send_id BIGINT,
    day             BIGINT,
    company_id      BIGINT,
    FOREIGN KEY (company_id)
        REFERENCES main.company (id) ON DELETE CASCADE,
    FOREIGN KEY (content_id)
        REFERENCES main.content (id) ON DELETE CASCADE,
    FOREIGN KEY (time_to_send_id)
        REFERENCES main.time_to_send (id) ON DELETE CASCADE
);

CREATE TABLE main.user (
     id BIGINT NOT NULL PRIMARY KEY,
     first_name VARCHAR(200),
     last_name VARCHAR(200),
     gender VARCHAR(10),
     birtdate TIMESTAMP,
     email VARCHAR(200),
     team_id BIGINT,
     company BIGINT NOT NULL,
     score BIGINT,
     join_date DATE,
     question_group_id BIGINT NOT NULL,
     FOREIGN KEY (question_group_id)
       REFERENCES main.question_group ON DELETE CASCADE,
     FOREIGN KEY (team_id)
       REFERENCES main.team ON DELETE CASCADE,
     FOREIGN KEY (company)
       REFERENCES main.company ON DELETE CASCADE
);

CREATE TABLE main.team (
   id BIGINT NOT NULL PRIMARY KEY,
   name VARCHAR(200) NOT NULL,
   score BIGINT
);

CREATE TABLE main.company (
  id BIGINT NOT NULL PRIMARY KEY,
  name VARCHAR(200)
);

CREATE TABLE main.plan_accomplished
(
    id           BIGINT NOT NULL PRIMARY KEY,
    user_id      BIGINT,
    plan_id      BIGINT,
    FOREIGN KEY (user_id)
        REFERENCES main.user (id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id)
        REFERENCES main.plan (id) ON DELETE CASCADE
);

CREATE TABLE main.answers_log
(
    id                BIGINT NOT NULL,
    question_query_id BIGINT,
    user_id           BIGINT,
    points            BIGINT,
    answer            VARCHAR(250),
    FOREIGN KEY (user_id)
        REFERENCES main.user (id) ON DELETE CASCADE,
    FOREIGN KEY (question_query_id)
        REFERENCES main.question_query (id) ON DELETE CASCADE
);

CREATE TABLE main.platform(
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(5000)
);

CREATE TABLE main.platform_to_user
(
    id                    BIGINT NOT NULL PRIMARY KEY,
    user_id            BIGINT,
    platform_id           BIGINT,
    FOREIGN KEY (user_id)
        REFERENCES main.user (id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id)
        REFERENCES main.platform (id) ON DELETE CASCADE
);