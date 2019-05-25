DROP SCHEMA PUBLIC CASCADE;

CREATE SEQUENCE order_sequence AS INTEGER START WITH 1;

CREATE SEQUENCE question_sequence AS INTEGER START WITH 1;
CREATE SEQUENCE question_query_sequence AS INTEGER START WITH 1;
CREATE SEQUENCE question_group_sequence AS INTEGER START WITH 1;
CREATE SEQUENCE person_sequence AS INTEGER START WITH 1;
CREATE SEQUENCE asked_questions_sequence AS INTEGER START WITH 1;
CREATE SEQUENCE action_plan_sequence AS INTEGER START WITH 1;

CREATE TABLE orders (
  id BIGINT NOT NULL PRIMARY KEY,
  order_number VARCHAR(255)
);

CREATE TABLE order_rows (
  item_name VARCHAR(255),
  price INT,
  quantity INT,
  orders_id BIGINT,
  FOREIGN KEY (orders_id)
    REFERENCES orders ON DELETE CASCADE
);








CREATE TABLE question (
 id BIGINT NOT NULL PRIMARY KEY,
 text VARCHAR(5000),
 can_be_random_selected BOOLEAN DEFAULT FALSE
);

CREATE TABLE question_query (
 id BIGINT NOT NULL PRIMARY KEY,
 query_text VARCHAR(5000),
 query_response VARCHAR(5000),
 question_id BIGINT,
 FOREIGN KEY (question_id)
    REFERENCES question ON DELETE CASCADE
);

CREATE TABLE time_of_day (
 id BIGINT NOT NULL PRIMARY KEY,
 ask_after_time TIME,
 ask_before_time TIME
);

CREATE TABLE question_group (
 id BIGINT NOT NULL PRIMARY KEY,
 platform VARCHAR(200),
 company VARCHAR(200)
);


CREATE TABLE chatfuel (
  id BIGINT NOT NULL PRIMARY KEY,
  person_id BIGINT,
  chatfuel_id BIGINT,
  FOREIGN KEY (person_id)
    REFERENCES person ON DELETE CASCADE
)

CREATE TABLE person (
 id BIGINT NOT NULL PRIMARY KEY,
 question_group_id BIGINT NOT NULL,
 FOREIGN KEY (question_group_id)
    REFERENCES question_group ON DELETE CASCADE
);

CREATE TABLE asked_questions (
 id BIGINT NOT NULL PRIMARY KEY,
 person_id BIGINT,
 question_group_id BIGINT,
 question_id BIGINT,
 FOREIGN KEY (question_id)
    REFERENCES question ON DELETE CASCADE,
 FOREIGN KEY (question_group_id)
    REFERENCES question_group ON DELETE CASCADE,
 FOREIGN KEY (person_id)
    REFERENCES person ON DELETE CASCADE
);

CREATE TABLE action_plan (
 id BIGINT NOT NULL PRIMARY KEY,
 question_id BIGINT,
 ask_date DATE,
 asked BOOLEAN DEFAULT FALSE,
 question_group_id BIGINT,
 time_of_day_id BIGINT,
 ask_at_time TIME,
 FOREIGN KEY (question_id)
    REFERENCES question ON DELETE CASCADE,
 FOREIGN KEY (question_group_id)
    REFERENCES question_group ON DELETE CASCADE,
 FOREIGN KEY (time_of_day_id)
    REFERENCES time_of_day ON DELETE CASCADE
);

CREATE TABLE action_plan_archive (
 id BIGINT NOT NULL PRIMARY KEY,
);


