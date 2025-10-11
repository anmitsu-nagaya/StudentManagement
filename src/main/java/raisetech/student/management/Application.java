package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  //課題
  //複数の情報を登録したらどうなる？
  //Mapの中の一部の情報を更新したい場合（Keyに紐づく受講生の情報をアップデートしたい）として、Postするとどうなる？
  private Map<String,String> student = new HashMap<>();


  public static void main(String[] args) {
    // localhost:8080
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return student.toString();
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(@RequestParam String name, @RequestParam String age){
    student.put(name,age);
  }

  //以下いったん残している
  //@PostMapping("/updateStudentName")
  //public void  updateStudentName(String privateName){
  //  this.privateName = privateName;
  //}

}
