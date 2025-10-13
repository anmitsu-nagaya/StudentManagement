package raisetech.student.management;

//クラス名＝型名
//コンストラクタ：クラス名と同じ名前を持ち、戻り値の型がないメソッド、new された瞬間に実行される初期化処理
public class RepositoryStudent {

  private String name;
  private int age;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age = age;
  }
}
