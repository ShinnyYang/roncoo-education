/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.roncoo.education.common.core.base;

/**
 * 常量工具类
 *
 * @author wujing
 */
public final class Constants {

    private Constants() {
    }

    public static final String ADMIN = "admin";
    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";

    /**
     * session有效期，单位：分钟
     */
    public final static int SESSIONTIME = 40;

    public interface RedisPre {
        String PRIVATEKEY = "private:key:";
        String ADMIN_APIS = "admin:apis:";
        /**
         * 验证码
         */
        String VER_CODE = "ver:code:";
        /**
         * 学习进度
         */
        String USER_STUDY = "user:study:";
        /**
         * 资源
         */
        String RESOURCE = "resource:";
        /**
         * 学习进度
         */
        String PROGRESS = "progress:";
        /**
         * 短信验证码
         */
        String CODE = "code:";
        String CODE_STAT = "code:stat:";
        /**
         * 微信用户信息
         */
        String WX_USER = "wx:user:";
    }

}
