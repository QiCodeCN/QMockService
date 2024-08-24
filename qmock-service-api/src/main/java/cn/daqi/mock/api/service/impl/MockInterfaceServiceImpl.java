package cn.daqi.mock.api.service.impl;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockInterfaceEntity;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;
import cn.daqi.mock.api.mapper.MockInterfaceMapper;
import cn.daqi.mock.api.service.MockInterfaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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
    public RespResult upsertMockInterface(MockInterfaceEntity interfaceEntity) {
        Integer result=0;
        if (interfaceEntity.getId()==null) {
            result = mockInterfaceMapper.insertMockInterface(interfaceEntity);
        } else {
            result = mockInterfaceMapper.updateMockInterface(interfaceEntity);
        }

        if (result==1){
            return RespResult.success();
        } else {
            return RespResult.failure(RespCode.DATA_SAVE_ERROR);
        }
    }

    @Override
    public RespResult removeMockInterface(Integer id) {
        return null;
    }
}
