package cn.daqi.mock.api.mapper;

import cn.daqi.mock.api.entity.MockProjectEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 数据操作类
 */
@Mapper
public interface MockProjectMapper {
    /**
     * @return Object
     * @desc 查询项目表多条数据
     */
    @Select("SELECT * FROM mock_project ORDER BY mp_update_date DESC")
    @Results(id = "projectMap", value = {
            @Result(column = "mp_id", property = "id"),
            @Result(column = "mp_name", property = "name"),
            @Result(column = "mp_type", property = "type"),
            @Result(column = "mp_desc", property = "desc"),
            @Result(column = "mp_owner", property = "owner"),
            @Result(column = "mp_create_user", property = "createUser"),
            @Result(column = "mp_create_date", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "mp_update_user", property = "updateUser"),
            @Result(column = "mp_update_date", property = "updateDate", jdbcType = JdbcType.TIMESTAMP)

    })
    List<MockProjectEntity> selectMockProject();


    /**
     * @param name
     * @return List<MockProjectEntity>
     * @desc 数据库项目表，根据id查询project详细信息
     */
    @Select("SELECT * FROM mock_project WHERE mp_name LIKE CONCAT(CONCAT('%',#{name},'%'))")
    @ResultMap("projectMap")
    List<MockProjectEntity> searchMockProject(String name);

    /**
     * @param id
     * @return MockProjectEntity
     * @desc 根据项目ID查询项目详细信息
     */
    @Select("SELECT * FROM mock_project WHERE mp_id=#{id}")
    @ResultMap("projectMap")
    MockProjectEntity selectById(Long id);


    /**
     * @param projectEntity
     * @return 影响数量 插入成功默认1
     * @desc 项目插入SQL
     */
    @Insert({"INSERT INTO mock_project (mp_name,mp_desc,mp_type,mp_owner,mp_create_user,mp_create_date) VALUES ( #{name},#{desc},#{type},#{owner},#{createUser},NOW())"})
    @ResultMap("projectMap")
    int insertProject(MockProjectEntity projectEntity);


    /**
     * 项目修改SQL
     * @param projectEntity
     * @return 影响数量 更新成功默认1
     */
    @Update({"UPDATE mock_project SET mp_name=#{name},mp_desc=#{desc},mp_type=#{type},mp_owner=#{owner},mp_update_user=#{updateUser},mp_update_date=NOW() WHERE mp_id=#{id}"})
    @ResultMap("projectMap")
    int updateProject(MockProjectEntity projectEntity);
}
