package com.roncoo.education.course.service.admin;

import com.roncoo.education.common.annotation.SysLog;
import com.roncoo.education.common.annotation.SysLogCache;
import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.service.SortReq;
import com.roncoo.education.course.service.admin.biz.AdminCourseChapterPeriodBiz;
import com.roncoo.education.course.service.admin.req.AdminCourseChapterPeriodEditReq;
import com.roncoo.education.course.service.admin.req.AdminCourseChapterPeriodListReq;
import com.roncoo.education.course.service.admin.req.AdminCourseChapterPeriodPageReq;
import com.roncoo.education.course.service.admin.req.AdminCourseChapterPeriodSaveReq;
import com.roncoo.education.course.service.admin.resp.AdminCourseChapterPeriodPageResp;
import com.roncoo.education.course.service.admin.resp.AdminCourseChapterPeriodViewResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * ADMIN-课时信息
 *
 * @author wujing
 */
@Api(tags = "admin-课时信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/admin/course/chapter/period")
public class AdminCourseChapterPeriodController {

    @NotNull
    private final AdminCourseChapterPeriodBiz biz;

    @ApiOperation(value = "课时信息分页", notes = "课时信息分页")
    @PostMapping(value = "/page")
    public Result<Page<AdminCourseChapterPeriodPageResp>> page(@RequestBody AdminCourseChapterPeriodPageReq req) {
        return biz.page(req);
    }

    @ApiOperation(value = "课时信息列表", notes = "课时信息列表")
    @PostMapping(value = "/list")
    public Result<List<AdminCourseChapterPeriodViewResp>> list(@RequestBody AdminCourseChapterPeriodListReq req) {
        return biz.list(req);
    }

    @ApiOperation(value = "课时信息添加", notes = "课时信息添加")
    @SysLog(value = "课时信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Valid AdminCourseChapterPeriodSaveReq req) {
        return biz.save(req);
    }

    @ApiOperation(value = "课时信息查看", notes = "课时信息查看")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLogCache
    @GetMapping(value = "/view")
    public Result<AdminCourseChapterPeriodViewResp> view(@RequestParam Long id) {
        return biz.view(id);
    }

    @ApiOperation(value = "课时信息修改", notes = "课时信息修改")
    @SysLog(value = "课时信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody @Valid AdminCourseChapterPeriodEditReq req) {
        return biz.edit(req);
    }

    @ApiOperation(value = "课时信息删除", notes = "课时信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLog(value = "课时信息删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return biz.delete(id);
    }

    @ApiOperation(value = "排序", notes = "排序")
    @SysLog(value = "排序")
    @PutMapping(value = "/sort")
    public Result<Integer> sort(@RequestBody List<SortReq> req) {
        return Result.success(biz.sort(req, "CourseChapterPeriod"));
    }
}
