DROP TABLE SOME_ENTITY
#

CREATE TABLE SOME_ENTITY
(
    ID  NUMBER GENERATED by default on null as IDENTITY,
    NAME       VARCHAR2(100)
)
#

INSERT INTO SOME_ENTITY ("NAME") VALUES ('Jens')
#

CREATE OR REPLACE PROCEDURE NO_IN_NO_OUT_NO_RETURN
AS BEGIN
    INSERT INTO SOME_ENTITY (NAME) VALUES ('NO_IN_NO_OUT_NO_RETURN');
END;
#