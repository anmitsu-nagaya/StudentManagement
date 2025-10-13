package raisetech.student.management;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  RepositoryStudent searchByName(String name);

  @Insert("INSERT student values(#{name}, #{age})")
  void registerStudent(String name, int age);

}
