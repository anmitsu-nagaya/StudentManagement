package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクト。
 */
@Getter
@Setter
public class Student {

  /**
   * 学生ID（主キー）。
   */
  private String id;
  /**
   * 学生のフルネーム。
   */
  @NotBlank
  @Size(max = 100)
  private String studentFullName;
  /**
   * 学生のフリガナ（カタカナ）。
   */
  @NotBlank
  @Size(max = 100)
  private String studentFurigana;
  /**
   * 学生のニックネーム。
   */
  @Size(max = 50)
  private String studentNickname;
  /**
   * メールアドレス。
   */
  @NotBlank
  @Email
  @Size(max = 254)
  private String email;
  /**
   * 地域（都道府県）。
   */
  @Size(max = 10)
  private String prefecture;
  /**
   * 地域（市区町村）。
   */
  @Size(max = 10)
  private String city;
  /**
   * 年齢。
   */
  @Min(1)
  private Integer age;
  /**
   * 性別。
   */
  @Size(max = 20)
  private String gender;
  /**
   * 備考欄。
   */
  @Size(max = 500)
  private String studentRemark;
  /**
   * 論理削除フラグ。
   */
  private Boolean studentIsDeleted;
}
