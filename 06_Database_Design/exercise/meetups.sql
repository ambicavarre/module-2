START TRANSACTION;
DROP TABLE IF EXISTS members,interest_groups,events,event_attendance,member_groups;

CREATE TABLE members (
    member_number serial,
    last_name varchar(50) NOT NULL,
    first_name varchar(50) NOT NULL,
    email_address varchar(100) NOT NULL UNIQUE,
    phone_number varchar(15),
    date_of_birth date NOT NULL,
    reminder_email_flag boolean NOT NULL,
	constraint pk_members primary key (member_number)
);


CREATE TABLE interest_groups (
    group_number serial,
    name varchar(100) NOT NULL UNIQUE,
	constraint pk_interest_groups primary key (group_number)
);

CREATE TABLE events (
    event_number serial,
    name varchar(100) NOT NULL,
    description text,
    start_date_time timestamp NOT NULL,
    duration int CHECK (Duration >= 30),
    group_number int,
    constraint pk_events primary key (event_number),
	constraint fk_events_interest_groups foreign key (group_number) references interest_groups(group_number)
);


CREATE TABLE member_groups (
    member_number int,
    group_number int,
    constraint pk_member_groups primary key (member_number, group_number),
    constraint fk_member_groups_members foreign key (member_number) references members(member_number),
    constraint fk_member_groups_interest_groups foreign key (group_number) references interest_groups(group_number)
);


CREATE TABLE event_attendance (
    member_number int,
    event_number int,
    constraint pk_event_attendance primary key (member_number, event_number),
    constraint fk_event_attendance_members foreign key (member_number) references members(member_number),
    constraint fk_event_attendance_events foreign key (event_number) references events(event_number)
);

INSERT INTO members (last_name, first_name, email_address, phone_number, date_of_birth, reminder_email_flag)
VALUES
('Doe', 'John', 'john.doe@example.com', '555-1234', '1985-06-15', TRUE),
('Smith', 'Jane', 'jane.smith@example.com', NULL, '1990-04-25', FALSE),
('Brown', 'Emily', 'emily.brown@example.com', '555-5678', '1988-02-10', TRUE),
('Johnson', 'Chris', 'chris.johnson@example.com', '555-8765', '1992-11-30', TRUE),
('Garcia', 'Luis', 'luis.garcia@example.com', NULL, '1985-09-14', FALSE),
('Martinez', 'Ana', 'ana.martinez@example.com', '555-4321', '1991-01-22', TRUE),
('Wilson', 'Sara', 'sara.wilson@example.com', NULL, '1987-07-08', TRUE),
('Lee', 'David', 'david.lee@example.com', '555-6789', '1993-05-18', FALSE);

INSERT INTO interest_groups (name)
VALUES
('Tech Enthusiasts'),
('Art Lovers'),
('Outdoor Adventurers');

INSERT INTO events (name, description, start_date_time, duration, group_number)
VALUES
('Tech Talk', 'Discussion on the latest in technology.', '2024-10-15 18:00:00', 60, 1),
('Art Workshop', 'Hands-on painting workshop.', '2024-10-20 14:00:00', 90, 2),
('Hiking Trip', 'Group hiking trip to the mountains.', '2024-10-25 08:00:00', 120, 3),
('Outdoor Movie Night', 'Watch movies under the stars.', '2024-10-30 19:00:00', 150, 3);

INSERT INTO member_groups (member_number, group_number)
VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 3),
(7, 1),
(8, 2);


INSERT INTO event_attendance (member_number, event_number)
VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 3),
(1, 4),
(7, 4),
(8, 4);



COMMIT;
