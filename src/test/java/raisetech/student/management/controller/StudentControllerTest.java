package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.controller.requestformat.RegisterRequestFormat;
import raisetech.student.management.controller.requestformat.registerdata.RegisterStudentCourseData;
import raisetech.student.management.controller.requestformat.registerdata.RegisterStudentData;
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
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "3b333f9d-993c-48c6-97ca-4a94bb7894b7";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());
    verify(service, times(1)).findStudentDetailById(id);
  }

  @Test
  void 受講生検索のIDにUUID以外が渡されたときにエラーがでること() throws Exception {
    String id = "ID";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(
            "リクエストのパラメータが正しくありません: showStudentDetail.id: must match \"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$\""));
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/register-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                           "student":{
                               "studentFullName" : "山田太郎",
                               "studentFurigana" : "ヤマダタロウ",
                               "studentNickname" : "タロー",
                               "email" : "taro@example.com",
                               "prefecture" : "東京都",
                               "city" : "渋谷区",
                               "age" : 30,
                               "gender" : "男性",
                               "studentRemark" : ""
                           },
                           "studentCoursesList" : [
                               {
                                   "courseName" : "Javaコース"
                               }
                           ]
                       }
                    """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudentDetailList(any());
  }


  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/update-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "student":{
                            "id" : "3b333f9d-993c-48c6-97ca-4a94bb7894b7",
                            "studentFullName" : "かきくけこ",
                            "studentFurigana" : "タチバナヒカリ",
                            "studentNickname" : "ヒカリ",
                            "email" : "hikari@example.com",
                            "prefecture" : "北海道",
                            "city" : "札幌市",
                            "age" : 20,
                            "gender" : "女性",
                            "studentRemark" : "",
                            "studentIsDeleted" : false
                        },
                        "studentCoursesList": [
                            {
                                "studentCourseName" : "デザインコース"
                            }
                        ]
                    }
                    """
            ))
        .andExpect(status().isOk());
    verify(service, times(1)).updateStudentDetailList(any());
  }

  @Test
  void exceptionエンドポイントでNotFoundExceptionがハンドリングされて400が返ること()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/exception"))
        .andExpect(status().is4xxClientError())
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
