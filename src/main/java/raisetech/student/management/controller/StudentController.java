package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.requestformat.RegisterRequestFormat;
import raisetech.student.management.controller.requestformat.UpdateRequestFormat;
import raisetech.student.management.controller.requestformat.registerdata.RegisterStudentCourseData;
import raisetech.student.management.controller.requestformat.registerdata.RegisterStudentData;
import raisetech.student.management.controller.requestformat.updatedata.UpdateStudentCourseData;
import raisetech.student.management.controller.requestformat.updatedata.UpdateStudentData;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */

@Validated
@RestController
public class StudentController {

  private StudentService service;

  /**
   * コンストラクタ
   *
   * @param service 受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @Operation(
      summary = "一覧検索",
      description = "受講生の一覧を検索します。全件検索を行うので、条件指定は行いません。",
      operationId = "getStudentList",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "一覧表示検索成功。検索した全受講生のデータを返します。",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = StudentDetail.class))
              )
          )
      }
  )
  @GetMapping("/students")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }


  @Operation(
      summary = "受講生検索",
      description = "受講生詳細検索です。IDに紐づく任意の受講生の情報を取得します。",
      operationId = "showStudentDetail",
      parameters = {
          @Parameter(
              name = "id",
              description = "検索したい受講生の受講生ID",
              in = ParameterIn.PATH,
              required = true,
              example = "123a4567-b89c-12d3-e456-789123455678"
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "受講生検索成功。検索した受講生のデータを返します。",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StudentDetail.class)
              )
          )
      }
  )
  @GetMapping("/student/{id}")
  public StudentDetail showStudentDetail(@PathVariable("id") @Size(min = 36, max = 36) String id) {
    return service.findStudentDetailById(id);
  }


  @Operation(
      summary = "受講生登録",
      description = "受講生情報とコース情報を登録します。",
      operationId = "registerStudent",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          description = "登録する受講生の詳細情報。受講生ID・コースID・コース開始日・コース修了日はservice層で自動採番・自動登録します。",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RegisterRequestFormat.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "登録成功。登録された受講生情報を返します。",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StudentDetail.class)
              )
          )
      }
  )
  @PostMapping("/register-student")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid RegisterRequestFormat registerRequestFormat) {
    StudentDetail studentDetail = registerStudentDetail(registerRequestFormat);
    StudentDetail responseStudentDetail = service.registerStudentDetailList(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * レスポンスされた受講生リストのデータ(不完全)を、受講生リストの型にマッピングします。OpenAPI用に作成したメソッドです。
   *
   * @param registerRequestFormat リクエスト側で必要な項目のみデータとして格納されている受講生リスト
   * @return　Postする際に必要な形にマッピングをした受講生リスト
   */
  private StudentDetail registerStudentDetail(RegisterRequestFormat registerRequestFormat) {

    RegisterStudentData formatStudent = registerRequestFormat.getStudent();
    Student student = new Student();
    student.setStudentFullName(formatStudent.getStudentFullName());
    student.setStudentFurigana(formatStudent.getStudentFurigana());
    student.setStudentNickname(formatStudent.getStudentNickname());
    student.setEmail(formatStudent.getEmail());
    student.setPrefecture(formatStudent.getPrefecture());
    student.setCity(formatStudent.getCity());
    student.setAge(formatStudent.getAge());
    student.setGender(formatStudent.getGender());
    student.setStudentRemark(formatStudent.getStudentRemark());

    List<RegisterStudentCourseData> formatstudentCoursesList = registerRequestFormat.getStudentCoursesList();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    for (RegisterStudentCourseData formatCourseData : formatstudentCoursesList) {
      StudentCourse studentCourse = new StudentCourse();
      studentCourse.setCourseName(formatCourseData.getCourseName());
      studentCourseList.add(studentCourse);
    }

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCoursesList(studentCourseList);
    return studentDetail;
  }


  @Operation(
      summary = "受講生更新",
      description = "受講生情報とコース情報を更新します。受講生IDが必要です。",
      operationId = "updateStudent",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          description = "更新する受講生の詳細情報",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UpdateRequestFormat.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "更新成功。更新された受講生情報を返します。",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StudentDetail.class)
              )
          )
      }
  )
  @PutMapping("/update-student")
  public ResponseEntity<String> updateStudent(
      @RequestBody @Valid UpdateRequestFormat updateRequestFormat) {
    StudentDetail studentDetail = updateStudentDetail(updateRequestFormat);
    service.updateStudentDetailList(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * レスポンスされた受講生リストのデータ(不完全)を、受講生リストの型にマッピングします。OpenAPI用に作成したメソッドです。
   *
   * @param updateRequestFormat リクエスト側で必要な項目のみデータとして格納されている受講生リスト
   * @return　Putする際に必要な形にマッピングをした受講生リスト
   */
  private StudentDetail updateStudentDetail(UpdateRequestFormat updateRequestFormat) {

    UpdateStudentData formatStudent = updateRequestFormat.getStudent();
    Student student = new Student();
    student.setId(formatStudent.getId());
    student.setStudentFullName(formatStudent.getStudentFullName());
    student.setStudentFurigana(formatStudent.getStudentFurigana());
    student.setStudentNickname(formatStudent.getStudentNickname());
    student.setEmail(formatStudent.getEmail());
    student.setPrefecture(formatStudent.getPrefecture());
    student.setCity(formatStudent.getCity());
    student.setAge(formatStudent.getAge());
    student.setGender(formatStudent.getGender());
    student.setStudentRemark(formatStudent.getStudentRemark());
    student.setStudentIsDeleted(formatStudent.getStudentIsDeleted());

    List<UpdateStudentCourseData> formatstudentCoursesList = updateRequestFormat.getStudentCoursesList();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    for (UpdateStudentCourseData formatCourseData : formatstudentCoursesList) {
      StudentCourse studentCourse = new StudentCourse();
      studentCourse.setCourseName(formatCourseData.getCourseName());
      studentCourseList.add(studentCourse);
    }

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCoursesList(studentCourseList);
    return studentDetail;
  }

  @GetMapping("/exception")
  public ResponseEntity<String> throwException() throws NotFoundException {
    throw new NotFoundException("このAPIは現在利用できません。古いURLとなっています。");
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

}


