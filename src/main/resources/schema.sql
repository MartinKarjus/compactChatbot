DROP SCHEMA PUBLIC CASCADE;

CREATE
    SEQUENCE order_sequence AS INTEGER START
    WITH 1;

CREATE
    SEQUENCE question_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE question_query_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE question_group_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE person_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE asked_questions_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE action_plan_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE chatfuel_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE time_of_day_sequence AS INTEGER START
    WITH 1;

CREATE
    SEQUENCE content_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE media_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE layout_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE bot_user_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE usergroup_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE plan_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE time_to_send_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE time_as_string_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE platform_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE platform_to_usergroup_sequence AS INTEGER START
    WITH 1;
CREATE
    SEQUENCE plan_accomplished_sequence AS INTEGER START
    WITH 1;






CREATE TABLE layout
(
    id   BIGINT NOT NULL PRIMARY KEY,
    type VARCHAR(5000)
);

CREATE TABLE media
(
    id     BIGINT NOT NULL PRIMARY KEY,
    type   VARCHAR(5000),
    source VARCHAR(5000)
);

CREATE TABLE question
(
    id                     BIGINT NOT NULL PRIMARY KEY,
    text                   VARCHAR(5000),
    can_be_random_selected BOOLEAN DEFAULT FALSE,
    media_id               BIGINT,
    FOREIGN KEY (media_id)
        REFERENCES media (id) ON DELETE CASCADE
);

CREATE TABLE content
(
    id                   BIGINT NOT NULL PRIMARY KEY,
    leads_to_content_id  BIGINT,
    leads_to_question_id BIGINT,
    text                 VARCHAR(5000),
    media_id             BIGINT,
    FOREIGN KEY (leads_to_content_id)
        REFERENCES content (id) ON DELETE CASCADE,
    FOREIGN KEY (media_id)
        REFERENCES media (id) ON DELETE CASCADE,
    FOREIGN KEY (leads_to_question_id)
        REFERENCES question (id) ON DELETE CASCADE
);

CREATE TABLE question_query
(
    id                  BIGINT NOT NULL PRIMARY KEY,
    query_text          VARCHAR(5000),
    query_response      VARCHAR(5000),
    leads_to_content_id BIGINT,
    media_id            BIGINT,
    question_id         BIGINT,
    FOREIGN KEY (question_id)
        REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (leads_to_content_id)
        REFERENCES content (id) ON DELETE CASCADE,
    FOREIGN KEY (media_id)
        REFERENCES media (id) ON DELETE CASCADE
);

CREATE TABLE time_as_string
(
    id        BIGINT NOT NULL PRIMARY KEY,
    name      VARCHAR(5000),
    send_time DATETIME
);

CREATE TABLE time_to_send
(
    id                BIGINT NOT NULL PRIMARY KEY,
    time_as_datetime  DATETIME,
    time_as_string_id BIGINT,
    FOREIGN KEY (time_as_string_id)
        REFERENCES time_as_string (id) ON DELETE CASCADE
);

CREATE TABLE usergroup
(
    id         BIGINT NOT NULL PRIMARY KEY,
    start_date DATE,
);

CREATE TABLE plan
(
    id              BIGINT NOT NULL PRIMARY KEY,
    content_id      BIGINT,
    time_to_send_id BIGINT,
    day             BIGINT,
    usergroup_id BIGINT,
    FOREIGN KEY (usergroup_id)
        REFERENCES usergroup (id) ON DELETE CASCADE,
    FOREIGN KEY (content_id)
        REFERENCES content (id) ON DELETE CASCADE,
    FOREIGN KEY (time_to_send_id)
        REFERENCES time_to_send (id) ON DELETE CASCADE
);

CREATE TABLE bot_user
(
    id           BIGINT NOT NULL PRIMARY KEY,
    usergroup_id BIGINT,
    first_name   VARCHAR(5000),
    last_name    VARCHAR(5000),
    FOREIGN KEY (usergroup_id)
        REFERENCES usergroup (id) ON DELETE CASCADE
);

CREATE TABLE plan_accomplished
(
    id           BIGINT NOT NULL PRIMARY KEY,
    user_id      BIGINT,
    plan_id      BIGINT,
    FOREIGN KEY (user_id)
        REFERENCES bot_user (id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id)
        REFERENCES plan (id) ON DELETE CASCADE
);

CREATE TABLE user_answers
(
    question_query_id BIGINT,
    user_id           BIGINT,
    FOREIGN KEY (user_id)
        REFERENCES bot_user (id) ON DELETE CASCADE,
    FOREIGN KEY (question_query_id)
        REFERENCES question_query (id) ON DELETE CASCADE
);

CREATE TABLE platform(
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(5000)
);

CREATE TABLE platform_to_usergroup
(
    id BIGINT NOT NULL PRIMARY KEY,
    usergroup_id BIGINT,
    platform_id           BIGINT,
    FOREIGN KEY (usergroup_id)
        REFERENCES usergroup (id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id)
        REFERENCES question_query (id) ON DELETE CASCADE
);






























CREATE TABLE orders
(
    id           BIGINT NOT NULL PRIMARY KEY,
    order_number VARCHAR(255)
);

CREATE TABLE order_rows
(
    item_name VARCHAR(255),
    price     INT,
    quantity  INT,
    orders_id BIGINT,
    FOREIGN KEY (orders_id)
        REFERENCES orders (id) ON DELETE CASCADE
);



CREATE TABLE time_of_day
(
    id              BIGINT NOT NULL PRIMARY KEY,
    ask_after_time  TIME,
    ask_before_time TIME
);

CREATE TABLE question_group
(
    id       BIGINT NOT NULL PRIMARY KEY,
    platform VARCHAR(200),
    company  VARCHAR(200)
);



CREATE TABLE person
(
    id                BIGINT NOT NULL PRIMARY KEY,
    question_group_id BIGINT NOT NULL,
    FOREIGN KEY (question_group_id)
        REFERENCES question_group (id) ON DELETE CASCADE
);

CREATE TABLE chatfuel
(
    id          BIGINT NOT NULL PRIMARY KEY,
    person_id   BIGINT,
    chatfuel_id VARCHAR(200),
    FOREIGN KEY (person_id)
        REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE asked_questions
(
    id                BIGINT NOT NULL PRIMARY KEY,
    person_id         BIGINT,
    question_group_id BIGINT,
    question_id       BIGINT,
    FOREIGN KEY (question_id)
        REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (question_group_id)
        REFERENCES question_group (id) ON DELETE CASCADE,
    FOREIGN KEY (person_id)
        REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE action_plan
(
    id                BIGINT NOT NULL PRIMARY KEY,
    question_id       BIGINT,
    ask_date          DATE,
    asked             BOOLEAN DEFAULT FALSE,
    question_group_id BIGINT,
    time_of_day_id    BIGINT,
    ask_at_time       TIME,
    FOREIGN KEY (question_id)
        REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (question_group_id)
        REFERENCES question_group (id) ON DELETE CASCADE,
    FOREIGN KEY (time_of_day_id)
        REFERENCES time_of_day (id) ON DELETE CASCADE
);

CREATE TABLE team
(
    id        BIGINT NOT NULL PRIMARY KEY,
    person_id BIGINT,
    team_id   BIGINT,
    FOREIGN KEY (person_id)
        REFERENCES person (id) ON DELETE CASCADE
);