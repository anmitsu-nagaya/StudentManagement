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

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return studentMap.toString();
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(@RequestBody List<StudentClassForJSON> students){
    students.forEach(s -> studentMap.put(s.getName(), s.getAge()));
  }

  public static class StudentClassForJSON {
    private String name;
    private String age;

    public String getName() { return name; }
    public String getAge() { return age; }
  }
}
