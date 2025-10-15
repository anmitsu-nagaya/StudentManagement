package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;

//これをつけるとbeanが見つかりません、というエラーが起きない、AutoWiredに管理させるためにこのアノテーションが必要
@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourses> convertStudentCourses = studentsCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentsCoursesList(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}
