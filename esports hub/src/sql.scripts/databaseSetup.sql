CREATE TABLE Team (
tID   INTEGER PRIMARY KEY,
name  VARCHAR(32) UNIQUE NOT NULL,
owner VARCHAR(32)
);

CREATE TABLE Achievement (
season VARCHAR(6),
year INTEGER,
placement INTEGER,
tID  INTEGER NOT NULL,
PRIMARY KEY (season, year, placement),
FOREIGN KEY (tID) REFERENCES Team (tID),
UNIQUE (season, year, tID)
);

CREATE TABLE Roster (
tID INTEGER,
season VARCHAR(6),
year   INTEGER,
wins   INTEGER NOT NULL,
losses INTEGER NOT NULL,
PRIMARY KEY (season, year, tID),
FOREIGN KEY (tID) REFERENCES Team (tID)
);


CREATE TABLE TeamMember (
tmID INTEGER PRIMARY KEY,
name VARCHAR(32),
age  INTEGER
);



CREATE TABLE PartOfRoster (
season VARCHAR(6),
year   INTEGER,
tID INTEGER,
tmID   INTEGER,
PRIMARY KEY (season, year, tID, tmID),
FOREIGN KEY (season, year, tID) REFERENCES Roster (season, year, tID),
FOREIGN KEY (tmID) REFERENCES TeamMember
);

CREATE TABLE Player (
tmID INTEGER PRIMARY KEY,
position VARCHAR(7),
alias VARCHAR(20) UNIQUE NOT NULL,
FOREIGN KEY (tmID) REFERENCES TeamMember (tmID)
);


CREATE TABLE Staff (
tmID INTEGER PRIMARY KEY,
role VARCHAR(20),
FOREIGN KEY (tmID) REFERENCES TeamMember
);


CREATE TABLE Arena (
aID  INTEGER PRIMARY KEY,
name VARCHAR(64) NOT NULL,
city VARCHAR(32) NOT NULL,
capacity INTEGER NOT NULL,
UNIQUE (name, city)
);



CREATE TABLE Game (
gID  INTEGER PRIMARY KEY,
rtID INTEGER NOT NULL,
btID INTEGER NOT NULL,
day  DATE,
aID  INTEGER NOT NULL,
FOREIGN KEY (rtID) REFERENCES Team (tID),
FOREIGN KEY (btID) REFERENCES Team (tID),
FOREIGN KEY (aID) REFERENCES Arena (aID)
);


CREATE TABLE PlaysIn (
gID INTEGER,
pID INTEGER,
PRIMARY KEY (gID, pID),
FOREIGN KEY (gID) REFERENCES Game (gID)
   ON DELETE CASCADE,
FOREIGN KEY (pID) REFERENCES Player (tmID)
);


CREATE TABLE SeasonDates (
day DATE PRIMARY KEY,
season VARCHAR(6)
);


CREATE TABLE Caster (
cID  INTEGER PRIMARY KEY,
name VARCHAR(32) NOT NULL
);



CREATE TABLE Casts (
cID INTEGER,
gID INTEGER,
language VARCHAR(20) NOT NULL,
PRIMARY KEY (cID, gID),
FOREIGN KEY (cID) REFERENCES Caster (cID),
FOREIGN KEY (gID) REFERENCES Game (gID)
ON DELETE CASCADE
);


CREATE TABLE Viewer (
vID  INTEGER PRIMARY KEY,
name VARCHAR(32)
);



CREATE TABLE Seat (
 aID INTEGER,
 seatNum INTEGER,
 price   FLOAT(5),
 PRIMARY KEY (aID, seatNum),
 FOREIGN KEY (aID) REFERENCES Arena (aID)
);

CREATE TABLE Ticket (
ticketNum INTEGER PRIMARY KEY,
vID  INTEGER,
gID  INTEGER NOT NULL,
aID  INTEGER NOT NULL,
seatNum   INTEGER NOT NULL,
FOREIGN KEY (vID) REFERENCES Viewer (vID),
FOREIGN KEY (gID) REFERENCES Game (gID)
   ON DELETE CASCADE,
FOREIGN KEY (aID, seatNum) REFERENCES Seat (aID, seatNum),
UNIQUE (gID, seatNum)
);

