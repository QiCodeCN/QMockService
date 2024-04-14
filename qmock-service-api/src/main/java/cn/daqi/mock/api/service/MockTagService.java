package cn.daqi.mock.api.service;

import cn.daqi.mock.api.commons.RespResult;
import cn.daqi.mock.api.entity.requests.MockProjectRequest;
import cn.daqi.mock.api.entity.requests.MockTagRequest;

/**
 * @ 作  者: Qi
 * @ 公众号: 非典型性程序员
 * @ 博 客：https://blog.csdn.net/zyueqi1987
 * @ 描 述: 分类接口服务
 */


public interface MockTagService {

    RespResult selectTagList();

    RespResult selectTagInfo(Integer tagId);

    RespResult saveTag(MockTagRequest mockTagRequest);

    RespResult removeTag(Integer id);
}
