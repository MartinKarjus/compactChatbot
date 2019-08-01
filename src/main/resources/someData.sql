INSERT INTO orders (id, order_number)
  VALUES (1, 'Num1');

INSERT INTO orders (id, order_number)
  VALUES (2, 'Num2');




INSERT INTO action_plan (id, question_id, ask_date, asked, question_group_id, time_of_day_id, ask_at_time)
  VALUES(1, NULL, NULL , false, NULL, NULL, NULL);




INSERT INTO question (id, text, can_be_random_selected, media_id)
  VALUES(1,'cake or glory?', false, null);

INSERT INTO content(id,leads_to_content_id, leads_to_question_id, text, media_id)
  VALUES(1,null,1,'Informative text about cake', null);

INSERT INTO question_query(id,query_text, leads_to_content_id, media_id, question_id)
  VALUES(1,'cake', null , null , 1);
INSERT INTO question_query(id,query_text, leads_to_content_id, media_id, question_id)
  VALUES(2,'glory', null , null , 1);

INSERT INTO time_to_send(id, time_as_datetime, time_as_string_id)
  VALUES(1, '2019-08-01 00:00:00' , null );

INSERT INTO usergroup(id, start_date)
 VALUES(1, '2019-08-01');

INSERT INTO plan(id, content_id, time_to_send_id, day,usergroup_id)
  VALUES(1, 1, 1, 0, 1);

INSERT INTO bot_user(id, usergroup_id, first_name, last_name)
  VALUES(1,1,'tester', 'testLname');

INSERT INTO platform(id, name)
  VALUES(1, 'chatfuel');

INSERT INTO platform_to_usergroup(id,usergroup_id, platform_id)
  VALUES(1,1, 1);
















