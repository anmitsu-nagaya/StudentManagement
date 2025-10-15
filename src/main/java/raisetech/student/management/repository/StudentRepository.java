package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.domain.Student;
import raisetech.student.management.domain.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchAllStudent();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchAllStudentsCourses();

}
