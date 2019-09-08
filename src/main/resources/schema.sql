DROP SCHEMA PUBLIC CASCADE;

-- CREATE SCHEMA public;

DROP SEQUENCE IF EXISTS content_sequence;

CREATE
    SEQUENCE content_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS team_sequence;

CREATE
  SEQUENCE team_sequence AS INTEGER START
  WITH 1;

DROP SEQUENCE IF EXISTS media_sequence;

CREATE
    SEQUENCE media_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS user_sequence;

CREATE
    SEQUENCE user_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS company_sequence;

CREATE
    SEQUENCE company_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS plan_sequence;

CREATE
    SEQUENCE plan_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS time_to_send_sequence;

CREATE
    SEQUENCE time_to_send_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS platform_sequence;

CREATE
    SEQUENCE platform_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS platform_to_user_sequence;

CREATE
    SEQUENCE platform_to_user_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS platform_to_company_sequence;

CREATE
    SEQUENCE platform_to_company_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS plan_accomplished_sequence;

CREATE
    SEQUENCE plan_accomplished_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS answers_sequence;

CREATE
    SEQUENCE answers_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS question_group_sequence;

CREATE
    SEQUENCE question_group_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS question_sequence;

CREATE
    SEQUENCE question_sequence AS INTEGER START
    WITH 1;

DROP SEQUENCE IF EXISTS transmission_log_sequence;

CREATE
    SEQUENCE transmission_log_sequence AS INTEGER START
    WITH 1;

DROP TABLE IF EXISTS public.company CASCADE;

CREATE TABLE public.company (
                              id BIGINT NOT NULL PRIMARY KEY,
                              date_created TIMESTAMP,
                              name VARCHAR(200)
);

DROP TABLE IF EXISTS public.question_group CASCADE;

CREATE TABLE public.question_group (
   id BIGINT NOT NULL PRIMARY KEY,
   name VARCHAR(250) NOT NULL,
   date_created TIMESTAMP,
   date_modified TIMESTAMP,
   company_id BIGINT,
   active BOOLEAN, -- if groups finish we will want to keep their info to archive it but stop sending content
   FOREIGN KEY (company_id)
     REFERENCES public.company (id) on DELETE CASCADE
);

DROP TABLE IF EXISTS public.media CASCADE;

CREATE TABLE public.media
(
    id     BIGINT NOT NULL PRIMARY KEY,
    date_created TIMESTAMP,
    date_modified TIMESTAMP,
    type   VARCHAR(5000),
    source VARCHAR(5000)
);

DROP TABLE IF EXISTS public.question CASCADE;

CREATE TABLE public.question
(
-- we probably need a diffrent unique identifier to match questions so leads_to_question points to that instead(name?)
--  beucase it would make adding content difficult
    id                     BIGINT NOT NULL PRIMARY KEY,
    date_created           TIMESTAMP,
    date_modified          TIMESTAMP,
    name                   VARCHAR(300),
    description            VARCHAR(500),
    text                   VARCHAR(5000),
    can_be_random_selected BOOLEAN DEFAULT FALSE,
    leads_to_question_id   BIGINT,
    media_id               BIGINT,
    FOREIGN KEY (media_id)
        REFERENCES public.media (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS public.time_to_send CASCADE;

CREATE TABLE public.time_to_send
(
    id                BIGINT NOT NULL PRIMARY KEY,
    date_created      TIMESTAMP,
    time_to_send      DATE
);

DROP TABLE IF EXISTS public.plan CASCADE;

CREATE TABLE public.plan
(
    id              BIGINT NOT NULL PRIMARY KEY,
    date_created    TIMESTAMP,
    date_modified   TIMESTAMP,
    question_id      BIGINT,
    time_to_send_id BIGINT,
    day             BIGINT,
    company_id      BIGINT,
    question_group_id BIGINT,
    -- we will have unique plans for groups so we could have one default content(question_group_id NULL = default)
    --      but we will need to add this ID for unique content i think?
    FOREIGN KEY (company_id)
        REFERENCES public.company (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id)
        REFERENCES public.question (id) ON DELETE CASCADE,
    FOREIGN KEY (time_to_send_id)
        REFERENCES public.time_to_send (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS public.team CASCADE;

CREATE TABLE public.team (
                           id BIGINT NOT NULL PRIMARY KEY,
                           name VARCHAR(200) NOT NULL,
                           date_created TIMESTAMP,
                           date_modified TIMESTAMP,
                           score BIGINT
);

DROP TABLE IF EXISTS public.bot_user CASCADE;

CREATE TABLE public.bot_user (
     id BIGINT NOT NULL PRIMARY KEY,
     first_name VARCHAR(200),
     last_name VARCHAR(200),
     date_created TIMESTAMP,
     date_modified TIMESTAMP,
     gender VARCHAR(10),
     birthdate TIMESTAMP,
     email VARCHAR(200),
     team_id BIGINT,
     company BIGINT,
     score BIGINT,
     question_group_id BIGINT NOT NULL,
     active BOOLEAN, -- individual users will want to leave service but we want to keep their data(archive later)
     FOREIGN KEY (question_group_id)
       REFERENCES public.question_group ON DELETE CASCADE,
     FOREIGN KEY (team_id)
       REFERENCES public.team ON DELETE CASCADE,
     FOREIGN KEY (company)
       REFERENCES public.company ON DELETE CASCADE
);

DROP TABLE IF EXISTS public.plan_accomplished CASCADE;

CREATE TABLE public.plan_accomplished
(
    id           BIGINT NOT NULL PRIMARY KEY,
    date_created TIMESTAMP,
    user_id      BIGINT,
    plan_id      BIGINT,
    content_sent BOOLEAN,
    FOREIGN KEY (user_id)
        REFERENCES public.bot_user (id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id)
        REFERENCES public.plan (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS public.transmission_log CASCADE;

CREATE TABLE public.transmission_log
(
  id      BIGINT NOT NULL primary key,
  question_id BIGINT,
  date_created TIMESTAMP,
  user_id BIGINT,
  state VARCHAR(250),
  FOREIGN KEY (user_id)
    REFERENCES public.bot_user (id) on DELETE CASCADE,
  FOREIGN KEY (question_id)
    REFERENCES public.question (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS public.answers CASCADE;

CREATE TABLE public.answers
(
    id                BIGINT NOT NULL PRIMARY KEY,
    question_id BIGINT,
    date_created      TIMESTAMP,
    user_id           BIGINT,
    points            BIGINT,
    answer            VARCHAR(250),
    FOREIGN KEY (user_id)
        REFERENCES public.bot_user (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id)
        REFERENCES public.question (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS public.platform CASCADE;

CREATE TABLE public.platform(
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(5000)
);

DROP TABLE IF EXISTS public.platform_to_user CASCADE;

CREATE TABLE public.platform_to_user
(
    id                    BIGINT NOT NULL PRIMARY KEY,
    user_id            BIGINT,
    platform_id           BIGINT,
    platform_specific_data VARCHAR(500),
    FOREIGN KEY (user_id)
        REFERENCES public.bot_user (id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id)
        REFERENCES public.platform (id) ON DELETE CASCADE
);