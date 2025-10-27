package raisetech.student.management.controller.requestformat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.controller.requestformat.updatedata.UpdateStudentCourseData;
import raisetech.student.management.controller.requestformat.updatedata.UpdateStudentData;

@Schema(description = "受講生登録情報")
@Getter
@Setter
public class UpdateRequestFormat {

  @Valid
  private UpdateStudentData student;
  @Valid
  private List<UpdateStudentCourseData> studentCoursesList;
}
