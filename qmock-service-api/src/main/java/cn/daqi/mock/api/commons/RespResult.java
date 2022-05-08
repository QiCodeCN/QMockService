package cn.daqi.mock.api.commons;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe: 符合 Ant Design Pro 统一规范返回
 */
@Data
public class RespResult implements Serializable {

    private static final long serialVersionUID = 1L;

    // 请求是否成功 true / false
    private Boolean success;

    // 实际返回的数据
    private Object data;

    // 错误编码
    private int errorCode;

    // 错误信息
    private String errorMessage;

    // 提示类型： 0 silent; 1 message.warn; 2 message.error; 4 notification; 9 page
    private int showType = 0;

    // 分页时候用的总数
    private long total;

    // 枚举通用赋值方法
    public void setResultCode(RespCode respCode){
        this.success = respCode.success();
        this.errorCode = respCode.errorCode();
        this.errorMessage = respCode.errorMessage();
        this.showType = respCode.showType();
    }

    // 默认响应成功
    public static RespResult success() {
        RespResult respResult = new RespResult();
        respResult.setResultCode(RespCode.SUCCESS);
        return respResult;
    }

    // 带返回data响应成功
    public static RespResult success(Object data) {
        RespResult respResult = new RespResult();
        respResult.setResultCode(RespCode.SUCCESS);
        respResult.setData(data);
        return respResult;
    }

    // 分页数据成功响应
    public static RespResult success(PageInfo pageData) {
        RespResult respResult = new RespResult();
        respResult.setResultCode(RespCode.SUCCESS);
        respResult.setData(pageData.getList());
        respResult.setTotal(pageData.getTotal());
        return respResult;
    }

    // 根据RespCode枚举失败返回
    public static RespResult failure(RespCode respCode){
        RespResult respResult = new RespResult();
        respResult.setResultCode(respCode);
        return respResult;
    }
}
