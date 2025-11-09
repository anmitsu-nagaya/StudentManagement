package raisetech.student.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.enums.CourseStatus;

/**
 * 受講生、コース、ステータス情報をまとめて受け取るリクエストDTO
 */
@Schema(description = "受講生・コース・ステータス一括検索用DTO")
@Getter
@Setter
public class StudentFilterRequestDto {

  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
      message = "UUIDの形式が正しくありません。")
  private String studentId;

  @Size(max = 100, message = "文字数が超過しています。")
  private String studentFullName;

  @Size(max = 100, message = "文字数が超過しています。")
  private String studentFurigana;

  @Size(max = 50, message = "文字数が超過しています。")
  private String studentNickname;

  @Email(message = "正しいメール形式で入力してください。")
  @Size(max = 254, message = "文字数が超過しています。")
  private String email;

  @Size(max = 10, message = "文字数が超過しています。")
  private String prefecture;

  @Size(max = 50, message = "文字数が超過しています。")
  private String city;

  @Min(value = 1, message = "値は1以上で入力してください。")
  private int age;

  @Size(max = 20, message = "文字数が超過しています。")
  private String gender;

  @Size(max = 500, message = "文字数が超過しています。")
  private String studentRemark;

  private Boolean studentIsDeleted;

  private int courseId;

  @Size(max = 50, message = "文字数が超過しています。")
  private String courseName;

  private int statusId;

  private CourseStatus status;

  private LocalDateTime temporaryAppliedAt;

  private LocalDateTime officialAppliedAt;

  private LocalDateTime courseStartedAt;

  private LocalDateTime courseCompletedAt;
}
