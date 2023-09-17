package cn.daqi.mock.gateway.mapper;

import cn.daqi.mock.gateway.entity.QMockApiEntity;
import cn.daqi.mock.gateway.handler.JSONObjectTypeHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/9/17
 * @ Des :
 */

@Mapper
public interface QMockApiMapper {

    @Select("SELECT * from mock_api WHERE api_path = #{path} AND api_method=#{method};")
    @Results(id = "apiMap",value = {
            @Result(column = "api_id", property = "id"),
            @Result(column = "api_tag_id", property="tagId"),
            @Result(column = "api_project_id", property="projectId"),
            @Result(column = "api_title", property="title"),
            @Result(column = "api_method", property = "method"),
            @Result(column = "api_path", property="path"),
            @Result(column = "api_enable", property="enable"),
            @Result(column = "api_desc", property = "desc"),
            @Result(column = "api_response_code", property="resCode"),
            @Result(column = "api_response_default", property="resDefault",jdbcType = JdbcType.VARCHAR, typeHandler = JSONObjectTypeHandler.class),
            @Result(column = "api_create_user", property = "createUser"),
            @Result(column = "api_create_date", property = "createDate"),
            @Result(column = "api_update_user", property = "updateUser"),
            @Result(column = "api_update_date", property = "updateDate")
    })
    List<QMockApiEntity> getMockApiListByPath(String path, String method);

}
