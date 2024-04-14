package cn.daqi.mock.api.controller;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.InterfaceSearchRequest;
import cn.daqi.mock.api.service.MockInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 公众号《非典型性程序员》
 * @ Describe: 接口管理API
 */
@RestController
@RequestMapping("/api/mock")
public class InterfaceController {

    @Autowired
    MockInterfaceService mockInterfaceService;

    @PostMapping(value = "/interface/list")
    public RespResult getInterfaceList(@RequestBody InterfaceSearchRequest reqs) {
        return mockInterfaceService.searchMockInterface(reqs);
    }

    @PostMapping(value = "/interface/upsert")
    public RespResult upsertInterface() {
        return mockInterfaceService.upsertMockInterface();
    }

    @PostMapping(value = "/interface/remove")
    public RespResult removeInterface(Integer id) {
        return mockInterfaceService.removeMockInterface(id);
    }
}
