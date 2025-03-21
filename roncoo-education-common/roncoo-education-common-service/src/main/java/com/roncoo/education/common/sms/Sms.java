package com.roncoo.education.common.sms;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wujing
 */
@Data
@Accessors(chain = true)
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer smsPlatform;

    // Lkyun短信签名
    private String lkyunSmsSignName;
    private String lkyunSmsAccessKeyId;
    private String lkyunSmsAccessKeySecret;
    private String lkyunSmsAuthCode;
    private String lkyunSmsPurchaseCode;

    // Aliyun短信签名
    private String aliyunSmsSignName;
    private String aliyunSmsAccessKeyId;
    private String aliyunSmsAccessKeySecret;
    private String aliyunSmsAuthCode;
    private String aliyunSmsPurchaseCode;

}
