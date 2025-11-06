package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.converter.DataDomainConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.enums.CourseStatus;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.StudentCourseDto;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private DataDomainConverter converter;

  /**
   * コンストラクタ
   *
   * @param repository          受講生テーブルと受講生コース情報テーブルと紐づくリポジトリ
   * @param converter 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーター
   */
  @Autowired
  public StudentService(StudentRepository repository, DataDomainConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudentList();
    List<StudentCourse> courseList = repository.searchStudentCourseList();
    List<StudentCourseStatus> statusList = repository.searchStudentCourseStatusList();
    List<CourseDetail> courseDetails = converter.toCourseWithStatus(courseList,
        statusList);
    return converter.toStudentDetail(studentList, courseDetails);
  }

  /**
   * 受講生詳細検索です。 IDに紐づく受講生情報を取得した後、その受講生に紐づくコース詳細を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Transactional
  public StudentDetail findStudentDetailById(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> courseList = repository.searchStudentCourse(student.getId());
    List<StudentCourseStatus> statusList = repository.searchStudentCourseStatusList();
    List<CourseDetail> courseDetails = converter.toCourseWithStatus(courseList,
        statusList);
    return new StudentDetail(student, courseDetails);
  }


  /**
   * 受講生詳細の登録を行います。 受講生とコース詳細を個別に登録し、コース詳細には受講生情報を紐づける値や日付情報・申し込み状況の初期値を設定します。
   * 受講生IDに対してUUIDの作成を行います。
   *
   * @param studentDetail リクエストされた登録内容を所持する受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public void registerStudentDetailList(StudentDetail studentDetail) {
    String id = UUID.randomUUID().toString();

    Student student = studentDetail.getStudent();
    student.setId(id);
    repository.registerStudent(student);

    studentDetail.getCourseList().forEach(courseDetail -> {
      StudentCourse course = courseDetail.getCourse();
      course.setStudentId(id);
      repository.registerStudentCourse(course);

      StudentCourseStatus status = new StudentCourseStatus();
      status.setCourseId(course.getId());
      status.setStatus(CourseStatus.仮申込);
      status.setTemporaryAppliedAt(LocalDateTime.now());
      repository.registerStudentCourseStatus(status);
    });
  }


  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 更新内容を所持する受講生詳細
   */
  @Transactional
  public void updateStudentDetailList(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    //for (StudentCourse studentCourse : studentDetail.getCourseList()) {
    //  studentCourse.setStudentId(studentDetail.getStudent().getId());
    //  StudentCourseDto studentCourseDto = getStudentCourseDto(studentCourse);
    //  repository.updateStudentCourse(studentCourseDto);
    //}
  }

  /**
   * 受講生コース情報のコース名を更新する際に必要な引数をまとめて取得します。
   *
   * @param studentCourse 更新内容を所持する受講生コース情報
   * @return　受講生ID,受講生コースID,受講生コース名を付与した受講生コース情報
   */
  private StudentCourseDto getStudentCourseDto(StudentCourse studentCourse) {
    StudentCourseDto studentCourseDto = new StudentCourseDto();
    studentCourseDto.setStudentId(studentCourse.getStudentId());
    studentCourseDto.setCourseId(studentCourse.getId());
    studentCourseDto.setCourseName(studentCourse.getCourseName());
    return studentCourseDto;
  }
}
