package life.zihuan.community.mapper;

import life.zihuan.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into QUESTION (title,\n" +
            "\tdescription,\n" +
            "\tgmt_create,\n" +
            "\tgmt_modified,\n" +
            "\tcreator,\n" +
            "\ttag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);
}
