package life.zihuan.community.mapper;

import life.zihuan.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into QUESTION (title,\n" +
            "\tdescription,\n" +
            "\tgmt_create,\n" +
            "\tgmt_modified,\n" +
            "\tcreator,\n" +
            "\ttag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size};")
    List<Question> list(@Param("offset") int offset,@Param("size") int size);

    @Select("select count(1) from question")
    int count();

    @Select("select count(1) from question where creator = #{id}")
    int countByUserId(@Param("id") int id);

    @Select("select * from question where creator = #{id}")
    List<Question> listByUserId(@Param("id") int id,@Param("offset") int offset,@Param("size") int size);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") int id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
