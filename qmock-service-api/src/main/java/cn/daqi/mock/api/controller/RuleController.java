package cn.daqi.mock.api.controller;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockRuleEntity;
import cn.daqi.mock.api.entity.requests.MockRuleRequest;
import cn.daqi.mock.api.service.MockRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 公众号《非典型性程序员》
 * @ Describe: 接口管理API
 */
@RestController
@RequestMapping("/api/mock")
public class RuleController {

    @Autowired
    MockRuleService mockRuleService;

    @PostMapping(value = "/rule/list")
    public RespResult getInterfaceList(@RequestBody MockRuleRequest req) {
        return mockRuleService.getMockRuleByApi(req);
    }

    @PostMapping(value = "/rule/upsert")
    public RespResult upsertInterface(@RequestBody MockRuleEntity mockRuleEntity) {
        return mockRuleService.upsertMockRule(mockRuleEntity);
    }

    @PostMapping(value = "/rule/remove")
    public RespResult removeInterface(Integer id) {
        return mockRuleService.removeMockRule(id);
    }
}
