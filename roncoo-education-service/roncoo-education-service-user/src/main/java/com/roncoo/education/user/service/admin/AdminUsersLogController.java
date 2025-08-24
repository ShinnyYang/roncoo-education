package com.roncoo.education.user.service.admin;

import com.roncoo.education.common.annotation.SysLog;
import com.roncoo.education.common.annotation.SysLogCache;
import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.user.service.admin.biz.AdminUsersLogBiz;
import com.roncoo.education.user.service.admin.req.AdminUsersLogEditReq;
import com.roncoo.education.user.service.admin.req.AdminUsersLogPageReq;
import com.roncoo.education.user.service.admin.req.AdminUsersLogSaveReq;
import com.roncoo.education.user.service.admin.resp.AdminUsersLogPageResp;
import com.roncoo.education.user.service.admin.resp.AdminUsersLogViewResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import jakarta.validation.constraints.NotNull;

/**
 * ADMIN-用户登录日志
 *
 * @author wujing
 */
@Api(tags = "admin-用户登录日志")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/admin/users/log")
public class AdminUsersLogController {

    @NotNull
    private final AdminUsersLogBiz biz;

    @ApiOperation(value = "用户登录日志分页", notes = "用户登录日志分页")
    @PostMapping(value = "/page")
    public Result<Page<AdminUsersLogPageResp>> page(@RequestBody AdminUsersLogPageReq req) {
        return biz.page(req);
    }

    @ApiOperation(value = "用户登录日志添加", notes = "用户登录日志添加")
    @SysLog(value = "用户登录日志添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Valid AdminUsersLogSaveReq req) {
        return biz.save(req);
    }

    @ApiOperation(value = "用户登录日志查看", notes = "用户登录日志查看")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLogCache
    @GetMapping(value = "/view")
    public Result<AdminUsersLogViewResp> view(@RequestParam Long id) {
        return biz.view(id);
    }

    @ApiOperation(value = "用户登录日志修改", notes = "用户登录日志修改")
    @SysLog(value = "用户登录日志修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody @Valid AdminUsersLogEditReq req) {
        return biz.edit(req);
    }

    @ApiOperation(value = "用户登录日志删除", notes = "用户登录日志删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLog(value = "用户登录日志删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return biz.delete(id);
    }
}
