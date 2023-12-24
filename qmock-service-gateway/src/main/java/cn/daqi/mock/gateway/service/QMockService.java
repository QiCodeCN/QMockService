package cn.daqi.mock.gateway.service;

import cn.daqi.mock.gateway.entity.QMockApiEntity;
import cn.daqi.mock.gateway.entity.QMockApiRuleEntity;
import cn.daqi.mock.gateway.mapper.QMockApiMapper;
import cn.daqi.mock.gateway.mapper.QMockApiRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/9/17
 * @ Des :
 */
@Service("QMockService")
public class QMockService{

    @Autowired
    private QMockApiMapper qMockApiMapper;

    @Autowired
    private QMockApiRuleMapper qMockApiRuleMapper;

    public List<QMockApiEntity> selectApiByPath(String path, String method) {
        List<QMockApiEntity> mockApiEntities = qMockApiMapper.getMockApiListByPath(path, method);
        return mockApiEntities;
    }

    public List<QMockApiRuleEntity> selectApiRuleList(Long apiId) {
        List<QMockApiRuleEntity> mockApiRuleEntities = qMockApiRuleMapper.getMockApiRuleLisByApiId(apiId);
        return mockApiRuleEntities;
    }
}
