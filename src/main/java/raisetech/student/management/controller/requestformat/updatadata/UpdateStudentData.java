package raisetech.student.management.controller.requestformat.updatadata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生更新内容")
@Getter
@Setter
public class UpdateStudentData {

  @NotBlank
  @Size(min = 36, max = 36)
  private String id;
  private String studentFullName;
  private String studentFurigana;
  private String studentNickname;
  private String email;
  private String prefecture;
  private String city;
  private Integer age;
  private String gender;
  private String studentRemark;
  private Boolean studentIsDeleted;

}
