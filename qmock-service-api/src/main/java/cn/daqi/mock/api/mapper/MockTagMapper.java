package cn.daqi.mock.api.mapper;

import cn.daqi.mock.api.entity.MockProjectEntity;
import cn.daqi.mock.api.entity.MockTagEntity;
import cn.daqi.mock.api.entity.requests.MockTagRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
 * @ 作  者: Qi
 * @ 公众号: 非典型性程序员
 * @ 博 客：https://blog.csdn.net/zyueqi1987
 * @ 类说明: 数据操作
 */
@Mapper
public interface MockTagMapper {
    @Select("SELECT * FROM mock_tag WHERE tag_state=0")
    @Results(id = "tagMap", value = {
            @Result(column = "tag_id", property = "id"),
            @Result(column = "tag_project_id", property = "projectId"),
            @Result(column = "tag_name", property = "name"),
            @Result(column = "tag_desc", property = "desc"),
            @Result(column = "tag_state", property = "state"),
            @Result(column = "tag_create_user", property = "createUser"),
            @Result(column = "tag_create_date", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "tag_update_user", property = "updateUser"),
            @Result(column = "tag_update_date", property = "updateDate", jdbcType = JdbcType.TIMESTAMP)

    })
    List<MockTagEntity> selectMockTag();

    @Select({"SELECT * FROM mock_tag WHERE tag_state=0 and tag_id=#{tagId}"})
    @ResultMap("tagMap")
    MockTagEntity selectMockTagInfo(Integer tagId);

    @Insert({"INSERT INTO mock_tag (tag_name,tag_project_id,tag_desc,tag_create_user) VALUES ( #{name},#{projectId},#{desc},#{createUser})"})
    int insertTag(MockTagEntity mockTagEntity);


    @Update({"UPDATE mock_tag SET tag_name=#{name} WHERE tag_id=#{id}"})
    int updateTag(MockTagEntity mockTagEntity);

    @Update({"UPDATE mock_tag SET tag_state=1 WHERE tag_id=#{id}"})
    Boolean removeTag(Integer id);
}
