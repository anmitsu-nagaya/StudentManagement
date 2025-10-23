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
 * 受講生テーブルと受講生コース情報テーブルと紐づくリポジトリです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return　受講生一覧(全件)
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudentList();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生のコース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> findStudentCoursesList(String studentId);


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
