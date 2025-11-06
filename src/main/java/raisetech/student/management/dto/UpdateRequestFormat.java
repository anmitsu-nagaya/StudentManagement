package raisetech.student.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生登録情報")
@Getter
@Setter
public class UpdateRequestFormat {

  @Valid
  private UpdateStudentData student;
  @Valid
  private List<UpdateStudentCourseData> studentCoursesList;
}
