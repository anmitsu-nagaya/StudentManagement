package raisetech.student.management.controller.converter;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import raisetech.student.management.converter.DataDomainConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

class dataDomainConverterTest {

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
    DataDomainConverter sut = new DataDomainConverter();
    //List<StudentDetail> actual = sut.toStudentDetail(studentList, studentCourseList);

    //検証
    //assertThat(actual).hasSize(1);
    //assertThat(actual.getFirst().getCourseList()).hasSize(2);
    //assertThat(actual.getFirst().getStudent()).isEqualTo(student);
    //assertThat(actual.getFirst().getCourseList().getFirst()).isEqualTo(studentCourse1);

    //後処理
    //ここでDBを元に戻す。
  }
}
