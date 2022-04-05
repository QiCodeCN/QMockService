package cn.daqi.mock.api.commons;

/**
 * @ Author: Zhang Qi
 * @ Copyright: 博客&公众号《大奇测试开发》
 * @ Describe:
 */

public enum RespCode {

    /**
     * 默认成功和系统状态
     * 提示类型： 0 静默silent; 1 警告message.warn; 2 错误message.error; 4 消息notification; 9 跳转page
     * */
    SUCCESS(true, 2000, "成功", 0),
    SYSTEM_ERROR(false, 5000, "系统繁忙，请稍后重试", 2),

    /* 参数错误 1001~1999 */
    PARAMS_WARNING(false, 1001, "参数缺失或为空", 2),

    /* 用户错误 2001~2999 */
    USER_AUTHORITY_FAILURE(false, 2001, "用户名或密码错误", 2);

    /* 其他错误 3001~3999 */

    private Boolean success;
    private Integer errorCode;
    private String errorMessage;
    private Integer showType;

    RespCode (Boolean success, Integer errorCode, String errorMessage, Integer showType) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.showType = showType;
    }

    public Boolean success() {
        return this.success;
    }

    public Integer errorCode() {
        return this.errorCode;
    }

    public String errorMessage() {
        return this.errorMessage;
    }

    public Integer showType() {
        return this.showType;
    }
}
