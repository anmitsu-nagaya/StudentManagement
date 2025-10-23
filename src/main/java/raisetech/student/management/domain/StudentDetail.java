package raisetech.student.management.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生と受講生コース情報を扱うオブジェクト。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCoursesList;

}
