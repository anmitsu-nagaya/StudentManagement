package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;
  private String studentFullName;
  private String studentFurigana;
  private String studentNickname;
  private String email;
  private String prefecture;
  private String city;
  private int age;
  private String gender;
  private String studentRemark; //備考欄
  private Boolean studentIsDeleted; //論理削除 削除フラグ

}
