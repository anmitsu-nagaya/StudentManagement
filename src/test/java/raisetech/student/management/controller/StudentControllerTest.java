package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.converter.DomainDtoConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.RegisterStudentDetailRequest;
import raisetech.student.management.dto.registerdata.RegisterCourseRequest;
import raisetech.student.management.dto.registerdata.RegisterStudentRequest;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  @MockitoBean
  private DomainDtoConverter converter;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  private Student student;
  private StudentCourse studentCourse;
  private StudentDetail studentDetail;

  private RegisterStudentRequest registerStudent;
  private RegisterCourseRequest registerCourse;

  private RegisterStudentRequest registerStudentRequest;
  private RegisterCourseRequest registerCourseRequest;
  private RegisterStudentDetailRequest registerRequest;

  @BeforeEach
  void before() {
    student = new Student();
    studentCourse = new StudentCourse();
    studentDetail = new StudentDetail();

    registerStudent = new RegisterStudentRequest();
    registerCourse = new RegisterCourseRequest();

    registerStudentRequest = new RegisterStudentRequest();
    registerCourseRequest = new RegisterCourseRequest();
    registerRequest = new RegisterStudentDetailRequest();
  }

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/students"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = UUID.randomUUID().toString();
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());
    verify(service, times(1)).findStudentDetailById(id);
  }


  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/register-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                            "student": {
                                "studentFullName": "山田太郎",
                                "studentFurigana": "ヤマダタロウ",
                                "studentNickname": "たろちゃん",
                                "email": "yamada.taro@example.com",
                                "prefecture": "東京都",
                                "city": "渋谷区",
                                "age": 25,
                                "gender": "男性",
                                "studentRemark": "積極的に質問する学生"
                            },
                            "courseList": [
                                {
                                    "course": {
                                        "courseName": "Javaコース"
                                    }
                                }
                            ]
                    }
                    """
            ))
        .andExpect(status().isOk());

    verify(converter, times(1)).toStudentDetailDomain(any());
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
                            "id" : "550e8400-e29b-41d4-a716-446655440001",
                            "studentFullName": "山田太郎",
                                "studentFurigana": "ヤマダタロウ",
                                "studentNickname": "たろちゃん",
                                "email": "yamada.taro@example.com",
                                "prefecture": "東京都",
                                "city": "渋谷区",
                                "age": 25,
                                "gender": "男性",
                                "studentRemark": "積極的に質問する学生",
                            "studentIsDeleted" : false
                        },
                        "courseList": [
                                {
                                    "course": {
                                        "id": 1,
                                        "courseName": "Javaコース"
                                    }
                                }
                                {
                                    "status": {
                                        "id": 1,
                                        "status": "本申込"
                                    }
                                }
                        ]
                    }
                    """
            ))
        .andExpect(status().isOk());
    verify(service, times(1)).updateStudentDetailList(any());
  }


  @Test
  void 存在しないURLにアクセスしたときにエラーレスポンスが返ること()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("このAPIは現在利用できません。古いURLとなっています。"));
  }

  @Test
  void リクエストのパラメータに不正な値が渡されたときにエラーレスポンスが返ること()
      throws Exception {
    String id = "ID";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(
            "リクエストのパラメータが正しくありません: showStudentDetail.id: must match \"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$\""));
  }

  @Test
  void 不正な入力があるときにバリデーションエラーレスポンスが返ること() throws Exception {
    String invalidJson = """
        {
          "student": {
            "studentFullName": "",
            "email": "invalid-email"
          }
        }
        """;

    mockMvc.perform(MockMvcRequestBuilders.post("/register-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("入力値が不正です"))
        .andExpect(jsonPath("$.details.length()").value(3))
        .andExpect(jsonPath("$.details[0].field").exists())
        .andExpect(jsonPath("$.details[0].message").exists());


  }

  @Test
  void リクエスト形式に問題があるときにエラーレスポンスが返ること() throws Exception {
    String invalidJson = """
        {
          "student": {
            "studentFullName": "山田太郎"
            "email": "yamada.taro@example.com"
          }
        }
        """;

    mockMvc.perform(MockMvcRequestBuilders.post("/register-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJson))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(
            "リクエスト形式に問題があります：JSON parse error: Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries"));

  }


  @Test
  void 登録リクエストの受講生詳細の受講生で適切な値を入力したときに入力チェックが正しく実行されて異常が発生しないこと() {
    registerStudent.setStudentFullName("山田太郎");
    registerStudent.setStudentFurigana("ヤマダタロウ");
    registerStudent.setStudentNickname("たろちゃん");
    registerStudent.setEmail("yamada.taro@example.com");
    registerStudent.setPrefecture("東京都");
    registerStudent.setCity("渋谷区");
    registerStudent.setAge(25);
    registerStudent.setStudentRemark("積極的に質問する学生");

    Set<ConstraintViolation<RegisterStudentRequest>> violations = validator.validate(
        registerStudent);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 登録リクエストのコース詳細で適切な値を入力したときに入力チェックが正しく実行されて異常が発生しないこと() {
    registerCourse.setCourseName("Javaコース");

    Set<ConstraintViolation<RegisterCourseRequest>> violations = validator.validate(
        registerCourse);

    assertThat(violations.size()).isEqualTo(0);
  }


}
