package cn.daqi.mock.gateway.service;

import cn.daqi.mock.gateway.entity.QMockApiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author : qi.zhang
 * @ Date : 2023/9/17
 * @ Des :
 */
@Service
public class QMockService{

    @Autowired
    QMockApiService qMockApiService;

    public List<QMockApiEntity> findApiByPath(String path, String method) {
        List<QMockApiEntity> mockApiEntities= qMockApiService.selectApiByPath(path, method);
        return mockApiEntities;
    }

}
