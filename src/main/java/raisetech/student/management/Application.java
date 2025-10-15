package raisetech.student.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Application {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/AllStudentList")
  public List<Student> getStudentList() {
    return repository.searchStudentListAll();
  }

  @GetMapping("/AllStudentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return repository.searchStudentCourseListAll();
  }


}
