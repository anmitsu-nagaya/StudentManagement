CREATE TABLE students (
  id varchar(36) PRIMARY KEY,
  student_full_name varchar(100) NOT NULL,
  student_furigana varchar(100) NOT NULL,
  student_nickname varchar(50) DEFAULT NULL,
  email varchar(254) NOT NULL,
  prefecture varchar(10),
  city varchar(50),
  age int,
  gender varchar(20),
  student_remark varchar(500),
  student_is_deleted boolean
);

CREATE TABLE `students_courses` (
  course_id int NOT NULL AUTO_INCREMENT,
  student_id varchar(36) NOT NULL,
  course_name varchar(50) NOT NULL,
  course_start_at timestamp,
  course_end_at timestamp
);
