package cn.daqi.mock.api.controller;

import cn.daqi.mock.api.commons.RespCode;
import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.MockTagRequest;
import cn.daqi.mock.api.service.MockTagService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ 作  者: Qi
 * @ 公众号: 非典型性程序员
 * @ 博 客：https://blog.csdn.net/zyueqi1987
 * @ 描 述: 接口分类接口
 */

@RestController
@RequestMapping("/api/mock/")
public class MockTagController {

    @Autowired
    private MockTagService mockTagService;

    @GetMapping(value = "/tag/list")
    public RespResult getTagList() {
        return mockTagService.selectTagList();
    }

    @GetMapping(value = "/tag/info")
    public RespResult getTagInfo(Integer tagId) {
        return mockTagService.selectTagInfo(tagId);
    }

    @PostMapping(value = "/tag/save")
    public RespResult saveTag(@RequestBody MockTagRequest mockTag) {
        try {
            return mockTagService.saveTag(mockTag);
        }catch (Exception e){
            System.out.println(e);
        }
        return RespResult.failure(RespCode.SYSTEM_ERROR);
    }

    @PostMapping(value = "/tag/remove")
    public RespResult removeTag(@Param("tagId") Integer tagId){
        return mockTagService.removeTag(tagId);
    }
}
