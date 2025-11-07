package raisetech.student.management.dto.updatedata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.enums.CourseStatus;

@Schema(description = "受講生コース更新内容")
@Getter
@Setter
public class UpdateStatusRequest {

  /**
   * 申し込み状況ID。
   */
  @NotNull(message = "入力は必須です。")
  private int id;
  /**
   * コースID。
   */
  @NotNull(message = "入力は必須です。")
  private int courseId;
  /**
   * 申し込み状況。
   */
  @NotNull(message = "入力は必須です。")
  private CourseStatus status;

}
