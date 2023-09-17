package cn.daqi.mock.gateway.service;

import cn.daqi.mock.gateway.entity.QMockApiEntity;
import cn.daqi.mock.gateway.mapper.QMockApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/9/17
 * @ Des :
 */

@Service("QMockApiService")
public class QMockApiServiceImpl implements QMockApiService{

    @Autowired
    private QMockApiMapper qMockApiMapper;

    public List<QMockApiEntity> selectApiByPath(String path, String method) {
        List<QMockApiEntity> mockApiEntities = qMockApiMapper.getMockApiListByPath(path, method);
        return mockApiEntities;
    }
}
