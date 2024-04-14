package cn.daqi.mock.api.service.impl;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockProjectEntity;
import cn.daqi.mock.api.entity.MockTagEntity;
import cn.daqi.mock.api.entity.requests.MockProjectRequest;
import cn.daqi.mock.api.entity.requests.MockTagRequest;
import cn.daqi.mock.api.mapper.MockProjectMapper;
import cn.daqi.mock.api.mapper.MockTagMapper;
import cn.daqi.mock.api.service.MockProjectService;
import cn.daqi.mock.api.service.MockTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 项目管理服务对外interface实现类
 */
@Service("MockTagService")
public class MockTagServiceImpl implements MockTagService {

    @Autowired
    MockTagMapper mockTagMapper;

    @Override
    public RespResult selectTagList() {
        List<MockTagEntity> mockTagEntities= mockTagMapper.selectMockTag();
        return RespResult.success(mockTagEntities);
    }

    public RespResult selectTagInfo(Integer tagId) {
        MockTagEntity mockTagEntity= mockTagMapper.selectMockTagInfo(tagId);
        return RespResult.success(mockTagEntity);
    }

    @Override
    public RespResult saveTag(MockTagRequest mockTagRequest) {
        MockTagEntity mockTagEntity = new MockTagEntity();
        mockTagEntity.setName(mockTagRequest.getName());
        mockTagEntity.setProjectId(mockTagRequest.getProjectId());
        mockTagEntity.setDesc(mockTagRequest.getDesc());
        mockTagEntity.setCreateUser(mockTagRequest.getOperator());

        if(mockTagRequest.getId() == null){
            mockTagEntity.setCreateUser(mockTagRequest.getOperator());
            mockTagMapper.insertTag(mockTagEntity);
        }
        else {
            mockTagEntity.setId(mockTagRequest.getId());
            mockTagEntity.setUpdateUser(mockTagRequest.getOperator());
            mockTagMapper.updateTag(mockTagEntity);
        }

        return RespResult.success();
    }

    @Override
    public RespResult removeTag(Integer id) {
        Boolean markResult = mockTagMapper.removeTag(id);
        if (markResult){
            return RespResult.success();
        }
        return RespResult.failure(RespCode.DATA_REMOVE_ERROR);
    }
}
