package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  private Student student;
  private StudentCourse studentCourse;
  private StudentDetail studentDetail;

  private RegisterStudentData requestStudent;
  private RegisterStudentCourseData requestCourse;
  private RegisterRequestFormat request;

  @BeforeEach
  void before() {
    student = new Student();
    studentCourse = new StudentCourse();
    studentDetail = new StudentDetail();

    requestStudent = new RegisterStudentData();
    requestCourse = new RegisterStudentCourseData();
    request = new RegisterRequestFormat();
  }

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/students"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生登録のレスポンス内容が正しいこと() throws Exception {
    // --- モックの戻り値 ---
    student.setStudentFullName("山田");
    student.setStudentFurigana("ヤマダタロウ");
    student.setEmail("taro@example.com");
    studentCourse.setCourseName("Javaコース");
    studentDetail.setStudent(student);
    studentDetail.setStudentCoursesList(List.of(studentCourse));

    when(service.registerStudentDetailList(any(StudentDetail.class)))
        .thenReturn(studentDetail);

    // --- リクエストBody ---
    requestStudent.setStudentFullName("てすと");
    requestStudent.setStudentFurigana("テスト");
    requestStudent.setEmail("test@example.com");
    requestCourse.setCourseName("テスト");
    request.setStudent(requestStudent);
    request.setStudentCoursesList(List.of(requestCourse));

    String json = new ObjectMapper().writeValueAsString(request);

    // --- MockMvc実行 & レスポンス検証 ---
    mockMvc.perform(MockMvcRequestBuilders.post("/register-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.studentFullName").value("山田太郎"))
        .andExpect(jsonPath("$.student.studentFurigana").value("ヤマダタロウ"))
        .andExpect(jsonPath("$.student.email").value("taro@example.com"))
        .andExpect(jsonPath("$.studentCoursesList[0].courseName").value("Javaコース"));
  }

  @Test
  void 受講生登録時にサービスが正しく呼び出されて正しい引数が渡されること() throws Exception {
    // --- リクエストBody ---
    requestStudent.setStudentFullName("山田太郎");
    requestStudent.setStudentFurigana("ヤマダ");
    requestStudent.setEmail("taro@example.com");
    requestCourse.setCourseName("Javaコース");
    request.setStudent(requestStudent);
    request.setStudentCoursesList(List.of(requestCourse));

    String json = new ObjectMapper().writeValueAsString(request);

    // --- モック ---
    when(service.registerStudentDetailList(any(StudentDetail.class)))
        .thenReturn(new StudentDetail());

    // --- MockMvc実行 ---
    mockMvc.perform(MockMvcRequestBuilders.post("/register-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());

    // --- サービス引数を検証 ---
    ArgumentCaptor<StudentDetail> captor = ArgumentCaptor.forClass(StudentDetail.class);
    verify(service, times(1)).registerStudentDetailList(captor.capture());

    StudentDetail actual = captor.getValue();
    assertThat(actual.getStudent().getStudentFullName()).isEqualTo("山田太郎");
    assertThat(actual.getStudent().getStudentFurigana()).isEqualTo("ヤマダタロウ");
    assertThat(actual.getStudent().getEmail()).isEqualTo("taro@example.com");

    assertThat(actual.getStudentCoursesList()).hasSize(1);
    assertThat(actual.getStudentCoursesList().get(0).getCourseName()).isEqualTo("Javaコース");
  }

  @Test
  void 受講生詳細の更新が実行できること() throws Exception {

    UpdateStudentData requestStudent = new UpdateStudentData();
    requestStudent.setId("3b333f9d-993c-48c6-97ca-4a94bb7894b7");
    requestStudent.setStudentFullName("山田太郎");
    requestStudent.setStudentFurigana("ヤマダタロウ");
    requestStudent.setEmail("test@example.com");

    UpdateStudentCourseData requestCourse = new UpdateStudentCourseData();
    requestCourse.setCourseName("Javaコース");

    UpdateRequestFormat request = new UpdateRequestFormat();
    request.setStudent(requestStudent);
    request.setStudentCoursesList(List.of(requestCourse));

    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.put("/update-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());

    ArgumentCaptor<StudentDetail> captor = ArgumentCaptor.forClass(StudentDetail.class);
    verify(service, times(1)).updateStudentDetailList(captor.capture());
  }

  @Test
  void exceptionエンドポイントでNotFoundExceptionがハンドリングされて400が返ること()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/exception"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("このAPIは現在利用できません。古いURLとなっています。"));
  }


  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    student.setId("3b333f9d-993c-48c6-97ca-4a94bb7894b7");
    student.setStudentFullName("山田太郎");
    student.setStudentFurigana("ヤマダタロウ");
    student.setStudentNickname("タロー");
    student.setEmail("taro@example.com");
    student.setPrefecture("東京都");
    student.setCity("渋谷区");
    student.setAge(28);
    student.setStudentRemark("Javaの勉強中です。");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDにUUID以外を用いたときに入力チェックに掛かること() {
    student.setId("テストです。");
    student.setStudentFullName("山田太郎");
    student.setStudentFurigana("ヤマダタロウ");
    student.setEmail("test@example.com");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("UUIDの形式が正しくありません。");

  }

  @Test
  void 受講生詳細の受講生で1o文字以上の都道府県を用いたときに入力チェックに掛かること() {
    student.setPrefecture("東京都渋谷区2-31-4");

    student.setStudentFullName("山田太郎");
    student.setStudentFurigana("ヤマダタロウ");
    student.setEmail("test@example.com");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("文字数が超過しています。");

  }

  @Test
  void 受講生詳細の受講生のメールアドレスでアドレス型以外を用いたときに入力チェックに掛かること() {
    student.setStudentFullName("山田太郎");
    student.setStudentFurigana("ヤマダタロウ");
    student.setEmail("testexample.com");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("電子メールアドレスとして正しい形式にしてください");

  }

  @Test
  void 受講生詳細のコース詳細でコースIDに数字以外を用いたときに入力チェックに掛かること() {
    studentCourse.setCourseId("テストID");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみで入力してください");

  }

}