INSERT INTO Team VALUES (1, 'Cloud9', 'Jack Etienne');
INSERT INTO Team VALUES (2, '100 Thieves', 'Matthew Haag');
INSERT INTO Team VALUES (3, 'Team Liquid', 'Steve Arhancet');
INSERT INTO Team VALUES (4, 'Team SoloMid', 'Andy Dinh');
INSERT INTO Team VALUES (5, 'Evil Geniuses', 'Nicole LaPointe Jameson');

INSERT INTO Achievement VALUES ('Spring', 2022, 1, 1);
INSERT INTO Achievement VALUES ('Spring', 2022, 2, 5);
INSERT INTO Achievement VALUES ('Spring', 2022, 3, 4);
INSERT INTO Achievement VALUES ('Spring', 2022, 4, 3);
INSERT INTO Achievement VALUES ('Spring', 2022, 5, 2);
INSERT INTO Achievement VALUES ('Summer', 2022, 1, 1);
INSERT INTO Achievement VALUES ('Summer', 2022, 2, 5);
INSERT INTO Achievement VALUES ('Summer', 2022, 3, 4);
INSERT INTO Achievement VALUES ('Summer', 2022, 4, 3);
INSERT INTO Achievement VALUES ('Summer', 2022, 5, 2);
INSERT INTO Achievement VALUES ('Summer', 2021, 1, 1);
INSERT INTO Achievement VALUES ('Summer', 2021, 2, 5);
INSERT INTO Achievement VALUES ('Summer', 2021, 3, 4);
INSERT INTO Achievement VALUES ('Summer', 2021, 4, 3);
INSERT INTO Achievement VALUES ('Summer', 2021, 5, 2);

INSERT INTO Roster VALUES (1, 'Spring', 2022, 6, 1);
INSERT INTO Roster VALUES (2, 'Spring', 2022, 6, 2);
INSERT INTO Roster VALUES (3, 'Spring', 2022, 6, 3);
INSERT INTO Roster VALUES (4, 'Spring', 2022, 1, 4);
INSERT INTO Roster VALUES (5, 'Spring', 2022, 1, 3);
INSERT INTO Roster VALUES (1, 'Summer', 2022, 1, 3);
INSERT INTO Roster VALUES (2, 'Summer', 2022, 1, 3);
INSERT INTO Roster VALUES (3, 'Summer', 2022, 2, 4);
INSERT INTO Roster VALUES (4, 'Summer', 2022, 2, 4);
INSERT INTO Roster VALUES (5, 'Summer', 2022, 2, 4);
INSERT INTO Roster VALUES (1, 'Summer', 2021, 2, 3);
INSERT INTO Roster VALUES (2, 'Summer', 2021, 2, 3);
INSERT INTO Roster VALUES (3, 'Summer', 2021, 4, 3);
INSERT INTO Roster VALUES (4, 'Summer', 2021, 4, 4);
INSERT INTO Roster VALUES (5, 'Summer', 2021, 4, 5);


