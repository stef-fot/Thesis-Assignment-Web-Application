CREATE DATABASE IF NOT EXISTS `diplomas`;
USE `diplomas`;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL ,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE professors (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL ,
  full_name VARCHAR(255)  ,
  specialty VARCHAR(255) ,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE subjects (
  subject_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) ,
  description VARCHAR(255),
  supervisor_id INT,
  FOREIGN KEY (supervisor_id) REFERENCES professors(id)
);

CREATE TABLE thesis (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  supervisor_id INT NOT NULL,
  thesis_title VARCHAR(255),
  assigned_student_name VARCHAR(255),
  implementation_grade DOUBLE,
  report_grade DOUBLE,
  presentation_grade DOUBLE,
  average_grade DOUBLE,
  FOREIGN KEY (supervisor_id) REFERENCES professors(id)
);

CREATE TABLE students (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  full_name VARCHAR(255)  ,
  year_of_studies INT ,
  current_average_grade DOUBLE  ,
  remaining_cources_for_graduation INT  ,
  assigned_subject_id INT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (assigned_subject_id) REFERENCES thesis(id)
);

CREATE TABLE applications (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  applicant_id INT NOT NULL,
  subject_id INT NOT NULL,
  application_date DATE NOT NULL,
  FOREIGN KEY (applicant_id) REFERENCES students(id),
  FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);
 
-- DROP DATABASE diplomas;
