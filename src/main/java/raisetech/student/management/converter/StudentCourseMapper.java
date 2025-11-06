package raisetech.student.management.converter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生コース情報に申し込み状況をマッピングするマッパーです。
 */
@Component
public class StudentCourseMapper {

  private StudentRepository repository;

  /**
   * コンストラクタ
   *
   * @param repository 受講生テーブルと受講生コース情報テーブルと紐づくリポジトリ
   */
  @Autowired
  public StudentCourseMapper(StudentRepository repository) {
    this.repository = repository;
  }

  /**
   * 受講生コース情報に申し込み状況をマッピングします。
   *
   * @param studentCourses 受講生コース情報のリスト（このメソッド内で更新されます）
   * @return コース更新情報がマッピングされた受講生コース情報
   */
  public List<StudentCourse> statusMapping(List<StudentCourse> studentCourses) {
    //for (StudentCourse studentCourse : studentCourses) {
    //StudentCourseStatus studentCourseStatus = repository.searchStudentCourseStatus(
    //studentCourse.getCourseId());
    //studentCourse.setStatus(studentCourseStatus.getStatus());
    //}
    //return studentCourses
    return null;
  }

}
