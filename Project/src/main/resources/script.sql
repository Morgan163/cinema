CREATE SEQUENCE THEATER_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE AGE_LIMIT_TYPES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE FILM_TYPES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE SEATS_SEANCES_STATUSES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE FILMS_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE LINES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE SEANCES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE SEAT_TYPES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE SEATS_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE SEATS_SEANCES_STATUSES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE USER_ROLES_ID_SEQUENCE
START WITH 100;

CREATE SEQUENCE USERS_ID_SEQUENCE
START WITH 100;


CREATE TABLE AGE_LIMIT_TYPES
(
AGE_LIMIT_ID INTEGER NOT NULL ,
AGE_LIMIT_VALUE INTEGER NOT NULL
);

ALTER TABLE AGE_LIMIT_TYPES
ADD CONSTRAINT XPKAGE_LIMIT_TYPES PRIMARY KEY (AGE_LIMIT_ID);

CREATE TABLE FILM_TYPES
(
FILM_TYPE_ID INTEGER NOT NULL ,
FILM_TYPE_NAME VARCHAR2(20) NULL
);

ALTER TABLE FILM_TYPES
ADD CONSTRAINT XPKSEANCE_TYPES PRIMARY KEY (FILM_TYPE_ID);

CREATE TABLE FILMS
(
FILM_ID INTEGER NOT NULL ,
FILM_NAME VARCHAR2(20) NOT NULL ,
FILM_TYPE_ID INTEGER NULL ,
AGE_LIMIT_ID INTEGER NULL
);

ALTER TABLE FILMS
ADD CONSTRAINT XPKFILMS PRIMARY KEY (FILM_ID);

CREATE TABLE LINES
(
LINE_NUMBER INTEGER NOT NULL ,
LINE_ID INTEGER NOT NULL ,
THEATER_ID INTEGER NULL
);

ALTER TABLE LINES
ADD CONSTRAINT XPKLINES PRIMARY KEY (LINE_ID);

CREATE TABLE SEANCES
(
SEANCE_ID INTEGER NOT NULL ,
FILM_ID INTEGER NOT NULL ,
BASE_PRICE_VALUE INTEGER NOT NULL ,
SEANCE_START_DATE TIMESTAMP NOT NULL
);

ALTER TABLE SEANCES
ADD CONSTRAINT XPKSEANCES PRIMARY KEY (SEANCE_ID);

CREATE TABLE SEAT_TYPES
(
SEAT_TYPE_ID INTEGER NOT NULL ,
SEAT_TYPE_NAME VARCHAR2(20) NOT NULL
);

ALTER TABLE SEAT_TYPES
ADD CONSTRAINT XPKSEAT_TYPES PRIMARY KEY (SEAT_TYPE_ID);

CREATE TABLE SEATS
(
SEAT_NUMBER INTEGER NOT NULL ,
SEAT_TYPE_ID INTEGER NULL ,
LINE_ID INTEGER NULL ,
SEAT_ID INTEGER NOT NULL
);

ALTER TABLE SEATS
ADD CONSTRAINT XPKSEAT PRIMARY KEY (SEAT_ID);

CREATE TABLE SEATS_SEANCES_STATUSES
(
STATUS_ID INTEGER NOT NULL ,
STATUS_NAME VARCHAR2(20) NULL
);

ALTER TABLE SEATS_SEANCES_STATUSES
ADD CONSTRAINT XPKSEATS_SEANCES_STATUSES PRIMARY KEY (STATUS_ID);

CREATE TABLE SEATS_SEANCES
(
MAPPING_ID INTEGER NOT NULL,
SEAT_ID INTEGER NULL ,
SEANCE_ID INTEGER NULL ,
STATUS_ID INTEGER NULL ,
BOOK_KEY VARCHAR2(20) NULL
);

ALTER TABLE SEATS_SEANCES
ADD CONSTRAINT XPKSEATS_SEANCES PRIMARY KEY (MAPPING_ID);

CREATE TABLE THEATERS
(
THEATER_ID INTEGER NOT NULL ,
THEATER_NUMBER INTEGER NULL
);

ALTER TABLE THEATERS
ADD CONSTRAINT XPKTHEATERS PRIMARY KEY (THEATER_ID);

CREATE TABLE USER_ROLES
(
ROLE_ID INTEGER NOT NULL ,
ROLE_NAME VARCHAR2(20) NULL
);

ALTER TABLE USER_ROLES
ADD CONSTRAINT XPKUSER_ROLES PRIMARY KEY (ROLE_ID);

CREATE TABLE USERS
(
USER_ID INTEGER NOT NULL ,
LOGIN VARCHAR2(20) NOT NULL ,
PASSWORD VARCHAR2(20) NOT NULL ,
ROLE_ID INTEGER NOT NULL
);

ALTER TABLE USERS ADD
NAME VARCHAR(20) NULL,
SECOND_NAME VARCHAR(20) NULL,
MIDDLE_NAME VARCHAR(20) NULL;

ALTER TABLE USERS
ADD CONSTRAINT XPKUSERS PRIMARY KEY (USER_ID);

