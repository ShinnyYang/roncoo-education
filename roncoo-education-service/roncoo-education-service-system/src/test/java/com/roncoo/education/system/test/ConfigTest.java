package com.roncoo.education.system.test;

import com.roncoo.education.system.service.admin.biz.AdminSysConfigBiz;
import com.roncoo.education.system.service.admin.req.AdminSysConfigListReq;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

public class ConfigTest extends BaseTest {

    @Resource
    private AdminSysConfigBiz adminSysConfigBiz;

    @Test
    public void test() {
        AdminSysConfigListReq req = new AdminSysConfigListReq();
        req.setConfigType(4);
        adminSysConfigBiz.list(req);
    }
}
