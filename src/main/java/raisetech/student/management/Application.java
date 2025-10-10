package raisetech.student.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  private String privateName = "Nagaya Mitsuyo";
  private String privateAge = "29";

  public static void main(String[] args) {
    // localhost:8080
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return privateName + " " + privateAge + "歳";
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(String privateName,String privateAge){
    this.privateName = privateName;
    this.privateAge = privateAge;
  }

  @PostMapping("/updateStudentName")
  public void  updateStudentName(String privateName){
    this.privateName = privateName;
  }

}
