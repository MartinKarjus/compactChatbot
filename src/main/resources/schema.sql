-- DROP SCHEMA PUBLIC CASCADE;

-- CREATE SCHEMA public;

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
    SEQUENCE platform_to_user_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE platform_to_company_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE plan_accomplished_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE answers_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE question_group_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE question_sequence AS INTEGER START
    WITH 1;

CREATE
    SEQUENCE transmission_log_sequence AS INTEGER START
    WITH 1;


CREATE TABLE public.company (
                              id BIGINT NOT NULL PRIMARY KEY,
                              date_created TIMESTAMP,
                              name VARCHAR(200)
);

CREATE TABLE public.question_group (
   id BIGINT NOT NULL PRIMARY KEY,
   name VARCHAR(250) NOT NULL,
   date_created TIMESTAMP,
   date_modified TIMESTAMP,
   company_id BIGINT,
--    FOREIGN KEY (company_id)
--      REFERENCES public.company (id) on DELETE CASCADE
);

CREATE TABLE public.media
(
    id     BIGINT NOT NULL PRIMARY KEY,
    date_created TIMESTAMP,
    date_modified TIMESTAMP,
    type   VARCHAR(5000),
    source VARCHAR(5000)
);

CREATE TABLE public.question
(
    id                     BIGINT NOT NULL PRIMARY KEY,
    date_created           TIMESTAMP,
    date_modified          TIMESTAMP,
    name                   VARCHAR(300),
    description            VARCHAR(500),
    text                   VARCHAR(5000),
    can_be_random_selected BOOLEAN DEFAULT FALSE,
    leads_to_question_id   BIGINT,
    media_id               BIGINT,
--     FOREIGN KEY (media_id)
--         REFERENCES public.media (id) ON DELETE CASCADE
);

-- CREATE TABLE main.content
-- (
--     id                   BIGINT NOT NULL PRIMARY KEY,
--     leads_to_content_id  BIGINT,
--     leads_to_question_id BIGINT,
--     text                 VARCHAR(5000),
--     media_id             BIGINT,
--     FOREIGN KEY (leads_to_content_id)
--         REFERENCES main.content (id) ON DELETE CASCADE,
--     FOREIGN KEY (media_id)
--         REFERENCES main.media (id) ON DELETE CASCADE,
--     FOREIGN KEY (leads_to_question_id)
--         REFERENCES main.question (id) ON DELETE CASCADE
-- );

-- CREATE TABLE main.question_query
-- (
--     id                  BIGINT NOT NULL PRIMARY KEY,
--     query_text          VARCHAR(5000),
--     query_response      VARCHAR(5000),
--     leads_to_content_id BIGINT,
--     media_id            BIGINT,
--     question_id         BIGINT,
--     FOREIGN KEY (question_id)
--         REFERENCES main.question (id) ON DELETE CASCADE,
--     FOREIGN KEY (leads_to_content_id)
--         REFERENCES main.content (id) ON DELETE CASCADE,
--     FOREIGN KEY (media_id)
--         REFERENCES main.media (id) ON DELETE CASCADE
-- );

CREATE TABLE public.time_to_send
(
    id                BIGINT NOT NULL PRIMARY KEY,
    date_created      TIMESTAMP,
    time_to_send      DATE
);

CREATE TABLE public.plan
(
    id              BIGINT NOT NULL PRIMARY KEY,
    date_created    TIMESTAMP,
    date_modified   TIMESTAMP,
    question_id      BIGINT,
    time_to_send_id BIGINT,
    day             BIGINT,
    company_id      BIGINT,
--     FOREIGN KEY (company_id)
--         REFERENCES public.company (id) ON DELETE CASCADE,
--     FOREIGN KEY (question_id)
--         REFERENCES public.question (id) ON DELETE CASCADE,
--     FOREIGN KEY (time_to_send_id)
--         REFERENCES public.time_to_send (id) ON DELETE CASCADE
);

CREATE TABLE public.team (
                           id BIGINT NOT NULL PRIMARY KEY,
                           name VARCHAR(200) NOT NULL,
                           date_created TIMESTAMP,
                           date_modified TIMESTAMP,
                           score BIGINT
);

CREATE TABLE public.user (
     id BIGINT NOT NULL PRIMARY KEY,
     first_name VARCHAR(200),
     last_name VARCHAR(200),
     date_created TIMESTAMP,
     date_modified TIMESTAMP,
     gender VARCHAR(10),
     birthdate TIMESTAMP,
     email VARCHAR(200),
     team_id BIGINT,
     company BIGINT NOT NULL,
     score BIGINT,
     question_group_id BIGINT NOT NULL,
--      FOREIGN KEY (question_group_id)
--        REFERENCES public.question_group ON DELETE CASCADE,
--      FOREIGN KEY (team_id)
--        REFERENCES public.team ON DELETE CASCADE,
--      FOREIGN KEY (company)
--        REFERENCES public.company ON DELETE CASCADE
);

CREATE TABLE public.plan_accomplished
(
    id           BIGINT NOT NULL PRIMARY KEY,
    date_created TIMESTAMP,
    user_id      BIGINT,
    plan_id      BIGINT,
--     FOREIGN KEY (user_id)
--         REFERENCES public.user (id) ON DELETE CASCADE,
--     FOREIGN KEY (plan_id)
--         REFERENCES public.plan (id) ON DELETE CASCADE
);

CREATE TABLE public.transmission_log
(
  id      BIGINT NOT NULL primary key,
  question_id BIGINT,
  date_created TIMESTAMP,
  user_id BIGINT,
  state VARCHAR(250),
--   FOREIGN KEY (user_id)
--     REFERENCES public.user (id) on DELETE CASCADE,
--   FOREIGN KEY (question_id)
--     REFERENCES public.question (id) ON DELETE CASCADE
);

CREATE TABLE public.answers
(
    id                BIGINT PRIMARY KEY,
    question_id BIGINT,
    date_created      TIMESTAMP,
    user_id           BIGINT,
    points            BIGINT,
    answer            VARCHAR(250),
    --FOREIGN KEY (user_id)
    --    REFERENCES public.user (id) ON DELETE CASCADE,
    --FOREIGN KEY (question_id)
    --   REFERENCES public.question (id) ON DELETE CASCADE
);

CREATE TABLE public.platform(
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(5000)
);

CREATE TABLE public.platform_to_user
(
    id                    BIGINT NOT NULL PRIMARY KEY,
    user_id            BIGINT,
    platform_id           BIGINT,
--     FOREIGN KEY (user_id)
--         REFERENCES public.user (id) ON DELETE CASCADE,
--     FOREIGN KEY (platform_id)
--         REFERENCES public.platform (id) ON DELETE CASCADE
);