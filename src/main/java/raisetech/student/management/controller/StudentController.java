package raisetech.student.management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

/**
 * 学生情報に関する Web API および画面表示を提供するコントローラクラス。
 *
 * <p>StudentService を呼び出して、学生データの取得・登録などを行います。
 * 画面（Thymeleaf テンプレート）とのやり取りを担当し、Controller → Service → Repository の流れを実現します。</p>
 *
 * <p>このクラスでは、学生情報の登録・一覧取得・画面表示を担当します。</p>
 */
@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service,StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/students")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();
    model.addAttribute("studentList",converter.convertStudentDetails(students,studentsCourses));
    return "studentList";
  }

  @GetMapping("/student-courses")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @GetMapping("/new-student")
  public String newStudent(Model model){
    model.addAttribute("studentDetail", new StudentDetail());//th:object="${studentDetail}に渡る
    return "registerStudent"; //registerStudent.htmlのこと
  }

  @PostMapping("/register-student")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
    if(result.hasErrors()){
      return "registerStudent";
    }

    //System.out.println(studentDetail.getStudent().getStudentFullName() + "さんが新規受講生として登録されました。");
    //課題1：新規受講生情報を登録する処理を実装する。
    // String id = studentDetail.getStudent().getId();
    String name = studentDetail.getStudent().getStudentFullName();
    String furigana = studentDetail.getStudent().getStudentFurigana();
    String email = studentDetail.getStudent().getEmail();

    //課題2：コース情報も一緒に登録できるように実装する。コースは単体で良い。
    //System.out.println(studentsCourses);
    String courseId = studentDetail.getStudentsCoursesList().getCourseId();
    String courseName=studentDetail.getStudentsCoursesList().getCourseName();

    service.addStudentDetailList(name,furigana,email,courseId,courseName);
    return "redirect:/students";
  }

}
