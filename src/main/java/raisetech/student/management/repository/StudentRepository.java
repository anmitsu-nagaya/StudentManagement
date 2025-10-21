package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudentList();

  @Select("SELECT * FROM students WHERE student_is_deleted = false")
  List<Student> searchNotDeletedStudentList();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();


  @Insert("INSERT INTO students(id,student_full_name,student_furigana,student_nickname,email,prefecture, city,age,gender,student_remark,student_is_deleted) VALUES(#{id}, #{studentFullName}, #{studentFurigana}, #{studentNickname}, #{email}, #{prefecture}, #{city}, #{age}, #{gender}, #{studentRemark}, false)")
  void registerStudent(
      String id,
      String studentFullName,
      String studentFurigana,
      String studentNickname,
      String email,
      String prefecture,
      String city,
      int age,
      String gender,
      String studentRemark);

  @Insert("INSERT INTO students_courses(course_id,student_id,course_name,course_start_at,course_end_at) VALUES (#{courseId}, #{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentCourses(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudent(String id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> findStudentCoursesList(String studentId);

  @Update("""
          UPDATE students
          SET
              student_full_name = #{studentFullName},
              student_furigana = #{studentFurigana},
              student_nickname = #{studentNickname},
              email = #{email},
              prefecture = #{prefecture},
              city = #{city},
              age = #{age},
              gender = #{gender},
              student_remark = #{studentRemark},
              student_is_deleted = #{studentIsDeleted}
          WHERE id = #{id}
      """)
  void updateStudent(Student student);

  @Update("""
          UPDATE students_courses
          SET
              course_name = #{courseName}
          WHERE student_id = #{studentId} AND course_id = #{courseId}
      """)
  void updateStudentCourses(StudentsCourses studentsCourses);


}
