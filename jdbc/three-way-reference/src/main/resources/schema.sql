CREATE TABLE COURSE
(
    ID   INTEGER IDENTITY PRIMARY KEY,
    NAME VARCHAR(200) NOT NULL
);

CREATE TABLE STUDENT
(
    ID   INTEGER IDENTITY PRIMARY KEY,
    NAME VARCHAR(200) NOT NULL
);

CREATE TABLE STUDENT_COURSE
(
    ID INTEGER IDENTITY PRIMARY KEY,
    STUDENT_ID INTEGER NOT NULL,
    COURSE_ID  INTEGER NOT NULL,
    UNIQUE (STUDENT_ID, COURSE_ID),
    FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(ID),
    FOREIGN KEY (COURSE_ID) REFERENCES COURSE(ID)
);

CREATE TABLE TEST_SCORE
(
    STUDENT_COURSE_ID INTEGER,
    INDEX INTEGER,
    VALUE            INTEGER,
    PRIMARY KEY (STUDENT_COURSE_ID, INDEX),
    FOREIGN KEY (STUDENT_COURSE_ID) REFERENCES STUDENT_COURSE(ID)
);