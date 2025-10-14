package raisetech.student.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Application {
  //CRUD処理（Create, Read, Update, Delete）
  //Create：登録（例：@Insert）
  //Read：取得（例：@Select）
  //Update：更新（例：@Update）
  //Delete：削除（例：@Delete）

  //自動でインスタンス生成してくれる
  //本来であったらnewなど書いてインスタンス生成しないと空っぽだったりnullだったりする
  //SpringBootとMyBatisは連携していて、@AutowiredはSpringBootが起動した瞬間に自動的にインスタンス化してくれて自動的に当てはめてくれる
  //「自動で紐づける」
  @Autowired
  private StudentRepository repository;


  //private final Map<String,String> studentMap = new ConcurrentHashMap<>();

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/student")
  public String getStudent(@RequestParam String name) {
    RepositoryStudent student = repository.searchByName(name);
    return student.getName() + " " + student.getAge() + "歳";
  }

  @PostMapping("/student")
  public void registerStudent(String name, int age){
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudent(String name,int age){
    repository.updateStudent(name,age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(String name){
    repository.deleteStudent(name);
  }

}
