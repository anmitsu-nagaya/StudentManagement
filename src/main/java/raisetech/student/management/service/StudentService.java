package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;
/**
 * 学生情報に関するビジネスロジックを提供するサービスクラス。
 *
 * <p>StudentRepositoryを利用して、学生データの検索・追加・更新などを行います。
 * Controller層から呼び出され、DB操作の前後で必要な処理や変換を行う役割を持ちます。</p>
 */
@Service
public class StudentService {

  private StudentRepository repository;
  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  /**
   * StudentRepositoryで全件検索した結果を返します。
   *
   * @return 検索されたすべての学生情報を格納したリスト
   */
  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

  /**
   * StudentRepositoryで全件検索した結果を返します。
   *
   * @return 検索されたすべての受講コースを格納したリスト
   */
  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  /**
   * 新規の受講生情報を登録します。
   * <p>StudentRepositoryを使用して、受講生DBに新しい学生レコードを追加します。</p>
   */
  public void addStudentDetailList(String id, String name, String furigana,String email,String courseId, String courseName){
    repository.addStudent(id, name,furigana,email);
    repository.addStudentCourses(courseId,id,courseName);
  }
}
