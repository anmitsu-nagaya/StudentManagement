package raisetech.student.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.converter.DataDomainConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.enums.CourseStatus;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.StudentCourseDto;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private DataDomainConverter dataDomainConverter;

  @Captor
  ArgumentCaptor<StudentCourse> courseCaptor;

  @Captor
  ArgumentCaptor<StudentCourseStatus> statusCaptor;

  private StudentService sut;
  private Student student;
  private StudentCourse studentCourse;
  private StudentCourseStatus studentCourseStatus;
  private String id;
  private StudentDetail studentDetail;
  private CourseDetail courseDetail;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, dataDomainConverter);
    student = new Student();
    studentCourse = new StudentCourse();
    studentCourseStatus = new StudentCourseStatus();
    studentDetail = new StudentDetail();
    courseDetail = new CourseDetail();
    id = "test-id";
  }


  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーター処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> courseList = new ArrayList<>();
    List<StudentCourseStatus> statusList = new ArrayList<>();
    List<CourseDetail> courseDetails = new ArrayList<>();
    List<StudentDetail> studentDetails = new ArrayList<>();
    when(repository.searchStudentList()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(courseList);
    when(repository.searchStudentCourseStatusList()).thenReturn(statusList);
    when(dataDomainConverter.toCourseWithStatus(courseList, statusList)).thenReturn(courseDetails);
    when(dataDomainConverter.toStudentDetail(studentList, courseDetails)).thenReturn(
        studentDetails);

    sut.searchStudentList();

    verify(repository, times(1)).searchStudentList();
    verify(repository, times(1)).searchStudentCourseList();
    verify(repository, times(1)).searchStudentCourseStatusList();
    verify(dataDomainConverter, times(1)).toCourseWithStatus(courseList, statusList);
    verify(dataDomainConverter, times(1)).toStudentDetail(studentList, courseDetails);

  }


  @Test
  void 受講生詳細の検索_リポジトリとマッパーの処理が適切に呼び出せていること() {
    student.setId(id);
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(new ArrayList<>());
    List<StudentCourse> courseList = new ArrayList<>();
    List<StudentCourseStatus> statusList = new ArrayList<>();
    List<CourseDetail> courseDetails = new ArrayList<>();
    when(repository.searchStudentCourseStatusList()).thenReturn(new ArrayList<>());
    when(dataDomainConverter.toCourseWithStatus(courseList, statusList)).thenReturn(courseDetails);

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());
    StudentDetail actual = sut.findStudentDetailById(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);
    verify(repository, times(1)).searchStudentCourseStatusList();
    verify(dataDomainConverter, times(1)).toCourseWithStatus(courseList, statusList);
    assertThat(actual.getStudent().getId()).isEqualTo(expected.getStudent().getId());
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    extracted();
    sut.registerStudentDetailList(studentDetail);

    verify(repository, times(1)).registerStudent(any(Student.class));
    verify(repository, times(1)).registerStudentCourse(any(StudentCourse.class));
    verify(repository, times(1)).registerStudentCourseStatus(any(StudentCourseStatus.class));
  }


  @Test
  void 受講生詳細の登録_受講生IDに正しいIDが設定されること() {
    extracted();
    sut.registerStudentDetailList(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);

    assertThat(studentDetail.getStudent().getId()).isNotNull();
    assertThat(studentDetail.getStudent().getId()).matches(
        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    assertThat(studentDetail.getCourseList().getFirst().getCourse().getStudentId()).isNotNull();
    assertThat(studentDetail.getCourseList().getFirst().getCourse().getStudentId()).matches(
        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

  }

  @Test
  void 受講生詳細の登録_コース詳細のデータマッピングが正しいこと() {
    extracted();
    sut.registerStudentDetailList(studentDetail);

    verify(repository, times(1)).registerStudentCourse(courseCaptor.capture());
    verify(repository, times(1)).registerStudentCourseStatus(statusCaptor.capture());

    StudentCourse courseCaptured = courseCaptor.getValue();
    assertThat(courseCaptured.getStudentId()).isEqualTo(studentDetail.getStudent().getId());
    assertThat(courseCaptured.getCourseName()).isEqualTo(courseDetail.getCourse().getCourseName());
    StudentCourseStatus statusCaptured = statusCaptor.getValue();
    assertThat(statusCaptured.getCourseId()).isEqualTo(studentCourse.getId());
    assertThat(statusCaptured.getStatus()).isEqualTo(CourseStatus.仮申込);
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime actual = statusCaptured.getTemporaryAppliedAt();
    assertThat(Duration.between(actual, now).abs().getSeconds()).isLessThan(3);
  }

  private void extracted() {
    studentDetail.setStudent(student);
    courseDetail.setCourse(studentCourse);
    courseDetail.setStatus(studentCourseStatus);
    List<CourseDetail> courseDetails = List.of(courseDetail);
    studentDetail.setCourseList(courseDetails);
  }

  @Test
  void 受講生詳細の更新_リポジトリが適切に呼び出せていること() {

    //List<StudentCourse> studentCourseList = new ArrayList<>();
    //studentCourseList.add(studentCourse);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    studentDetail.setStudent(student);
    //studentDetail.setCourseList(studentCourseList);

    sut.updateStudentDetailList(studentDetail);

    verify(repository, times(1)).updateStudent(studentDetail.getStudent());
    verify(repository, times(1)).updateStudentCourse(any(StudentCourseDto.class));

  }

}
