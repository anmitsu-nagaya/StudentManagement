package raisetech.student.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import raisetech.student.management.converter.StudentCourseMapper;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.StudentCourseDto;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private DataDomainConverter converter;

  @Mock
  private StudentCourseMapper mapper;

  @Captor
  ArgumentCaptor<StudentCourseStatus> statusCaptor;

  private StudentService sut;
  private Student student;
  private StudentCourse studentCourse;
  private String id;
  private StudentDetail studentDetail;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter, mapper);
    student = new Student();
    studentCourse = new StudentCourse();
    studentDetail = new StudentDetail();
    id = "test-id";
  }


  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターとマッパーの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudentList()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(mapper.statusMapping(studentCourseList)).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).searchStudentList();
    verify(repository, times(1)).searchStudentCourseList();
    //verify(converter, times(1)).toStudentDetail(studentList, studentCourseList);
    verify(mapper, times(1)).statusMapping(studentCourseList);
  }


  @Test
  void 受講生詳細の検索_リポジトリとマッパーの処理が適切に呼び出せていること() {
    student.setId(id);
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(new ArrayList<>());
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(mapper.statusMapping(studentCourseList)).thenReturn(studentCourseList);

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());
    StudentDetail actual = sut.findStudentDetailById(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);
    verify(mapper, times(1)).statusMapping(studentCourseList);
    assertThat(actual.getStudent().getId()).isEqualTo(expected.getStudent().getId());
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    studentDetail.setStudent(student);
    //studentDetail.setCourseList(studentCourseList);

    sut.registerStudentDetailList(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
    verify(repository, times(1)).registerStudentCourseStatus(any(StudentCourseStatus.class));
  }

  @Test
  void 受講生詳細の登録_コース申し込み状況に正しくコースIDが登録されていること() {
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    studentDetail.setStudent(student);
    //studentDetail.setCourseList(studentCourseList);

    sut.registerStudentDetailList(studentDetail);

    verify(repository, times(1)).registerStudentCourseStatus(statusCaptor.capture());

    StudentCourseStatus captured = statusCaptor.getValue();
    //assertThat(captured.getCourseId()).isEqualTo(studentCourse.getCourseId());
  }

  @Test
  void 受講生詳細の登録_初期情報の設定が適切に動作していること() {

    sut.initStudentCourses(studentCourse, id);

    assertEquals(id, studentCourse.getStudentId());
    //assertNotNull(studentCourse.getCourseStartAt());
    //assertNotNull(studentCourse.getCourseEndAt());
    //assertTrue(studentCourse.getCourseEndAt().isAfter(studentCourse.getCourseStartAt()));
    //assertEquals(LocalDateTime.now().getHour(), studentCourse.getCourseStartAt().getHour());
    //assertEquals(LocalDateTime.now().plusDays(300).getHour(),
    //    studentCourse.getCourseEndAt().getHour());

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
