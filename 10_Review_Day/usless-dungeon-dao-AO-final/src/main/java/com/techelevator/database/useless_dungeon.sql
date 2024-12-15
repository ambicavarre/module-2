--Database Name: useless_dungeon
BEGIN TRANSACTION;

DROP TABLE IF EXISTS room_monsters, heroes, monsters, professions, rooms;


CREATE TABLE IF NOT EXISTS monsters
(
    monster_id int NOT NULL,
    monster_name varchar(20) NOT NULL,
    monster_health int NOT NULL,
    monster_defense int NOT NULL,
	monster_attack_description text NOT NULL,
    monster_damage int NOT NULL,
    CONSTRAINT pk_monster PRIMARY KEY (monster_id)
);

CREATE TABLE IF NOT EXISTS professions
(
    profession_id int NOT NULL,
    profession_name varchar(20) NOT NULL,
    starting_health int NOT NULL,
    starting_defense int NOT NULL,
    starting_attack int NOT NULL,
    starting_attack_description varchar(20) NOT NULL,
    CONSTRAINT pk_profession PRIMARY KEY (profession_id)
);

CREATE TABLE IF NOT EXISTS heroes
(
    hero_id serial NOT NULL,
    hero_name varchar(20) NOT NULL,
    hero_health int NOT NULL,
    hero_defense int NOT NULL,
    hero_profession_id int NOT NULL,
    CONSTRAINT pk_hero PRIMARY KEY (hero_id),
	CONSTRAINT fk_hero_to_profession FOREIGN KEY (hero_profession_id) REFERENCES professions (profession_id)
);

CREATE TABLE IF NOT EXISTS rooms
(
    room_id integer NOT NULL,
    room_name character varying COLLATE pg_catalog."default" NOT NULL,
    room_description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_room PRIMARY KEY (room_id)
);

CREATE TABLE IF NOT EXISTS room_monsters
(
	room_id integer NOT NULL,
	monster_id integer NOT NULL,
	CONSTRAINT PK_room_monsters PRIMARY KEY(room_id, monster_id)
);


--Insert Room Records
INSERT INTO rooms (room_id, room_name, room_description) VALUES (1, 'Entry Hall', 'This 10 foot by 20 foot entry hall has stone walls with a Door on the east side leading to another room.');
INSERT INTO rooms (room_id, room_name, room_description) VALUES (2, 'Dinning Hall', 'This 20 foot by 30 foot dinning area has Velvet Elvis Paintings with a Door on the south wall leading to another room.');
INSERT INTO rooms (room_id, room_name, room_description) VALUES (3, 'Dance Studio', 'This 40 foot by 50 foot dance studio has Mirrored Walls with a Door on the west wall leading to another room.');
INSERT INTO rooms (room_id, room_name, room_description) VALUES (4, 'Arcade', 'This 20 foot by 20 foot arcade has posters of Metallica  with a Door on the north wall leading to another room. Cletus is gently restrained in the comfy chair.');

--Insert Monster Records
INSERT INTO monsters (monster_id, monster_name, monster_health, monster_defense, monster_attack_description, monster_damage) VALUES (1, 'Velociraptor', 50, 100, 'Tube Launched Optically Tracked Wire Guided Anti Personnel Missile', 500);
INSERT INTO monsters (monster_id, monster_name, monster_health, monster_defense, monster_attack_description, monster_damage) VALUES (2, 'Darth Vader', 100, 1000, 'Red Laser Sword', 5000);
INSERT INTO monsters (monster_id, monster_name, monster_health, monster_defense, monster_attack_description, monster_damage) VALUES (3, 'The Gelatinous Cube', 500, 100, 'Gelatinous Envelopment', 500);


--Insert Room Monsters Records
INSERT INTO public.room_monsters(room_id, monster_id)  VALUES (2, 1);
INSERT INTO public.room_monsters(room_id, monster_id)  VALUES (3, 2);
INSERT INTO public.room_monsters(room_id, monster_id)  VALUES (4, 3);

--Professions Meta Table
INSERT INTO professions (profession_id, profession_name, starting_health, starting_defense, starting_attack, starting_attack_description) VALUES (1, 'Warrior', 10, 5, 10, 'Claymore');
INSERT INTO professions (profession_id, profession_name, starting_health, starting_defense, starting_attack, starting_attack_description) VALUES (2, 'Wizard', 4, 4, 100, 'Fireball');
INSERT INTO professions (profession_id, profession_name, starting_health, starting_defense, starting_attack, starting_attack_description) VALUES (3, 'Rogue', 6, 10, 6, 'Crossbow');
INSERT INTO professions (profession_id, profession_name, starting_health, starting_defense, starting_attack, starting_attack_description) VALUES (4, 'Healer', 8, 6, 4, 'Staff');

--Starting Characters
INSERT INTO public.heroes(hero_name, hero_health, hero_defense, hero_profession_id)	VALUES ('Mungo', 10, 5, 1);
INSERT INTO public.heroes(hero_name, hero_health, hero_defense, hero_profession_id)	VALUES ('Rizard', 4, 4, 2);
INSERT INTO public.heroes(hero_name, hero_health, hero_defense, hero_profession_id)	VALUES ('Shifty', 6, 10, 3);
INSERT INTO public.heroes(hero_name, hero_health, hero_defense, hero_profession_id)	VALUES ('Curad', 8, 6, 4);

COMMIT;