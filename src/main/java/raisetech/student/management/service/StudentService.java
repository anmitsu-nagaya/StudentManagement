package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

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
    return repository.searchStudentList();
  }

  /**
   * StudentRepositoryで全件検索し、論理削除がfalseである結果を返します。
   *
   * @return 論理削除対象外の学生情報を格納したリスト。List型。
   */
  public List<Student> searchNotDeletedStudentList() {
    return repository.searchNotDeletedStudentList();
  }

  /**
   * StudentRepositoryで全件検索した結果を返します。
   *
   * @return 検索されたすべての受講コースを格納したリスト。List型。
   */
  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCoursesList();
  }

  /**
   * 新規の受講生情報を登録します。
   * <p>StudentRepositoryを使用して、受講生DBに新しい学生レコードを追加します。</p>
   */
  @Transactional
  public StudentDetail registerStudentDetailList(StudentDetail studentDetail) {
    String id = UUID.randomUUID().toString();
    studentDetail.getStudent().setId(id);
    String studentFullName = studentDetail.getStudent().getStudentFullName();
    String studentFurigana = studentDetail.getStudent().getStudentFurigana();
    String studentNickname = studentDetail.getStudent().getStudentNickname();
    String email = studentDetail.getStudent().getEmail();
    String prefecture = studentDetail.getStudent().getPrefecture();
    String city = studentDetail.getStudent().getCity();
    int age = studentDetail.getStudent().getAge();
    String gender = studentDetail.getStudent().getGender();
    String studentRemark = studentDetail.getStudent().getStudentRemark();

    repository.registerStudent(id, studentFullName, studentFurigana, studentNickname, email,
        prefecture, city, age, gender, studentRemark);

    List<StudentsCourses> studentsCourses = studentDetail.getStudentsCoursesList();
    for (StudentsCourses course : studentsCourses) {
      course.setStudentId(id);
      course.setCourseStartAt(LocalDateTime.now());
      course.setCourseEndAt(LocalDateTime.now().plusDays(300));
      repository.registerStudentCourses(course);
    }
    return studentDetail;
  }

  /**
   * ボタンで選択した受講生情報を検索した結果を取得します。
   *
   * @param id
   * @return ボタンで選んだ受講生情報のみ格納したデータ。StudentDetail型。
   */
  @Transactional
  public StudentDetail findStudentDetailById(String id) {
    Student student = repository.findStudent(id);
    List<StudentsCourses> studentsCoursesList = repository.findStudentCoursesList(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCoursesList(studentsCoursesList);
    return studentDetail;
  }

  /**
   * ボタンで選択した受講生情報を更新します。 キャンセルチェックボックスにより、論理削除フラグも更新されています。
   * <p>StudentRepositoryを使用して、ボタンで選択された受講生（一人）を対象に、受講生DB・受講生コースDBの学生レコードを更新します。</p>
   *
   * @param studentDetail
   */
  @Transactional
  public void updateStudentDetailList(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses courses : studentDetail.getStudentsCoursesList()) {
      courses.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourses(courses);
    }

  }
}
