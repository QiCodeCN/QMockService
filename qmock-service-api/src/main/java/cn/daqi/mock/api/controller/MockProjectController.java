package cn.daqi.mock.api.controller;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.MockProjectRequest;
import cn.daqi.mock.api.service.MockProjectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ Author: Zhang Qi
 * @ Copyright: 公众号《非典型性程序员》
 * @ Describe: 项目管理接口类
 */
@RestController
@RequestMapping("/api/mock/")
public class MockProjectController {

    @Autowired
    private MockProjectService mockProjectService;

    @GetMapping(value = "/project/list")
    public RespResult getProjectList() {
        return mockProjectService.selectMockProjectList();
    }

    @RequestMapping(value = "/project/search", method = RequestMethod.GET)
    public RespResult searchProject(String name, int current, int pageSize) {
        return mockProjectService.searchMockProject(name, current, pageSize);
    }

    /***
     * 项目增加保存类
     * @param mockProject
     * @return
     */
    @PostMapping(value = "/project/save")
    public RespResult saveProject(@RequestBody MockProjectRequest mockProject) {
        try {
            return mockProjectService.saveMockProject(mockProject);
        }catch (Exception e){
            System.out.println(e);
        }
        return RespResult.failure(RespCode.SYSTEM_ERROR);
    }

    @PostMapping(value = "/project/remove")
    public RespResult removeProject(@Param("id") Integer id){
        return mockProjectService.removeMockProject(id);
    }
}
