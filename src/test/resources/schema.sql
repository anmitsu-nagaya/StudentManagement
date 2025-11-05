CREATE TABLE students (
  id char(36) PRIMARY KEY,
  student_full_name varchar(100) NOT NULL,
  student_furigana varchar(100) NOT NULL,
  student_nickname varchar(50) DEFAULT NULL,
  email varchar(254) NOT NULL UNIQUE,
  prefecture varchar(10),
  city varchar(50),
  age int,
  gender varchar(20),
  student_remark varchar(500),
  student_is_deleted boolean not null default false
);

CREATE TABLE students_courses (
  course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  student_id CHAR(36) NOT NULL,
  course_name VARCHAR(50) NOT NULL,
  course_start_at TIMESTAMP,
  course_end_at TIMESTAMP
);

CREATE TABLE students_courses_status (
  status_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  course_id INT NOT NULL UNIQUE,
  status ENUM('仮申込', '本申込', '受講中', '受講修了') NOT NULL DEFAULT '仮申込'
);
