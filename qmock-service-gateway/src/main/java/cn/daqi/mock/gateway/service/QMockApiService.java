package cn.daqi.mock.gateway.service;

import cn.daqi.mock.gateway.entity.QMockApiEntity;

import java.util.List;

public interface QMockApiService {
    public List<QMockApiEntity> selectApiByPath(String path, String method);
}
