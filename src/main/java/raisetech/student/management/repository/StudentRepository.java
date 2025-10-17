package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;


/**
 * 受講生情報を扱うリポジトリ。
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */

@Mapper
public interface StudentRepository {

  /**
   *受講生DBを全件検索します。
   *
   * @return 受講生DBを全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  /**
   * 受講生コースDBを全件検索します。
   *
   * @return 受講生コースDBを全件検索したコースの一覧
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  /**
   * 受講生DBに受講生データを新規登録します。
   *
   * @return void
   */
  @Insert("INSERT INTO students(id,student_full_name,student_furigana,email) VALUES (#{id},#{name},#{furigana},#{email})")
  void addStudent(
      @Param("id") String id,
      @Param("name") String name,
      @Param("furigana") String furigana,
      @Param("email") String email);

  /**
   * 受講生コースDBに受講生コースデータを新規登録します。
   *
   * @return void
   */
  @Insert("INSERT INTO students_courses(course_id,student_id,course_name) VALUES (#{courseId},#{studentID},#{courseName})")
  void addStudentCourses(
      @Param("courseId") String courseId,
      @Param("studentID") String studentID,
      @Param("courseName") String courseName);
}
