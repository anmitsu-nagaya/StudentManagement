package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
  @Insert("INSERT INTO students VALUES(UUID(), #{studentFullName}, #{studentFurigana}, #{studentNickname}, #{email}, #{prefecture}, #{city}, #{age}, #{gender}, #{studentRemark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id") //自動生成された項目を使うことを指定
  void registerStudent(Student student);

  /**
   * 受講生コースDBに受講生コースデータを新規登録します。
   *
   * @return void
   */
  @Insert("INSERT students_courses VALUES (#{courseId}, #{studentID}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  void registerStudentCourses(StudentsCourses studentsCourses);

}
