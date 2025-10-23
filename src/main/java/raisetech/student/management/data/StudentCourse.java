package raisetech.student.management.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  /**
   * コースID。PRIMARY KEY (`student_id`,`course_id`)。
   * <ul>
   *   <li>DB カラム: course_id</li>
   *   <li>型: UUID を文字列で格納（例: "1a2b3c4d-0001"）</li>
   *   <li>最大長: 36文字</li>
   *   <li>NOT NULL</li>
   * </ul>
   */
  private String courseId;
  /**
   * 受講生ID。PRIMARY KEY (`student_id`,`course_id`)
   * <ul>
   *   <li>DB カラム: student_id</li>
   *   <li>型: 文字列</li>
   *   <li>最大長: 36文字</li>
   *   <li>NOT NULL</li>
   * </ul>
   */
  private String studentId;
  /**
   * コース名。
   * <ul>
   *   <li>DB カラム: course_name</li>
   *   <li>型: 文字列</li>
   *   <li>最大長: 50文字</li>
   *   <li>NOT NULL</li>
   * </ul>
   */
  private String courseName;
  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
}
