package raisetech.student.management;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Application {

  //自動でインスタンス生成してくれる
  //本来であったらnewなど書いてインスタンス生成しないと空っぽだったりnullだったりする
  //SpringBootとMyBatisは連携していて、@AutowiredはSpringBootが起動した瞬間に自動的にインスタンス化してくれて自動的に当てはめてくれる
  //「自動で紐づける」
  @Autowired
  private StudentRepository repository;


  //private final Map<String,String> studentMap = new ConcurrentHashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    Student student = repository.searchByName("TanakaKousuke");
    return student.getName() + " " + student.getAge() + "歳";
  }

  //@PostMapping("/studentInfo")
  //public void setStudentInfo(@RequestBody List<StudentClassForJSON> students){
  //  students.forEach(s -> studentMap.put(s.getName(), s.getAge()));
  //}

  //public static class StudentClassForJSON {
  //  private String name;
  //  private String age;

  //  public String getName() { return name; }
  //  public void setName(String name) { this.name = name; }
  //  public String getAge() { return age; }
  //  public void setAge(String age) { this.age = age; }
  //}

  //@PostMapping("/updateStudentAge")
  //public void updateStudentName(@RequestBody List<StudentClassForJSON> students){
  //  students.forEach(s -> {
  //    if(studentMap.containsKey(s.getName())){
  //      studentMap.put(s.getName(), s.getAge());
  //    }
  //  });
  //}
}