INSERT INTO TeamMember VALUES (1, 'Ibrahim Allami', 21);
INSERT INTO TeamMember VALUES (2, 'Can Celik', 22);
INSERT INTO TeamMember VALUES (3, 'Soren Bjerg', 23);
INSERT INTO TeamMember VALUES (4, 'Peng Yiliang', 24);
INSERT INTO TeamMember VALUES (5, 'Philippe Laflamme', 25);
INSERT INTO TeamMember VALUES (6, 'Daniel Guy', 31);
INSERT INTO TeamMember VALUES (7, 'Brian Pang', 32);
INSERT INTO TeamMember VALUES (8, 'Mickey Mouse', 33);
INSERT INTO TeamMember VALUES (9, 'Barack Obama', 34);
INSERT INTO TeamMember VALUES (10, 'Peter Parker', 35);
INSERT INTO TeamMember VALUES (11, 'Guy Name 11', 21);
INSERT INTO TeamMember VALUES (12, 'Guy Name 12', 22);
INSERT INTO TeamMember VALUES (13, 'Guy Name 13', 23);
INSERT INTO TeamMember VALUES (14, 'Guy Name 14', 24);
INSERT INTO TeamMember VALUES (15, 'Guy Name 15', 25);
INSERT INTO TeamMember VALUES (16, 'Guy Name 16',20);
INSERT INTO TeamMember VALUES (17, 'Guy Name 17',20);
INSERT INTO TeamMember VALUES (18, 'Guy Name 18',20);
INSERT INTO TeamMember VALUES (19, 'Guy Name 19',20);
INSERT INTO TeamMember VALUES (20, 'Guy Name 20',20);
INSERT INTO TeamMember VALUES (21, 'Guy Name 21',20);
INSERT INTO TeamMember VALUES (22, 'Guy Name 22',23);
INSERT INTO TeamMember VALUES (23, 'Guy Name 23',23);
INSERT INTO TeamMember VALUES (24, 'Guy Name 24',23);
INSERT INTO TeamMember VALUES (25, 'Guy Name 25',23);
INSERT INTO TeamMember VALUES (26, 'Guy Name 26',23);
INSERT INTO TeamMember VALUES (27, 'Guy Name 27',21);
INSERT INTO TeamMember VALUES (28, 'Guy Name 28',21);
INSERT INTO TeamMember VALUES (29, 'Guy Name 29',21);
INSERT INTO TeamMember VALUES (30, 'Guy Name 30',21);
INSERT INTO TeamMember VALUES (31, 'Guy Name 31',21);
INSERT INTO TeamMember VALUES (32, 'Guy Name 32',20);
INSERT INTO TeamMember VALUES (33, 'Guy Name 33',20);
INSERT INTO TeamMember VALUES (34, 'Guy Name 34',20);
INSERT INTO TeamMember VALUES (35, 'Guy Name 35',20);
INSERT INTO TeamMember VALUES (36, 'Guy Name 36',27);
INSERT INTO TeamMember VALUES (37, 'Guy Name 37',27);
INSERT INTO TeamMember VALUES (38, 'Guy Name 38',27);
INSERT INTO TeamMember VALUES (39, 'Guy Name 39',27);
INSERT INTO TeamMember VALUES (40, 'Guy Name 40',27);
INSERT INTO TeamMember VALUES (41, 'Guy Name 41',20);
INSERT INTO TeamMember VALUES (42, 'Guy Name 42',29);
INSERT INTO TeamMember VALUES (43, 'Guy Name 43',29);
INSERT INTO TeamMember VALUES (44, 'Guy Name 44',19);
INSERT INTO TeamMember VALUES (45, 'Guy Name 45',19);
INSERT INTO TeamMember VALUES (46, 'Guy Name 46',19);
INSERT INTO TeamMember VALUES (47, 'Guy Name 47',16);
INSERT INTO TeamMember VALUES (48, 'Guy Name 48',16);
INSERT INTO TeamMember VALUES (49, 'Guy Name 49',26);
INSERT INTO TeamMember VALUES (50, 'Guy Name 50',26);
INSERT INTO TeamMember VALUES (51, 'Guy Name 51',26);
INSERT INTO TeamMember VALUES (52, 'Guy Name 52',20);
INSERT INTO TeamMember VALUES (53, 'Guy Name 53',20);
INSERT INTO TeamMember VALUES (54, 'Guy Name 54',20);
INSERT INTO TeamMember VALUES (55, 'Guy Name 55',20);
INSERT INTO TeamMember VALUES (56, 'Guy Name 56',22);
INSERT INTO TeamMember VALUES (57, 'Guy Name 57',22);
INSERT INTO TeamMember VALUES (58, 'Guy Name 58',22);
INSERT INTO TeamMember VALUES (59, 'Guy Name 59',22);
INSERT INTO TeamMember VALUES (60, 'Guy Name 60',22);
INSERT INTO TeamMember VALUES (61, 'Guy Name 61',20);
INSERT INTO TeamMember VALUES (62, 'Guy Name 62',20);
INSERT INTO TeamMember VALUES (63, 'Guy Name 63',20);
INSERT INTO TeamMember VALUES (64, 'Guy Name 64',25);
INSERT INTO TeamMember VALUES (65, 'Guy Name 65',25);
INSERT INTO TeamMember VALUES (66, 'Guy Name 66',25);
INSERT INTO TeamMember VALUES (67, 'Guy Name 67',25);
INSERT INTO TeamMember VALUES (68, 'Guy Name 68',25);
INSERT INTO TeamMember VALUES (69, 'Guy Name 69',22);
INSERT INTO TeamMember VALUES (70, 'Guy Name 70',22);
INSERT INTO TeamMember VALUES (71, 'Guy Name 71',22);
INSERT INTO TeamMember VALUES (72, 'Guy Name 72',22);
INSERT INTO TeamMember VALUES (73, 'Guy Name 73',17);
INSERT INTO TeamMember VALUES (74, 'Guy Name 74',17);
INSERT INTO TeamMember VALUES (75, 'Guy Name 75',17);
INSERT INTO TeamMember VALUES (76, 'Guy Name 76',17);
INSERT INTO TeamMember VALUES (77, 'Guy Name 77',17);
INSERT INTO TeamMember VALUES (78, 'Guy Name 78',21);
INSERT INTO TeamMember VALUES (79, 'Guy Name 79',21);
INSERT INTO TeamMember VALUES (80, 'Guy Name 80',31);
INSERT INTO TeamMember VALUES (81, 'Guy Name 81',31);
INSERT INTO TeamMember VALUES (82, 'Guy Name 82',31);
INSERT INTO TeamMember VALUES (83, 'Guy Name 83',30);
INSERT INTO TeamMember VALUES (84, 'Guy Name 84',30);

