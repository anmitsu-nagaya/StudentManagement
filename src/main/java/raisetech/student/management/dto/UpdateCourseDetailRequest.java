package raisetech.student.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.dto.updatedata.UpdateStatusRequest;

@Schema(description = "受講生登録情報")
@Getter
@Setter
public class UpdateCourseDetailRequest {

  @Valid
  private StudentCourse course;
  @Valid
  private UpdateStatusRequest status;
}
