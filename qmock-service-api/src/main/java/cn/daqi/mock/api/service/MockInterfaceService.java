package cn.daqi.mock.api.service;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;

public interface MockInterfaceService {

    RespResult searchMockInterface(InterfaceSearchRequest reqs);

    RespResult upsertMockInterface();

    RespResult removeMockInterface(Integer id);
}
