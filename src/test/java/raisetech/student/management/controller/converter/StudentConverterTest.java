package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  @Test
  void レポジトリ層から得た受講生とその受講生のコースリストがStudentDetail型に正しく変換されること() {

    //事前準備
    Student student = new Student();
    String id = UUID.randomUUID().toString();
    student.setId(id);
    List<Student> studentList = List.of(student);

    StudentCourse studentCourse1 = new StudentCourse();
    StudentCourse studentCourse2 = new StudentCourse();
    studentCourse1.setStudentId(id);
    studentCourse2.setStudentId(id);
    List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2);

    //実行
    StudentConverter sut = new StudentConverter();
    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    //検証
    assertThat(actual).hasSize(1);
    assertThat(actual.getFirst().getStudentCoursesList()).hasSize(2);
    assertThat(actual.getFirst().getStudent()).isEqualTo(student);
    assertThat(actual.getFirst().getStudentCoursesList().getFirst()).isEqualTo(studentCourse1);

    //後処理
    //ここでDBを元に戻す。
  }
}
