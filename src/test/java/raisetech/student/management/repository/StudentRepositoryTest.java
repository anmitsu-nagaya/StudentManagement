package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.enums.CourseStatus;

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
    assertThat(actual.getStatus()).isEqualTo(CourseStatus.仮申込);
    LocalDateTime expected = LocalDateTime.parse("2025-10-01 09:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    assertThat(actual.getTemporaryAppliedAt()).isEqualTo(expected);
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
    studentCourse.setId(11);
    studentCourse.setStudentId(UUID.randomUUID().toString());
    studentCourse.setCourseName("Javaコース");
    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void コース申し込み状況の登録が行えること() {
    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatus.setId(11);
    studentCourseStatus.setCourseId(11);
    studentCourseStatus.setStatus(CourseStatus.仮申込);
    studentCourseStatus.setTemporaryAppliedAt(LocalDateTime.parse("2025-10-01 09:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    studentCourseStatus.setOfficialAppliedAt(null);
    studentCourseStatus.setCourseStartedAt(null);
    studentCourseStatus.setCourseCompletedAt(null);
    sut.registerStudentCourseStatus(studentCourseStatus);

    List<StudentCourseStatus> actual = sut.searchStudentCourseStatusList();

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = new Student();
    student.setId("550e8400-e29b-41d4-a716-446655440001");
    student.setStudentFullName("山田太郎");
    student.setStudentFurigana("ヤマダタロウ");
    student.setStudentNickname("たろちゃん");
    student.setEmail("yamada.taro@example.com");
    student.setPrefecture("東京都");
    student.setCity("新宿区");
    student.setAge(26);
    student.setGender("男性");
    student.setStudentRemark("積極的に質問する学生");
    student.setStudentIsDeleted(false);

    sut.updateStudent(student);

    Student actual = sut.searchStudent("550e8400-e29b-41d4-a716-446655440001");

    assertThat(actual.getAge()).isEqualTo(26);
    assertThat(actual.getCity()).isEqualTo("新宿区");
  }

  @Test
  void 受講生コース情報の更新が行えること() {

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId("550e8400-e29b-41d4-a716-446655440001");
    studentCourse.setCourseName("AWSコース");
    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse("550e8400-e29b-41d4-a716-446655440001");

    assertThat(actual.getFirst().getCourseName()).isEqualTo("AWSコース");
  }

  @Test
  void コース申し込み状況の更新が行えること() {

    StudentCourseStatus studentCourseStatus = new StudentCourseStatus();
    studentCourseStatus.setId(1);
    studentCourseStatus.setCourseId(1);
    studentCourseStatus.setStatus(CourseStatus.本申込);
    studentCourseStatus.setTemporaryAppliedAt(LocalDateTime.parse("2025-10-01 09:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    studentCourseStatus.setOfficialAppliedAt(LocalDateTime.parse("2025-10-05 09:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    studentCourseStatus.setCourseStartedAt(null);
    studentCourseStatus.setCourseCompletedAt(null);
    sut.updateStudentCourseStatus(studentCourseStatus);

    StudentCourseStatus actual = sut.searchStudentCourseStatus(1);

    assertThat(actual.getStatus()).isEqualTo(CourseStatus.本申込);
    assertThat(actual.getOfficialAppliedAt()).isEqualTo(LocalDateTime.parse("2025-10-05 09:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }
}
