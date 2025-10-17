package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

/**
 * 受講生DBに対応するエンティティクラスです。
 * 学生情報（名前、年齢など）を保持します。
 * <p>このクラスは DB の 1 行（1 生徒）を表します。各フィールドは対応するカラムを保持します。
 *  フィールド説明には、DB 上の制約（主キー・NOT NULL・長さなど）、想定される値の例、
 *  null 許容性、利用上の注意点を記載しています。</p>
 */
@Getter
@Setter
public class Student {
  /**
   * 学生ID（主キー）。
   * <ul>
   *   <li>DB カラム: id</li>
   *   <li>型: UUID を文字列で格納（例: "1a2b3c4d-0001"）</li>
   *   <li>NOT NULL</li>
   * </ul>
   */
  private String id;
  /**
   * 学生のフルネーム。
   * <ul>
   *   <li>DB カラム: student_full_name</li>
   *   <li>型: 文字列</li>
   *   <li>最大長: 100 文字（例）</li>
   *   <li>NOT NULL</li>
   * </ul>
   */
  private String studentFullName;
  /**
   * 学生のフリガナ（カタカナ）。
   * <ul>
   *   <li>DB カラム: student_furigana</li>
   *   <li>型: 文字列</li>
   *   <li>最大長: 100 文字（例）</li>
   *   <li>NOT NULL</li>
   * </ul>
   */
  private String studentFurigana;
  /**
   * ニックネーム。
   * <ul>
   *   <li>DB カラム: student_nickname</li>
   *   <li>型: 文字列</li>
   * </ul>
   */
  private String studentNickname;
  private String email;
  private String prefecture;
  private String city;
  private int age;
  private String gender;
  private String studentRemark; //備考欄
  private Boolean studentIsDeleted; //論理削除 削除フラグ

}
