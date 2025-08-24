package com.roncoo.education.course.service.api;

import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.base.BaseController;
import com.roncoo.education.course.service.api.biz.ApiZoneBiz;
import com.roncoo.education.course.service.api.resp.ApiZoneResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 专区课程关联表
 *
 * @author wuyun
 */
@Api(tags = "api-分区")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/course/api/zone")
public class ApiZoneController extends BaseController {

    @NotNull
    private final ApiZoneBiz biz;

    /**
     * 专区课程分页列表接口
     *
     * @param bo
     * @return
     */
    @ApiOperation(value = "专区接口", notes = "列出专区课程列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<ApiZoneResp>> list() {
        return biz.list();
    }

}
