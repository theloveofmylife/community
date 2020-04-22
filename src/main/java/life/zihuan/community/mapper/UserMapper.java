package life.zihuan.community.mapper;

import life.zihuan.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into USER (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    @Select("select * from USER where Token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from USER where id = #{id}")
    User findById(@Param("id") int creator);

    @Select("select * from USER where account_id = #{account_id}")
    User findByAccountId(@Param("account_id") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where account_id=#{accountId}")
    void update(User user);
}
