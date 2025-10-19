package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;


/**
 * 受講生情報を扱うリポジトリ。
 * <p>
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */

@Mapper
public interface StudentRepository {

  /**
   * 受講生DBを全件検索します。
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

  /**
   * 受講生コースDBに受講生コースデータを新規登録します。
   *
   * @return void
   */
  @Insert("INSERT INTO students_courses(course_id,student_id,course_name,course_start_at,course_end_at) VALUES (#{courseId}, #{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  //自動生成された項目を使うことを指定
  void registerStudentCourses(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentDetail(String id);

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
              student_is_deleted = false
          WHERE id = #{id}
      """)
  void updateStudent(Student student);

  @Update("""
          UPDATE students_courses
          SET
              course_id = #{courseId},
              student_id = #{studentId},
              course_name = #{courseName},
              course_start_at = #{courseStartAt},
              course_end_at = #{courseEndAt}
          WHERE student_id = #{studentId}
      """)
  void updateStudentCourses(StudentsCourses studentsCourses);


}