INSERT INTO Player VALUES (1, 'TOP', 'Fudge');
INSERT INTO Player VALUES (2, 'JNG', 'Closer');
INSERT INTO Player VALUES (3, 'MID', 'Bjergsen');
INSERT INTO Player VALUES (4, 'ADC', 'Doublelift');
INSERT INTO Player VALUES (5, 'SUP', 'Vulcan');
INSERT INTO Player VALUES (6, 'TOP', 'Woll');
INSERT INTO Player VALUES (7, 'JNG', 'Poll');
INSERT INTO Player VALUES (8, 'MID', 'Toll');
INSERT INTO Player VALUES (9, 'ADC', 'Foll');
INSERT INTO Player VALUES (10, 'SUP', 'Goll');
INSERT INTO Player VALUES (11, 'TOP', 'Nill');
INSERT INTO Player VALUES (12, 'JNG', 'Jill');
INSERT INTO Player VALUES (13, 'MID', 'Hill');
INSERT INTO Player VALUES (14, 'ADC', 'Bill');
INSERT INTO Player VALUES (15, 'SUP', 'Dill');
INSERT INTO Player VALUES (16, 'TOP', 'Will');
INSERT INTO Player VALUES (17, 'JNG', 'Pill');
INSERT INTO Player VALUES (18, 'MID', 'Till');
INSERT INTO Player VALUES (19, 'ADC', 'Fill');
INSERT INTO Player VALUES (20, 'SUP', 'Gill');
INSERT INTO Player VALUES (21, 'TOP', 'Nell');
INSERT INTO Player VALUES (22, 'JNG', 'Jell');
INSERT INTO Player VALUES (23, 'MID', 'Hell');
INSERT INTO Player VALUES (24, 'ADC', 'Bell');
INSERT INTO Player VALUES (25, 'SUP', 'Dell');
INSERT INTO Player VALUES (26, 'TOP', 'Well');
INSERT INTO Player VALUES (27, 'JNG', 'Pell');
INSERT INTO Player VALUES (28, 'MID', 'Tell');
INSERT INTO Player VALUES (29, 'ADC', 'Fell');
INSERT INTO Player VALUES (30, 'SUP', 'Gell');
INSERT INTO Player VALUES (31, 'TOP', 'Null');
INSERT INTO Player VALUES (32, 'JNG', 'Jull');
INSERT INTO Player VALUES (33, 'MID', 'Hull');
INSERT INTO Player VALUES (34, 'ADC', 'Bull');
INSERT INTO Player VALUES (35, 'SUP', 'Dull');
INSERT INTO Player VALUES (36, 'TOP', 'Wull');
INSERT INTO Player VALUES (37, 'JNG', 'Pull');
INSERT INTO Player VALUES (38, 'MID', 'Tull');
INSERT INTO Player VALUES (39, 'ADC', 'Full');
INSERT INTO Player VALUES (40, 'SUP', 'Gull');
INSERT INTO Player VALUES (41, 'TOP', 'Nall');
INSERT INTO Player VALUES (42, 'JNG', 'Jall');
INSERT INTO Player VALUES (43, 'MID', 'Hall');
INSERT INTO Player VALUES (44, 'ADC', 'Ball');
INSERT INTO Player VALUES (45, 'SUP', 'Dall');
INSERT INTO Player VALUES (46, 'TOP', 'Wall');
INSERT INTO Player VALUES (47, 'JNG', 'Pall');
INSERT INTO Player VALUES (48, 'MID', 'Tall');
INSERT INTO Player VALUES (49, 'ADC', 'Fall');
INSERT INTO Player VALUES (50, 'SUP', 'Gall');
INSERT INTO Player VALUES (51, 'TOP', 'Nellt');
INSERT INTO Player VALUES (52, 'JNG', 'Jellt');
INSERT INTO Player VALUES (53, 'MID', 'Hellt');
INSERT INTO Player VALUES (54, 'ADC', 'Bellt');
INSERT INTO Player VALUES (55, 'SUP', 'Dellt');
INSERT INTO Player VALUES (56, 'TOP', 'Wellt');
INSERT INTO Player VALUES (57, 'JNG', 'Pellt');
INSERT INTO Player VALUES (58, 'MID', 'Tellt');
INSERT INTO Player VALUES (59, 'ADC', 'Fellt');
INSERT INTO Player VALUES (60, 'SUP', 'Gellt');
INSERT INTO Player VALUES (61, 'TOP', 'Nullt');
INSERT INTO Player VALUES (62, 'JNG', 'Jullt');
INSERT INTO Player VALUES (63, 'MID', 'Hullt');
INSERT INTO Player VALUES (64, 'ADC', 'Bullt');
INSERT INTO Player VALUES (65, 'SUP', 'Dullt');
INSERT INTO Player VALUES (66, 'TOP', 'Wullt');
INSERT INTO Player VALUES (67, 'JNG', 'Pullt');
INSERT INTO Player VALUES (68, 'MID', 'Tullt');
INSERT INTO Player VALUES (69, 'ADC', 'Fullt');
INSERT INTO Player VALUES (70, 'SUP', 'Gullt');
INSERT INTO Player VALUES (71, 'TOP', 'Nallt');
INSERT INTO Player VALUES (72, 'JNG', 'Jallt');
INSERT INTO Player VALUES (73, 'MID', 'Hallt');
INSERT INTO Player VALUES (74, 'ADC', 'Ballt');
INSERT INTO Player VALUES (75, 'SUP', 'Dallt');


