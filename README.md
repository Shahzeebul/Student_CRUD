# Student_CRUD

DROP TABLE Student;

CREATE DATABASE StudentDB;
USE StudentDB;


CREATE TABLE Student (
    Enrollment INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Age INT,
    Course VARCHAR(50),
    
    Email VARCHAR(100),
    Password VARCHAR(50)
);


DESC Student;
SELECT * FROM Student;
