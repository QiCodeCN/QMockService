package cn.daqi.mock.api.entity.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ 作  者: Qi
 * @ 公众号: 非典型性程序员
 * @ 博 客：https://blog.csdn.net/zyueqi1987
 * @ 描 述: 接口分类管理请求类
 */
@Data
public class MockTagRequest {

    private Integer id;

    private Integer projectId;

    private String name;

    private String desc;

    private String operator;
}
