INSERT INTO public.company (ID, date_created, name) VALUES (1, '2019-01-01', 'CompAct');
INSERT INTO public.company (ID, date_created, name) VALUES (2, '2019-05-07', 'EstEtno');

INSERT INTO public.platform (ID, name) VALUES (1, 'Chatfuel');

INSERT INTO public.team (ID, name, date_created, date_modified, score)
VALUES (1, 'Siberian Tigers', '2019-01-01', '2019-01-01', 200);

INSERT INTO public.team (ID, name, date_created, date_modified, score)
VALUES (2, 'Grand Sloths', '2019-01-01', '2019-01-01', 300);

INSERT INTO public.team (ID, name, date_created, date_modified, score)
VALUES (3, 'Forgotten Beavers', '2019-05-07', '2019-05-06', 300);

INSERT INTO public.question_group (ID, name, date_created, date_modified, company_id, active)
VALUES (1, 'Commuting', '2019-01-01', '2019-01-01', 1, TRUE);

INSERT INTO public.question_group (ID, name, date_created, date_modified, company_id, active)
VALUES (2, 'Food', '2019-05-07', '2019-05-07', 2, TRUE);

INSERT INTO public.bot_user (id, first_name, last_name, date_created, date_modified, gender,
birthdate, email, team_id, company, score, question_group_id, active)
VALUES (1, 'Rain', 'Vagel', '2019-05-07', '2019-05-07', 'Male', '1995-05-07', 'rain.vagel@gmail.com', 3,
 2, 300, 2, TRUE);

INSERT INTO public.bot_user (id, first_name, last_name, date_created, date_modified, gender,
birthdate, email, team_id, company, score, question_group_id, active)
VALUES (2, 'Egle', 'Truska', '2019-01-01', '2019-01-01', 'Female', '1993-05-07', 'egle.truska@gmail.com', 2,
 1, 150, 1, TRUE);

INSERT INTO public.bot_user (id, first_name, last_name, date_created, date_modified, gender,
birthdate, email, team_id, company, score, question_group_id, active)
VALUES (3, 'Kaisa', 'Hansen', '2019-01-01', '2019-01-01', 'Female', '1989-05-07', 'kaisa.hansen@gmail.com', 2,
 1, 100, 1, TRUE);

INSERT INTO public.bot_user (id, first_name, last_name, date_created, date_modified, gender,
birthdate, email, team_id, company, score, question_group_id, active)
VALUES (4, 'Marie', 'Udam', '2019-01-01', '2019-01-01', 'Female', '1993-05-07', 'marie.udam@gmail.com', 2,
 1, 50, 1, TRUE);

INSERT INTO public.bot_user (id, first_name, last_name, date_created, date_modified, gender,
birthdate, email, team_id, company, score, question_group_id, active)
VALUES (5, 'Martin', 'Karjus', '2019-01-01', '2019-01-01', 'Male', '1998-05-07', 'martin.karjus@gmail.com', 1,
 1, 200, 1, TRUE);

INSERT INTO public.platform_to_user (id, user_id, platform_id, platform_specific_data)
VALUES (1, 1, 1, null);

INSERT INTO public.platform_to_user (id, user_id, platform_id, platform_specific_data)
VALUES (2, 2, 1, null);

INSERT INTO public.platform_to_user (id, user_id, platform_id, platform_specific_data)
VALUES (3, 3, 1, null);

INSERT INTO public.platform_to_user (id, user_id, platform_id, platform_specific_data)
VALUES (4, 4, 1, null);

INSERT INTO public.platform_to_user (id, user_id, platform_id, platform_specific_data)
VALUES (5, 5, 1, null);

INSERT INTO public.question (id, date_created, date_modified, name, description, text, can_be_random_selected,
leads_to_question_id, media_id)
VALUES (1, '2019-01-01', '2019-01-01', 'Road congestion', 'In the commuting group.', 'Roads congested',
FALSE, 2, null);

INSERT INTO public.question (id, date_created, date_modified, name, description, text, can_be_random_selected,
leads_to_question_id, media_id)
VALUES (2, '2019-01-01', '2019-01-01', 'Public transport', 'In the commuting group.', 'Use public transport',
FALSE, 3, null);

INSERT INTO public.question (id, date_created, date_modified, name, description, text, can_be_random_selected,
leads_to_question_id, media_id)
VALUES (3, '2019-01-01', '2019-01-01', 'Carpool', 'In the commuting group.', 'Carpool with colleagues',
FALSE, null, null);

INSERT INTO public.question (id, date_created, date_modified, name, description, text, can_be_random_selected,
leads_to_question_id, media_id)
VALUES (4, '2019-01-01', '2019-01-01', 'Vegetarian', 'In the food group.', 'Go vegetarian',
FALSE, 5, null);

INSERT INTO public.question (id, date_created, date_modified, name, description, text, can_be_random_selected,
leads_to_question_id, media_id)
VALUES (5, '2019-01-01', '2019-01-01', 'SHopping', 'In the food group.', 'Own containers',
FALSE, 6, null);

INSERT INTO public.question (id, date_created, date_modified, name, description, text, can_be_random_selected,
leads_to_question_id, media_id)
VALUES (6, '2019-01-01', '2019-01-01', 'Drinks', 'In the food group.', 'Do not use straws',
FALSE, null, null);

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (1, 1, '2019-01-01', 2, 50, 'Bike');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (2, 2, '2019-01-02', 2, 50, 'No');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (3, 3, '2019-01-03', 2, 50, 'No');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (4, 1, '2019-01-01', 3, 20, 'Public Transport');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (5, 2, '2019-01-02', 3, 50, 'Yes');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (6, 3, '2019-01-03', 3, 20, 'No');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (7, 1, '2019-01-01', 4, 50, 'Bike');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (8, 1, '2019-01-01', 5, 50, 'On Foot');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (9, 2, '2019-01-02', 5, 50, 'No');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (10, 3, '2019-01-03', 5, 100, 'All');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (11, 4, '2019-01-01', 1, 100, 'Never');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (12, 5, '2019-01-02', 1, 100, 'Yes');

INSERT INTO public.answers (id, question_id, date_created, user_id, points, answer)
VALUES (13, 6, '2019-01-03', 1, 100, 'No');
