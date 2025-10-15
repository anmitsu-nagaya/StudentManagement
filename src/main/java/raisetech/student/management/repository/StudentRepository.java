package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
   *受講生DBを検索します。
   *
   * @return 受講生DBを検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  /**
   * 受講生コースDBを検索します。
   *
   * @return 受講生コースDBを検索したコースの一覧
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();


}
