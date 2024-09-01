package cn.daqi.mock.api.service;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockRuleEntity;
import cn.daqi.mock.api.entity.requests.MockRuleRequest;

public interface MockRuleService {

    RespResult getMockRuleByApi(MockRuleRequest req);

    RespResult upsertMockRule(MockRuleEntity ruleEntity);

    RespResult removeMockRule(Integer id);
}
