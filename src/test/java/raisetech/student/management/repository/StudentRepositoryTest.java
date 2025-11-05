package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.enums.CourseStatus;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudentList();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の単一検索が行えること() {
    Student actual = sut.searchStudent("550e8400-e29b-41d4-a716-446655440001");
    assertThat(actual.getStudentFullName()).isEqualTo("山田太郎");
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生コース情報の単一検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourse("550e8400-e29b-41d4-a716-446655440001");
    assertThat(actual.size()).isEqualTo(2);
    assertThat(actual.getFirst().getCourseName()).isEqualTo("Javaコース");
  }

  @Test
  void 受講生コース申し込み状況の全件検索が行えること() {
    List<StudentCourseStatus> actual = sut.searchStudentCourseStatusList();
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生コース申し込み状況の単一検索が行えること() {
    StudentCourseStatus actual = sut.searchStudentCourseStatus(1);
    assertThat(actual.getStatus()).isEqualTo(CourseStatus.受講修了);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setId(UUID.randomUUID().toString());
    student.setStudentFullName("井上花子");
    student.setStudentFurigana("イノウエハナコ");
    student.setStudentNickname("はな");
    student.setEmail("hana@example.com");
    student.setPrefecture("東京都");
    student.setCity("渋谷区");
    student.setAge(20);
    student.setGender("女性");
    student.setStudentRemark("");
    student.setStudentIsDeleted(false);
    sut.registerStudent(student);

    List<Student> actual = sut.searchStudentList();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId(11);
    studentCourse.setStudentId(UUID.randomUUID().toString());
    studentCourse.setCourseName("Javaコース");
    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void コース申し込み状況の登録が行えること() {
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatus.setStatusId(11);
    studentCourseStatus.setCourseId(11);
    studentCourseStatus.setStatus(CourseStatus.仮申込);

    sut.registerStudentCourseStatus(studentCourseStatus);

    List<StudentCourseStatus> actual = sut.searchStudentCourseStatusList();

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = new Student();
    student.setId("1a2b3c4d-0001-0000-0000-000000000001");
    student.setStudentFullName("山田太郎");
    student.setStudentFurigana("ヤマダタロウ");
    student.setStudentNickname("たろう");
    student.setEmail("taro.yamada@example.com");
    student.setPrefecture("東京都");
    student.setCity("渋谷区");
    student.setAge(26);
    student.setGender("男性");
    student.setStudentRemark("Java勉強中");
    student.setStudentIsDeleted(false);

    sut.updateStudent(student);

    Student actual = sut.searchStudent("1a2b3c4d-0001-0000-0000-000000000001");

    assertThat(actual.getAge()).isEqualTo(26);
    assertThat(actual.getCity()).isEqualTo("渋谷区");
  }

  @Test
  void 受講生コース情報の更新が行えること() {

    List<StudentCourse> update = sut.searchStudentCourse("1a2b3c4d-0001-0000-0000-000000000001");

    StudentCourseDto studentCourseDto = new StudentCourseDto();
    studentCourseDto.setStudentId("1a2b3c4d-0001-0000-0000-000000000001");
    studentCourseDto.setCourseId(1);
    studentCourseDto.setCourseName("Javaコース");
    sut.updateStudentCourse(studentCourseDto);

    List<StudentCourse> actual = sut.searchStudentCourse("1a2b3c4d-0001-0000-0000-000000000001");

    assertThat(actual.getFirst().getCourseName()).isEqualTo("Javaコース");
  }

}
