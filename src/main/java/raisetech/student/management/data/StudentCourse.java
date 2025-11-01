package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生コース情報を扱うオブジェクト。
 */
@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  /**
   * コースID。PRIMARY KEY (`student_id`,`course_id`)。
   */
  @Pattern(regexp = "^[0-9]+$", message = "数字のみで入力してください")
  private String courseId;
  /**
   * 受講生ID。PRIMARY KEY (`student_id`,`course_id`)
   */
  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
      message = "UUIDの形式が正しくありません。")
  private String studentId;
  /**
   * コース名。
   */
  @Size(max = 50)
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
