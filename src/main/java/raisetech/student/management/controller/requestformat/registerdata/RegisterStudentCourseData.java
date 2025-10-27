package raisetech.student.management.controller.requestformat.registerdata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース登録内容")
@Getter
@Setter
public class RegisterStudentCourseData {

  @NotBlank
  private String courseName;

}
