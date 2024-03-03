package cn.daqi.mock.api.service.impl;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockInterfaceEntity;
import cn.daqi.mock.api.entity.MockProjectEntity;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;
import cn.daqi.mock.api.mapper.MockInterfaceMapper;
import cn.daqi.mock.api.service.MockInterfaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MockInterfaceServiceImpl implements MockInterfaceService {

    @Resource
    MockInterfaceMapper mockInterfaceMapper;
    @Override
    public RespResult searchMockInterface(InterfaceSearchRequest reqs) {
        PageHelper.startPage(reqs.getCurrent(), reqs.getPageSize());
        PageInfo pageData = new PageInfo(mockInterfaceMapper.selectMockInterface(reqs));
        return RespResult.success(pageData);
    }

    @Override
    public RespResult upsertMockInterface() {
        return null;
    }

    @Override
    public RespResult removeMockInterface(Integer id) {
        return null;
    }
}
