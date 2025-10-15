package raisetech.student.management.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.domain.Student;
import raisetech.student.management.domain.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //ドメイン処理とか業務処理
    return repository.searchStudent();
  }

  public List<Student> searchStudentsInThirtiesList(){
    //検索処理
    //絞り込みをする。年齢が30台の人のみを抽出する。
    List<Student> studentsInThirties = new ArrayList<>();
    List<Student> studentList = repository.searchStudent();
    for(Student s : studentList){
      if(s.getAge() >= 30 && s.getAge() < 40){
        studentsInThirties.add(s);
      }
    }
    return studentsInThirties;
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }
}
