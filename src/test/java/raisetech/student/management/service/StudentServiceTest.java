package raisetech.student.management.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentCourseDto;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;
  private Student student;
  private StudentCourse studentCourse;
  private String id;
  private StudentDetail studentDetail;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
    student = new Student();
    studentCourse = new StudentCourse();
    studentDetail = new StudentDetail();
    id = UUID.randomUUID().toString();
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudentList()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).searchStudentList();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    student.setId(id);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

    sut.findStudentDetailById(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(student.getId());
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    student.setId(id);
    studentCourse.setStudentId(id);
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusDays(300));

    List<StudentCourse> studentCourseList = new ArrayList<>();

    studentDetail.setStudent(student);
    studentCourseList.add(studentCourse);
    studentDetail.setStudentCoursesList(studentCourseList);

    sut.registerStudentDetailList(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);

  }

  @Test
  void 受講生詳細の登録_初期情報の設定が適切に動作していること() {

    sut.initStudentCourses(studentCourse, id);

    Assertions.assertEquals(id, studentCourse.getStudentId());
    Assertions.assertNotNull(studentCourse.getCourseStartAt());
    Assertions.assertNotNull(studentCourse.getCourseEndAt());
    Assertions.assertTrue(studentCourse.getCourseEndAt().isAfter(studentCourse.getCourseStartAt()));

  }

  @Test
  void 受講生詳細の更新_リポジトリが適切に呼び出せていること() {

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);
    studentDetail.setStudent(student);
    studentDetail.setStudentCoursesList(studentCourseList);

    sut.updateStudentDetailList(studentDetail);

    verify(repository, times(1)).updateStudent(studentDetail.getStudent());
    verify(repository, times(1)).updateStudentCourse(any(StudentCourseDto.class));

  }

}
