package raisetech.student.management.data;

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
  private String studentFullName;
  /**
   * 学生のフリガナ（カタカナ）。
   */
  private String studentFurigana;
  private String studentNickname;
  /**
   * メールアドレス。
   */
  private String email;
  /**
   * 地域（都道府県）。
   */
  private String prefecture;
  /**
   * 地域（市区町村）。
   */
  private String city;
  /**
   * 年齢。
   */
  private int age;
  /**
   * 性別。
   */
  private String gender;
  /**
   * 備考欄。
   */
  private String studentRemark;
  /**
   * 論理削除フラグ。
   */
  private Boolean studentIsDeleted;
}
