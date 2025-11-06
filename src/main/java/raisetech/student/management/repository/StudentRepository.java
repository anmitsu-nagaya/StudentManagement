package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.dto.StudentCourseDto;

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
  List<Student> searchStudentList();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生のコース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生のコース申し込み状況の全件検索を行います。
   *
   * @return　コース申し込み状況(全件)
   */
  List<StudentCourseStatus> searchStudentCourseStatusList();

  /**
   * コースIDに紐づくコース申し込み状況を検索します。
   *
   * @param courseId コースID
   * @return コースIDに紐づくコース申し込み状況
   */
  StudentCourseStatus searchStudentCourseStatus(Integer courseId);

  /**
   * 受講生を新規登録します。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行います。
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * コース申し込み状況を新規登録します。IDに関しては自動採番を行います。
   *
   * @param studentCourseStatus 受講生コース情報
   */
  void registerStudentCourseStatus(StudentCourseStatus studentCourseStatus);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourseDto 受講生ID,受講生コースID,受講生コース名
   */
  void updateStudentCourse(StudentCourseDto studentCourseDto);

}