INSERT INTO Staff VALUES (76, 'Coach');
INSERT INTO Staff VALUES (77, 'Assistant Coach');
INSERT INTO Staff VALUES (78, 'Analyst');
INSERT INTO Staff VALUES (79, 'Sports Psychologist');
INSERT INTO Staff VALUES (80, 'Positional Coach');
INSERT INTO Staff VALUES (81, 'Manager');
INSERT INTO Staff VALUES (82, 'Coach');
INSERT INTO Staff VALUES (83, 'Analyst');
INSERT INTO Staff VALUES (84, 'Assistant Coach');


INSERT INTO Arena VALUES (1, 'Rogers Arena', 'Vancouver', 10);
INSERT INTO Arena VALUES (2, 'LCS Arena', 'Los Angeles', 20);
INSERT INTO Arena VALUES (3, 'LEC Studio', 'Berlin', 10);
INSERT INTO Arena VALUES (4, 'LPL Stadium', 'Shanghai', 5);
INSERT INTO Arena VALUES (5, 'LOL Park', 'Seoul', 20);

INSERT INTO Game VALUES (1, 1, 2, '22-OCT-10', 2);
INSERT INTO Game VALUES (2, 2, 3, '22-JAN-23', 3);
INSERT INTO Game VALUES (3, 4, 3, '22-OCT-30', 3);
INSERT INTO Game VALUES (4, 1, 4, '22-FEB-27', 4);
INSERT INTO Game VALUES (5, 3, 5, '22-JUN-25', 5);


INSERT INTO SeasonDates VALUES ('22-SEP-21', 'Spring');
INSERT INTO SeasonDates VALUES ('22-OCT-10', 'Spring');
INSERT INTO SeasonDates VALUES ('22-JAN-23', 'Spring');
INSERT INTO SeasonDates VALUES ('22-OCT-30', 'Spring');
INSERT INTO SeasonDates VALUES ('22-FEB-27', 'Spring');
INSERT INTO SeasonDates VALUES ('22-JUN-25', 'Summer');
INSERT INTO SeasonDates VALUES ('22-FEB-21', 'Spring');
INSERT INTO SeasonDates VALUES ('22-OCT-23', 'Summer');


INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1,  1);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1,  2);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1,  3);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1,  4);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1,  5);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2,  6);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2,  7);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2,  8);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2,  9);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 10);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 11);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 12);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 13);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 14);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 15);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 16);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 17);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 18);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 19);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 20);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 5, 21);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 5, 22);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 5, 23);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 5, 24);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 5, 25);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 1, 26);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 1, 27);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 1, 28);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 1, 29);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 1, 30);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 2, 31);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 2, 32);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 2, 33);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 2, 34);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 2, 35);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 3, 36);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 3, 37);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 3, 38);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 3, 39);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 3, 40);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 4, 41);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 4, 42);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 4, 43);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 4, 44);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 4, 45);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 5, 46);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 5, 47);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 5, 48);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 5, 49);
INSERT INTO PartOfRoster VALUES ('Summer', 2022, 5, 50);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 1, 51);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 1, 52);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 1, 53);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 1, 54);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 1, 55);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 2, 56);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 2, 57);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 2, 58);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 2, 59);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 2, 60);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 3, 61);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 3, 62);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 3, 63);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 3, 64);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 3, 65);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 4, 66);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 4, 67);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 4, 68);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 4, 69);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 4, 70);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 5, 71);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 5, 72);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 5, 73);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 5, 74);
INSERT INTO PartOfRoster VALUES ('Summer', 2021, 5, 75);

INSERT INTO Caster VALUES (1, 'Kobe');
INSERT INTO Caster VALUES (2, 'CaptainFlowers');
INSERT INTO Caster VALUES (3, 'Phreak');
INSERT INTO Caster VALUES (4, 'Jatt');
INSERT INTO Caster VALUES (5, 'Azael');

INSERT INTO Casts VALUES (1,1, 'English');
INSERT INTO Casts VALUES (2,1, 'French');
INSERT INTO Casts VALUES (3,2, 'English');
INSERT INTO Casts VALUES (2,3, 'French');
INSERT INTO Casts VALUES (1,4, 'English');
INSERT INTO Casts VALUES (1,5, 'English');


INSERT INTO Viewer VALUES (1, 'Bob');
INSERT INTO Viewer VALUES (2, 'Joe');
INSERT INTO Viewer VALUES (3, 'Tom');
INSERT INTO Viewer VALUES (4, 'Sam');
INSERT INTO Viewer VALUES (5, 'Rob');
INSERT INTO Viewer VALUES (6, 'Joanna');
INSERT INTO Viewer VALUES (7, 'Rick');
INSERT INTO Viewer VALUES (8, 'Liam');
INSERT INTO Viewer VALUES (9, 'Pam');
INSERT INTO Viewer VALUES (10, 'Michael');
INSERT INTO Viewer VALUES (11, 'Jacob');
INSERT INTO Viewer VALUES (12, 'Isabella');
INSERT INTO Viewer VALUES (13, 'Cindy');
INSERT INTO Viewer VALUES (14, 'Tyler');
INSERT INTO Viewer VALUES (15, 'Bobette');
INSERT INTO Viewer VALUES (16, 'Rachel');
INSERT INTO Viewer VALUES (17, 'Noel');
INSERT INTO Viewer VALUES (18, 'Pierre');
INSERT INTO Viewer VALUES (19, 'Po');
INSERT INTO Viewer VALUES (20, 'Derek');

INSERT INTO Seat VALUES (1, 1, 50.00);
INSERT INTO Seat VALUES (1, 2, 50.00);
INSERT INTO Seat VALUES (1, 3, 50.00);
INSERT INTO Seat VALUES (1, 4, 50.00);
INSERT INTO Seat VALUES (1, 5, 50.00);
INSERT INTO Seat VALUES (1, 6, 60.00);
INSERT INTO Seat VALUES (1, 7, 60.00);
INSERT INTO Seat VALUES (1, 8, 60.00);
INSERT INTO Seat VALUES (1, 9, 60.00);
INSERT INTO Seat VALUES (1, 10, 60.00);

