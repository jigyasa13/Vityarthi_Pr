-- Run this file in MySQL to create schema + sample data
CREATE DATABASE IF NOT EXISTS sis_jdbc;
USE sis_jdbc;

CREATE TABLE IF NOT EXISTS Student (
  register_no VARCHAR(20) PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  dob DATE,
  email VARCHAR(150),
  phone VARCHAR(20),
  address TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Course (
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  course_code VARCHAR(16) UNIQUE NOT NULL,
  title VARCHAR(200) NOT NULL,
  credits INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Semester (
  semester_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  start_date DATE,
  end_date DATE
);

CREATE TABLE IF NOT EXISTS Enrollment (
  enroll_id INT AUTO_INCREMENT PRIMARY KEY,
  register_no VARCHAR(20),
  course_id INT,
  semester_id INT,
  marks INT,
  grade VARCHAR(3),
  grade_points DECIMAL(4,2),
  CONSTRAINT fk_enroll_student FOREIGN KEY (register_no) REFERENCES Student(register_no) ON DELETE CASCADE,
  CONSTRAINT fk_enroll_course FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE,
  CONSTRAINT fk_enroll_semester FOREIGN KEY (semester_id) REFERENCES Semester(semester_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS AttendanceRecord (
  att_id INT AUTO_INCREMENT PRIMARY KEY,
  register_no VARCHAR(20),
  course_id INT,
  att_date DATE NOT NULL,
  status ENUM('P','A','L') NOT NULL,
  CONSTRAINT fk_att_student FOREIGN KEY (register_no) REFERENCES Student(register_no) ON DELETE CASCADE,
  CONSTRAINT fk_att_course FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

DELIMITER $$
CREATE TRIGGER IF NOT EXISTS student_register_no_check
BEFORE INSERT ON Student
FOR EACH ROW
BEGIN
  IF LEFT(NEW.register_no,1) <> '2' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'register_no must start with "2"';
  END IF;
END$$
DELIMITER ;

INSERT IGNORE INTO Student (register_no, first_name, last_name, dob, email, phone, address)
VALUES
('20001','Asha','Rao','2003-04-12','asha.rao@example.com','9876543210','Mumbai'),
('20002','Rajat','Singh','2002-08-03','rajat.singh@example.com','9876501234','Delhi');

INSERT IGNORE INTO Course (course_code, title, credits) VALUES
('CS101','Intro to Programming',4),
('CS102','Data Structures',4),
('MA101','Calculus I',3);

INSERT IGNORE INTO Semester (name, start_date, end_date) VALUES
('Sem1','2025-01-01','2025-05-31'),
('Sem2','2025-07-01','2025-11-30');

INSERT IGNORE INTO Enrollment (register_no, course_id, semester_id, marks, grade, grade_points)
VALUES
('20001', 1, 1, 85, 'A', 8.5),
('20001', 2, 1, 78, 'B+', 7.8),
('20002', 1, 1, 65, 'C', 6.5),
('20002', 3, 1, 72, 'B', 7.2);

INSERT IGNORE INTO AttendanceRecord (register_no, course_id, att_date, status) VALUES
('20001',1,'2025-02-01','P'),
('20001',1,'2025-02-03','A'),
('20001',2,'2025-02-01','P'),
('20002',1,'2025-02-01','L');
