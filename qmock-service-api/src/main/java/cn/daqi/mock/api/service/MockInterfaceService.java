package cn.daqi.mock.api.service;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockInterfaceEntity;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;

public interface MockInterfaceService {

    RespResult searchMockInterface(InterfaceSearchRequest reqs);

    RespResult upsertMockInterface(MockInterfaceEntity interfaceEntity);

    RespResult removeMockInterface(Integer id);
}
