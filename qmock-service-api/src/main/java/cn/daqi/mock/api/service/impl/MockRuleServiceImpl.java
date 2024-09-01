package cn.daqi.mock.api.service.impl;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockRuleEntity;
import cn.daqi.mock.api.entity.requests.MockRuleRequest;
import cn.daqi.mock.api.mapper.MockRuleMapper;
import cn.daqi.mock.api.service.MockRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MockRuleServiceImpl implements MockRuleService {

    @Resource
    MockRuleMapper mockRuleMapper;

    @Override
    public RespResult getMockRuleByApi(MockRuleRequest req) {
        return RespResult.success(mockRuleMapper.getMockRuleByApi(req));
    }

    @Override
    public RespResult upsertMockRule(MockRuleEntity ruleEntity) {
        Integer result=0;
        if (ruleEntity.getId()==null) {
            result = mockRuleMapper.insertMockRule(ruleEntity);
        } else {
            result = mockRuleMapper.updateMockRule(ruleEntity);
        }

        if (result==1){
            return RespResult.success();
        } else {
            return RespResult.failure(RespCode.DATA_SAVE_ERROR);
        }
    }

    @Override
    public RespResult removeMockRule(Integer id) {
        Integer result = mockRuleMapper.removeMockRule(id);
        if (result==1) {
            return RespResult.success();
        } else {
            return RespResult.failure(RespCode.DATA_REMOVE_ERROR);
        }
    }
}