INSERT INTO Seat VALUES (2, 1, 30.00);
INSERT INTO Seat VALUES (2, 2, 30.00);
INSERT INTO Seat VALUES (2, 3, 30.00);
INSERT INTO Seat VALUES (2, 4, 30.00);
INSERT INTO Seat VALUES (2, 5, 30.00);
INSERT INTO Seat VALUES (2, 6, 30.00);
INSERT INTO Seat VALUES (2, 7, 30.00);
INSERT INTO Seat VALUES (2, 8, 30.00);
INSERT INTO Seat VALUES (2, 9, 30.00);
INSERT INTO Seat VALUES (2, 10, 30.00);
INSERT INTO Seat VALUES (2, 11, 50.00);
INSERT INTO Seat VALUES (2, 12, 50.00);
INSERT INTO Seat VALUES (2, 13, 50.00);
INSERT INTO Seat VALUES (2, 14, 50.00);
INSERT INTO Seat VALUES (2, 15, 50.00);
INSERT INTO Seat VALUES (2, 16, 70.00);
INSERT INTO Seat VALUES (2, 17, 70.00);
INSERT INTO Seat VALUES (2, 18, 70.00);
INSERT INTO Seat VALUES (2, 19, 70.00);
INSERT INTO Seat VALUES (2, 20, 70.00);

INSERT INTO Seat VALUES (3, 1, 30.00);
INSERT INTO Seat VALUES (3, 2, 30.00);
INSERT INTO Seat VALUES (3, 3, 30.00);
INSERT INTO Seat VALUES (3, 4, 30.00);
INSERT INTO Seat VALUES (3, 5, 30.00);
INSERT INTO Seat VALUES (3, 6, 20.00);
INSERT INTO Seat VALUES (3, 7, 20.00);
INSERT INTO Seat VALUES (3, 8, 20.00);
INSERT INTO Seat VALUES (3, 9, 20.00);
INSERT INTO Seat VALUES (3, 10, 20.00);

INSERT INTO Seat VALUES (4, 1, 50.00);
INSERT INTO Seat VALUES (4, 2, 50.00);
INSERT INTO Seat VALUES (4, 3, 50.00);
INSERT INTO Seat VALUES (4, 4, 50.00);
INSERT INTO Seat VALUES (4, 5, 50.00);

INSERT INTO Seat VALUES (5, 1, 20.00);
INSERT INTO Seat VALUES (5, 2, 20.00);
INSERT INTO Seat VALUES (5, 3, 20.00);
INSERT INTO Seat VALUES (5, 4, 20.00);
INSERT INTO Seat VALUES (5, 5, 20.00);
INSERT INTO Seat VALUES (5, 6, 20.00);
INSERT INTO Seat VALUES (5, 7, 20.00);
INSERT INTO Seat VALUES (5, 8, 20.00);
INSERT INTO Seat VALUES (5, 9, 20.00);
INSERT INTO Seat VALUES (5, 10, 50.00);
INSERT INTO Seat VALUES (5, 11, 50.00);
INSERT INTO Seat VALUES (5, 12, 50.00);
INSERT INTO Seat VALUES (5, 13, 50.00);
INSERT INTO Seat VALUES (5, 14, 50.00);
INSERT INTO Seat VALUES (5, 15, 50.00);
INSERT INTO Seat VALUES (5, 16, 50.00);
INSERT INTO Seat VALUES (5, 17, 50.00);
INSERT INTO Seat VALUES (5, 18, 50.00);
INSERT INTO Seat VALUES (5, 19, 50.00);
INSERT INTO Seat VALUES (5, 20, 50.00);

