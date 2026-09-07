package com.resume.common;

/**
 * 统一响应状态码
 */
public interface ResultCode {

    /** 成功 */
    int SUCCESS = 200;

    /** 参数或业务错误 */
    int BAD_REQUEST = 400;

    /** 未认证/登录过期 */
    int UNAUTHORIZED = 401;

    /** 服务端异常 */
    int ERROR = 500;
}
