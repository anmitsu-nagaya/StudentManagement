package raisetech.student.management.controller.requestformat.registerdata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生登録内容")
@Getter
@Setter
public class RegisterStudentData {

  @NotBlank
  @Size(max = 100)
  private String studentFullName;
  @NotBlank
  @Size(max = 100)
  private String studentFurigana;
  @Size(max = 50)
  private String studentNickname;
  @NotBlank
  @Email
  @Size(max = 254)
  private String email;
  @Size(max = 10)
  private String prefecture;
  @Size(max = 50)
  private String city;
  @Min(1)
  private Integer age;
  @Size(max = 20)
  private String gender;
  @Size(max = 500)
  private String studentRemark;

}
