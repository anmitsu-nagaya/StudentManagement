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
@RestController

public class Application {
  private final Map<String,String> studentMap = new ConcurrentHashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  //課題①複数の情報を登録したらどうなる？
  @GetMapping("/studentInfo")
  public Map<String,String> getStudentInfo() {
    return studentMap;
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(@RequestBody List<StudentClassForJSON> students){
    students.forEach(s -> studentMap.put(s.getName(), s.getAge()));
  }

  public static class StudentClassForJSON {
    private String name;
    private String age;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
  }

  //課題②Mapの中の一部の情報を更新したい場合（とある受講生の年齢の情報をアップデートしたい）として、Postするとどうなる？
  @PostMapping("/updateStudentName")
  public void updateStudentName(@RequestBody List<StudentClassForJSON> students) {
    students.forEach(s -> studentMap.put(s.getName(), s.getAge()));
  }

}
