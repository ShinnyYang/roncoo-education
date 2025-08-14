/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.roncoo.education.user.test.service;

import com.roncoo.education.common.core.enums.PayTypeEnum;
import com.roncoo.education.common.tools.JsonUtil;
import com.roncoo.education.user.service.auth.req.AuthOrderPayReq;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class PayTest extends BaseTest {

    @Test
    public void pay() throws Exception {
        AuthOrderPayReq req = new AuthOrderPayReq();
        req.setPayType(PayTypeEnum.WEIXIN_SCAN.getCode());
        req.setCourseId(1246446673697738753L);
        mvc.perform(MockMvcRequestBuilders.post("/user/auth/order/pay/create").content(JsonUtil.toJsonString(req)));
    }

}
