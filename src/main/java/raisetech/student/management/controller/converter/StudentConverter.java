package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      studentsCourses.forEach(courses -> {
        if(Objects.equals(courses.getStudentId(), student.getId())){
          studentDetail.setStudentsCoursesList(courses);
        }
      });

      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}
