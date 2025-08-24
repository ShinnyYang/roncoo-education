/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.roncoo.education.user.test.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
public class BaseTest {

    private static final Long USERID = 1L;

    protected MockMvc mvc;

    @Resource
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).defaultRequest(MockMvcRequestBuilders.post("/").accept("application/json;charset=UTF-8").header("userId", USERID).contentType("application/json")).alwaysDo(MockMvcResultHandlers.print()).build();  //构造MockMvc
    }
}
