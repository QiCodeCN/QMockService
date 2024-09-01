package cn.daqi.mock.api.mapper;

import cn.daqi.mock.api.entity.MockInterfaceEntity;
import cn.daqi.mock.api.entity.MockRuleEntity;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;
import cn.daqi.mock.api.entity.requests.MockRuleRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MockRuleMapper {

    List<MockRuleEntity> getMockRuleByApi(MockRuleRequest reqs);

    Integer insertMockRule(MockRuleEntity entity);

    Integer updateMockRule(MockRuleEntity entity);

    @Update("update mock_rule set rule_enable=2 where rule_id=#{id}")
    Integer removeMockRule(Integer id);
}
