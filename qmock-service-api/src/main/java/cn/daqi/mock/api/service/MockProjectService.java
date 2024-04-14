package cn.daqi.mock.api.service;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.MockProjectRequest;

/**
 * @ 作  者: Qi
 * @ 公众号: 非典型性程序员
 * @ 博 客：https://blog.csdn.net/zyueqi1987
 * @ 描 述: 项目服务类
 */

public interface MockProjectService {

    RespResult selectMockProjectList();

    RespResult searchMockProject(String name, int current, int pageSize);

    RespResult saveMockProject(MockProjectRequest mockProject);

    RespResult removeMockProject(Integer id);
}