ALTER TABLE FILMS
ADD (CONSTRAINT R_11 FOREIGN KEY (FILM_TYPE_ID) REFERENCES FILM_TYPES (FILM_TYPE_ID) ON DELETE SET NULL);

ALTER TABLE FILMS
ADD (CONSTRAINT R_12 FOREIGN KEY (AGE_LIMIT_ID) REFERENCES AGE_LIMIT_TYPES (AGE_LIMIT_ID) ON DELETE SET NULL);

ALTER TABLE LINES
ADD (CONSTRAINT R_10 FOREIGN KEY (THEATER_ID) REFERENCES THEATER (THEATER_ID) ON DELETE SET NULL);

ALTER TABLE SEANCES
ADD (CONSTRAINT R_13 FOREIGN KEY (FILM_ID) REFERENCES FILMS (FILM_ID) ON DELETE SET NULL);

ALTER TABLE SEATS
ADD (CONSTRAINT R_1 FOREIGN KEY (SEAT_TYPE_ID) REFERENCES SEAT_TYPES (SEAT_TYPE_ID) ON DELETE SET NULL);

ALTER TABLE SEATS
ADD (CONSTRAINT R_4 FOREIGN KEY (LINE_ID) REFERENCES LINES (LINE_ID) ON DELETE SET NULL);

ALTER TABLE SEATS_SEANCES
ADD (CONSTRAINT R_6 FOREIGN KEY (SEAT_ID) REFERENCES SEATS (SEAT_ID) ON DELETE SET NULL);

ALTER TABLE SEATS_SEANCES
ADD (CONSTRAINT R_7 FOREIGN KEY (SEANCE_ID) REFERENCES SEANCES (SEANCE_ID) ON DELETE SET NULL);

ALTER TABLE SEATS_SEANCES
ADD (CONSTRAINT R_8 FOREIGN KEY (STATUS_ID) REFERENCES SEATS_SEANCES_STATUSES (STATUS_ID) ON DELETE SET NULL);

ALTER TABLE USERS
ADD (CONSTRAINT R_14 FOREIGN KEY (ROLE_ID) REFERENCES USER_ROLES (ROLE_ID) ON DELETE SET NULL);

Insert into CINEMA.THEATERS (THEATER_ID,THEATER_NUMBER) values ('1','1');
Insert into CINEMA.THEATERS (THEATER_ID,THEATER_NUMBER) values ('2','2');

Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('1','1','1');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('2','2','1');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('3','3','1');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('4','4','1');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('5','5','1');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('1','6','2');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('2','7','2');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('3','8','2');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('4','9','2');
Insert into CINEMA.LINES (LINE_NUMBER,LINE_ID,THEATER_ID) values ('5','10','2');

Insert into CINEMA.SEAT_TYPES (SEAT_TYPE_ID,SEAT_TYPE_NAME) values ('1','VIP');
Insert into CINEMA.SEAT_TYPES (SEAT_TYPE_ID,SEAT_TYPE_NAME) values ('0','GENERIC');

Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('6','0','1','6');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','1','2','7');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','1','2','8');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','1','2','9');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','1','3','12');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','1','4','13');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','1','4','14');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','1','4','15');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','0','5','16');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','0','5','17');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','0','5','18');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('4','0','5','19');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('5','0','5','20');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('6','0','5','21');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','1','6','22');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','1','6','23');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','1','6','24');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','1','7','25');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','1','7','26');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','1','7','27');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','1','8','28');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','1','8','29');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','1','8','30');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','0','9','31');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','0','9','32');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','0','9','33');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('4','0','9','34');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('5','0','9','35');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','0','1','1');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','0','1','2');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','0','1','3');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('4','0','1','4');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('5','0','1','5');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','1','3','10');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','1','3','11');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('6','0','9','36');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('1','0','10','37');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('2','0','10','38');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('3','0','10','39');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('4','0','10','40');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('5','0','10','41');
Insert into CINEMA.SEATS (SEAT_NUMBER,SEAT_TYPE_ID,LINE_ID,SEAT_ID) values ('6','0','10','42');

Insert into CINEMA.AGE_LIMIT_TYPES (AGE_LIMIT_ID,AGE_LIMIT_VALUE) values ('0','0');
Insert into CINEMA.AGE_LIMIT_TYPES (AGE_LIMIT_ID,AGE_LIMIT_VALUE) values ('1','6');
Insert into CINEMA.AGE_LIMIT_TYPES (AGE_LIMIT_ID,AGE_LIMIT_VALUE) values ('2','12');
Insert into CINEMA.AGE_LIMIT_TYPES (AGE_LIMIT_ID,AGE_LIMIT_VALUE) values ('3','16');
Insert into CINEMA.AGE_LIMIT_TYPES (AGE_LIMIT_ID,AGE_LIMIT_VALUE) values ('4','18');

Insert into CINEMA.FILM_TYPES (FILM_TYPE_ID,FILM_TYPE_NAME) values ('0','Драма');
Insert into CINEMA.FILM_TYPES (FILM_TYPE_ID,FILM_TYPE_NAME) values ('1','Ужасы');