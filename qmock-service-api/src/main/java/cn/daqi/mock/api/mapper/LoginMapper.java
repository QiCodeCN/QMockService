package cn.daqi.mock.api.mapper;

import org.apache.ibatis.annotations.*;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe:
 */
@Mapper
public interface LoginMapper {

    @Select("SELECT count(*) FROM users WHERE name=#{name} and `password`=#{password}")
    Integer userLogin(@Param("name") String name, @Param("password") String password);
}
