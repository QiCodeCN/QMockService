package cn.daqi.mock.api.service;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.MockProjectRequest;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 项目管理服务对外interface
 */

public interface MockProjectService {

    RespResult selectMockProjectList();

    RespResult searchMockProject(String name, int current, int pageSize);

    RespResult saveMockProject(MockProjectRequest mockProject);

    RespResult removeMockProject(Integer id);
}
