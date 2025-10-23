package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  /**
   * コンストラクタ
   *
   * @param repository 受講生テーブルと受講生コース情報テーブルと紐づくリポジトリ
   * @param converter  受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーター
   */
  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudentList();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索です。 IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Transactional
  public StudentDetail findStudentDetailById(String id) {
    Student student = repository.findStudent(id);
    List<StudentsCourses> studentsCoursesList = repository.findStudentCoursesList(student.getId());
    return new StudentDetail(student, studentsCoursesList);
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
