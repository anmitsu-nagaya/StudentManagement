package raisetech.student.management.controller.requestformat.updatadata;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース更新内容")
@Getter
@Setter
public class UpdateStudentCourseData {

  private String courseName;


}
