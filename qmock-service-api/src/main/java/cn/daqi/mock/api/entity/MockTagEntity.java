package cn.daqi.mock.api.entity;

import lombok.Data;

/**
 * @ 作  者: Mega Qi
 * @ 公众号: 非典型性程序员
 * @ 博 客：https://blog.csdn.net/zyueqi1987
 * @ Describe: 接口分类管理实体类
 */

@Data
public class MockTagEntity extends BaseEntity{
    private Integer id;
    private Integer projectId;
    private String name;
    private String desc;
    private Integer state;
}
