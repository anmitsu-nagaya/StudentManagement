package raisetech.student.management.service.mapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.converter.StudentCourseMapper;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.enums.CourseStatus;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentCourseMapperTest {

  @Mock
  private StudentRepository repository;

  private StudentCourseMapper sut;

  @Test
  void 受講生詳細の検索_受講生コース情報に申し込み状況が正しくマッピングされていること() {
    StudentCourse course1 = new StudentCourse();
    //course1.setCourseId(1);
    //course1.setCourseName("Javaコース");
    StudentCourse course2 = new StudentCourse();
    //course2.setCourseId(2);
    course2.setCourseName("AWSコース");
    List<StudentCourse> studentCourseList = List.of(course1, course2);

    StudentCourseStatus studentCourseStatus1 = new StudentCourseStatus();
    studentCourseStatus1.setStatus(CourseStatus.受講修了);
    StudentCourseStatus studentCourseStatus2 = new StudentCourseStatus();
    studentCourseStatus2.setStatus(CourseStatus.受講中);

    when(repository.searchStudentCourseStatus(1)).thenReturn(studentCourseStatus1);
    when(repository.searchStudentCourseStatus(2)).thenReturn(studentCourseStatus2);

    sut = new StudentCourseMapper(repository);
    List<StudentCourse> actual = sut.statusMapping(studentCourseList);

    verify(repository, times(1)).searchStudentCourseStatus(1);
    verify(repository, times(1)).searchStudentCourseStatus(2);

    assertThat(actual).hasSize(2);
    assertThat(actual.get(0).getCourseName()).isEqualTo("Javaコース");
    //assertThat(actual.get(0).getStatus()).isEqualTo(CourseStatus.受講修了);
    //assertThat(actual.get(1).getStatus()).isEqualTo(CourseStatus.受講中);

  }
}
