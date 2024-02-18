package cn.daqi.mock.api.service.impl;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.MockProjectEntity;
import cn.daqi.mock.api.entity.requests.MockProjectRequest;
import cn.daqi.mock.api.mapper.MockProjectMapper;
import cn.daqi.mock.api.service.MockProjectService;
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
@Service("MockProjectService")
public class MockProjectServiceImpl implements MockProjectService {

    @Autowired
    MockProjectMapper mockProjectMapper;

    /***
     * 获取项目列表
     * @return Resp
     */
    @Override
    public RespResult selectMockProjectList() {
        List<MockProjectEntity> mockProjectEntityList= mockProjectMapper.selectMockProject();
        return RespResult.success(mockProjectEntityList);
    }


    /**
     * 根据搜索条件搜索项目List
     * name为空的时候按全部条件搜索
     * @param name
     * @return
     */
    @Override
    public RespResult searchMockProject(String name, int current, int pageSize) {
        List<MockProjectEntity> mockProjectEntityList;
        PageHelper.startPage(current, pageSize);

        if (name==null || name.isEmpty()){
            mockProjectEntityList = mockProjectMapper.selectMockProject();
        } else{
            mockProjectEntityList = mockProjectMapper.searchMockProject(name);
        }

        PageInfo pageData = new PageInfo(mockProjectEntityList);
        return RespResult.success(pageData);
    }

    /**
     * 项目添加和保存实现
     * @param mockProject
     * @return
     */
    public RespResult saveMockProject(MockProjectRequest mockProject) {
        MockProjectEntity mockProjectEntity = new MockProjectEntity();
        mockProjectEntity.setName(mockProject.getName());
        mockProjectEntity.setDesc(mockProject.getDesc());
        mockProjectEntity.setType(mockProject.getType());
        mockProjectEntity.setOwner(mockProject.getOwner());

        if(mockProject.getId() == null){
            mockProjectEntity.setCreateUser(mockProject.getOperator());
            mockProjectMapper.insertProject(mockProjectEntity);
        }
        else {
            mockProjectEntity.setId(mockProject.getId());
            mockProjectEntity.setUpdateUser(mockProject.getOperator());
            mockProjectMapper.updateProject(mockProjectEntity);
        }

        return RespResult.success();
    }

    @Override
    public RespResult removeMockProject(Integer id) {

        Boolean markResult = mockProjectMapper.removeProject(id);
        if (markResult){
            return RespResult.success();
        }
        return RespResult.failure(RespCode.DATA_REMOVE_ERROR);
    }
}
