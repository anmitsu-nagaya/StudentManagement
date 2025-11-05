package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.enums.CourseStatus;

/**
 * 受講生コース申し込み状況を扱うオブジェクト。
 */
@Schema(description = "受講生コース申し込み状況")
@Getter
@Setter
public class StudentCourseStatus {

  /**
   * 申し込み状況ID。
   */
  private int statusId;
  /**
   * コースID。
   */
  private int courseId;

  /**
   * 申し込み状況。
   */
  @NotNull
  private CourseStatus status;

}
