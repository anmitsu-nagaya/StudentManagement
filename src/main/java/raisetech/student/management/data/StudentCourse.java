package raisetech.student.management.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生コース情報を扱うオブジェクト。
 */
@Getter
@Setter
public class StudentCourse {

  /**
   * コースID。PRIMARY KEY (`student_id`,`course_id`)。
   */
  private String courseId;
  /**
   * 受講生ID。PRIMARY KEY (`student_id`,`course_id`)
   */
  private String studentId;
  /**
   * コース名。
   */
  private String courseName;
  /**
   * 受講開始日。
   */
  private LocalDateTime courseStartAt;
  /**
   * 受講修了日。
   */
  private LocalDateTime courseEndAt;
}
