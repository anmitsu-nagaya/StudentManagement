package raisetech.student.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.Student;
import raisetech.student.management.domain.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;

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
    return repository.searchAllStudent();
  }

  @GetMapping("/AllStudentsCoursesList")
  public List<StudentsCourses> getStudentsCourseList() {
    return repository.searchAllStudentsCourses();
  }


}
