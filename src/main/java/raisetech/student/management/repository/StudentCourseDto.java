package raisetech.student.management.repository;

import lombok.Getter;
import lombok.Setter;

/**
 * 受講生コース情報のうち、特定の引数を持つデータ転送オブジェクトです。
 */
@Getter
@Setter
public class StudentCourseDto {

  private String studentId;
  private int courseId;
  private String courseName;
}
