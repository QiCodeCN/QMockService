package cn.daqi.mock.gateway.mapper;

import cn.daqi.mock.gateway.entity.QMockApiRuleEntity;
import cn.daqi.mock.gateway.handler.JSONObjectTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/12/02
 * @ Des :
 */

@Mapper
public interface QMockApiRuleMapper {

    @Select("SELECT * from mock_rule WHERE rule_api_id=#{apiId} and rule_enable <> 3 ORDER BY rule_enable, rule_update_date DESC")
    @Results(id = "apiRuleMap", value = {
        @Result(column = "rule_id", property = "id"),
        @Result(column = "rule_api_id", property = "apiId"),
        @Result(column = "rule_title", property = "title"),
        @Result(column = "rule_type", property = "type"),
        @Result(column = "rule_enable", property = "enable"),
        @Result(column = "rule_request_filter", property = "reqFilter", jdbcType =  JdbcType.VARCHAR, typeHandler = JSONObjectTypeHandler.class),
        @Result(column = "rule_response_body", property = "resBody", jdbcType =  JdbcType.VARCHAR, typeHandler = JSONObjectTypeHandler.class),
        @Result(column = "rule_response_code",property = "resCode"),
        @Result(column = "rule_shell", property = "shell"),

        @Result(column = "rule_create_user", property = "createUser"),
        @Result(column = "rule_create_date", property = "createDate"),
        @Result(column = "rule_update_user", property = "updateUser"),
        @Result(column = "rule_update_date", property = "updateDate")
    })
    List<QMockApiRuleEntity> getMockApiRuleLisByApiId(@Param("apiId") Long apiId);

}
