package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.Student;
import raisetech.student.management.domain.StudentsCourses;
import raisetech.student.management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  //ユーザーに近いところなのでユーザー視点で書くとget
  @GetMapping("/students")
  public List<Student> getStudentList() {
    // リクエストの加工処理、入力チェックとか
    return service.searchStudentList();
  }

  @GetMapping("/students/age-30s")
  public List<Student> getStudentsInThirtiesList(){
    return service.searchStudentsInThirtiesList();
  }

  @GetMapping("/student-courses")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @GetMapping("/student-courses/Java-course")
  public List<StudentsCourses> getJavaCourseList(){
    return service.searchJavaCourseList();
  }

}
