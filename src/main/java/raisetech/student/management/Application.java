package raisetech.student.management;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@SpringBootApplication が付いたクラスは、
//アプリ全体の起動設定とエントリーポイント（入口）を担う特別なクラスです。
//「Springを起動する」
//「設定情報を読み込む」
//「全体の構成を初期化する」
//この役割だけを持たせるのが原則です。

@RestController
//@RestController は、「HTTPリクエストを受け取り、処理して返す」というWeb層の責務を持ちます。
//「/studentInfo へのリクエストを受け取る」
//「入力を処理する」
//「結果をレスポンスとして返す」
//これは「起動処理」とは別の責務です。

//本来は
//SpringApplication.run():アプリ起動、Applicationクラス
//@GetMapping, @PostMapping:Webリクエスト処理、Controllerクラス
//今は責務が混在している

public class Application { //これがコントローラ(Controller)のこと、URLに /studentInfo が来たら、このクラス内の対応メソッドを呼び出します。 つまり、Application クラスが「Webリクエストを受けて処理を実行し、結果を返す役割＝コントローラ」です。
//リクエストとは：ブラウザやPostmanから送られる1回のアクセス のこと、リクエストは「ユーザーが送る命令」で、アクセスするたびに新しく発生

  //インスタンス（Instance）→ Spring が自動的に1つだけ生成した Application クラスの実体
  //Springはアプリ起動時に：new Application();のような処理を内部で行い、この1つのオブジェクトを全リクエストで共有します。
  //（これを「singletonスコープ」と呼びます）
  //どのユーザーからのリクエストも 同じ Application インスタンス に届く
  //その中にある student フィールド（new HashMap<>()）も 全員で共有して使う
  //だから起こる問題
  //この student は、コントローラ（＝Applicationクラス）に保持され、そのインスタンスが全ユーザー共通の1つだけなので、
  //複数のリクエストが同時に student.put() するとデータが壊れる（競合）。

  //ConcurrentHashMap は、こうした同時書き込み・読み込みを安全に処理するよう設計されたMapです。
  //スレッド（thread） = プログラム内で同時に動く処理の流れ。Webサーバーは、複数ユーザーが同時にアクセスしてきたとき、それぞれのリクエストを 別スレッドで並行処理 します。
  //スレッドセーフ（thread-safe）とは：複数のスレッドが同じオブジェクトを同時に触っても、データが壊れない設計になっている状態を指します。
  //ConcurrentHashMap は内部で、
  //複数の部分に分割してロックをかける
  //読み込み時はロックを最小限に抑える
  //書き込み時は必要な部分だけをロックする
  //といった制御をしており、複数スレッドが同時に触っても壊れません。
  //つまり「効率的かつ安全に同時操作ができるHashMap」です。

  //課題
  //①複数の情報を登録したらどうなる？
  private final Map<String,String> studentMap = new ConcurrentHashMap<>();


  public static void main(String[] args) {
    // localhost:8080
    SpringApplication.run(Application.class, args);
  }

  //JSONでやるならmap自体をreturnでOK
  @GetMapping("/studentInfo")
  public Map<String,String> getStudentInfo() {
    return studentMap;
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(@RequestBody List<StudentClassForJSON> students){
    students.forEach(s -> studentMap.put(s.getName(), s.getAge()));
  }

  public static class StudentClassForJSON { //Jackson（Springが標準で使うJSONライブラリ）は、デフォルトコンストラクタと getter/setter が必要
    private String name;
    private String age;

    // デフォルトコンストラクタ
    //public StudentClassForJSON() { }

    // getter / setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

  }

  //@RequestParam
  //使う場面：URLのパラメータやフォームの入力値を受け取るとき。
  //データ形式：クエリパラメータまたはフォームデータ（application/x-www-form-urlencoded）。
  //使用場所：URL や form の入力
  //適している用途：単純な値

  //@RequestBody
  //使う場面：リクエスト本文（body）に構造化データを送るとき。
  //データ形式：JSON（application/json）。
  //使用場所：HTTPボディ
  //適している用途：複雑なデータ構造（オブジェクト・リスト）

  //②Mapの中の一部の情報を更新したい場合（Keyに紐づく受講生の情報をアップデートしたい）として、Postするとどうなる？
  @PostMapping("/updateStudentName")
  public void updateStudentName(@RequestBody List<StudentClassForJSON> students){
    students.forEach(s -> {
      if(studentMap.containsKey(s.getName())){
        studentMap.put(s.getName(), s.getAge());
      }
    });
  }

}