INSERT INTO Ticket VALUES (1, 1, 1, 2, 1);
INSERT INTO Ticket VALUES (2, 2, 1, 2, 2);
INSERT INTO Ticket VALUES (3, 3, 1, 2, 3);
INSERT INTO Ticket VALUES (4, 4, 1, 2, 4);
INSERT INTO Ticket VALUES (5, 5, 1, 2, 5);
INSERT INTO Ticket VALUES (6, 6, 1, 2, 6);
INSERT INTO Ticket VALUES (7, 7, 1, 2, 7);
INSERT INTO Ticket VALUES (8, 8, 1, 2, 8);
INSERT INTO Ticket VALUES (9, null, 1, 2, 9);
INSERT INTO Ticket VALUES (10, 1, 1, 2, 10);
INSERT INTO Ticket VALUES (11, 3, 1, 2, 11);
INSERT INTO Ticket VALUES (12, null, 1, 2, 12);
INSERT INTO Ticket VALUES (13, null, 1, 2, 13);
INSERT INTO Ticket VALUES (14, null, 1, 2, 14);
INSERT INTO Ticket VALUES (15, 2, 1, 2, 15);
INSERT INTO Ticket VALUES (16, null, 1, 2, 16);
INSERT INTO Ticket VALUES (17, null, 1, 2, 17);
INSERT INTO Ticket VALUES (18, null, 1, 2, 18);
INSERT INTO Ticket VALUES (19, null, 1, 2, 19);
INSERT INTO Ticket VALUES (20, null, 1, 2, 20);
INSERT INTO Ticket VALUES (21, null, 2, 3, 1);
INSERT INTO Ticket VALUES (22, null, 2, 3, 2);
INSERT INTO Ticket VALUES (23, null, 2, 3, 3);
INSERT INTO Ticket VALUES (24, null, 2, 3, 4);
INSERT INTO Ticket VALUES (25, 1, 2, 3, 5);
INSERT INTO Ticket VALUES (26, 3, 2, 3, 6);
INSERT INTO Ticket VALUES (27, null, 2, 3, 7);
INSERT INTO Ticket VALUES (28, null, 2, 3, 8);
INSERT INTO Ticket VALUES (29, null, 2, 3, 9);
INSERT INTO Ticket VALUES (30, null, 2, 3, 10);
INSERT INTO Ticket VALUES (31, 2, 3, 3, 1);
INSERT INTO Ticket VALUES (32, 1, 3, 3, 2);
INSERT INTO Ticket VALUES (33, null, 3, 3, 3);
INSERT INTO Ticket VALUES (34, null, 3, 3, 4);
INSERT INTO Ticket VALUES (35, null, 3, 3, 5);
INSERT INTO Ticket VALUES (36, null, 3, 3, 6);
INSERT INTO Ticket VALUES (37, null, 3, 3, 7);
INSERT INTO Ticket VALUES (38, null, 3, 3, 8);
INSERT INTO Ticket VALUES (39, null, 3, 3, 9);
INSERT INTO Ticket VALUES (40, null, 3, 3, 10);
INSERT INTO Ticket VALUES (41, 1, 4, 4, 1);
INSERT INTO Ticket VALUES (42, null, 4, 4, 2);
INSERT INTO Ticket VALUES (43, null, 4, 4, 3);
INSERT INTO Ticket VALUES (44, null, 4, 4, 4);
INSERT INTO Ticket VALUES (45, null, 4, 4, 5);
INSERT INTO Ticket VALUES (46, null, 5, 5, 1);
INSERT INTO Ticket VALUES (47, 3, 5, 5, 2);
INSERT INTO Ticket VALUES (48, 4, 5, 5, 3);
INSERT INTO Ticket VALUES (49, 5, 5, 5, 4);
INSERT INTO Ticket VALUES (50, null, 5, 5, 5);
INSERT INTO Ticket VALUES (51, 1, 5, 5, 6);
INSERT INTO Ticket VALUES (52, null, 5, 5, 7);
INSERT INTO Ticket VALUES (53, null, 5, 5, 8);
INSERT INTO Ticket VALUES (54, null, 5, 5, 9);
INSERT INTO Ticket VALUES (55, null, 5, 5, 10);
INSERT INTO Ticket VALUES (56, null, 5, 5, 11);
INSERT INTO Ticket VALUES (57, null, 5, 5, 12);
INSERT INTO Ticket VALUES (58, null, 5, 5, 13);
INSERT INTO Ticket VALUES (59, null, 5, 5, 14);
INSERT INTO Ticket VALUES (60, null, 5, 5, 15);
INSERT INTO Ticket VALUES (61, null, 5, 5, 16);
INSERT INTO Ticket VALUES (62, null, 5, 5, 17);
INSERT INTO Ticket VALUES (63, null, 5, 5, 18);
INSERT INTO Ticket VALUES (64, null, 5, 5, 19);
INSERT INTO Ticket VALUES (65, null, 5, 5, 20);


INSERT INTO PlaysIn VALUES (1, 1);
INSERT INTO PlaysIn VALUES (2, 2);
INSERT INTO PlaysIn VALUES (3, 3);
INSERT INTO PlaysIn VALUES (4, 4);
INSERT INTO PlaysIn VALUES (5, 5);


commit;
