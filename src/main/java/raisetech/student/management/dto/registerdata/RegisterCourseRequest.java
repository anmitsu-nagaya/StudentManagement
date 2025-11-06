package raisetech.student.management.dto.registerdata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース登録内容")
@Getter
@Setter
public class RegisterCourseRequest {

  /**
   * コース名。
   */
  @NotBlank
  @Size(max = 50)
  private String courseName;

}
