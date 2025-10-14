package raisetech.student.management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Application {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  //課題：生徒情報を一覧表示する
  @GetMapping("/student")
  public String getStudent() {
    Printer printer = new Printer();
    List<RepositoryStudent> student = repository.findAll();
    List<String> studentList = new ArrayList<>();
    for(RepositoryStudent s : student){
      studentList.add(printer.printer(s.getName(), s.getAge()));
    }
    return studentList.toString();
  }

  @PostMapping("/student")
  public void registerStudent(String name, int age){
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudent(String name,int age){
    repository.updateStudent(name,age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(String name){
    repository.deleteStudent(name);
  }

}
