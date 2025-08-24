package com.roncoo.education.course.service.admin;

import com.roncoo.education.common.annotation.SysLog;
import com.roncoo.education.common.annotation.SysLogCache;
import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.admin.biz.AdminCourseChapterBiz;
import com.roncoo.education.course.service.admin.req.*;
import com.roncoo.education.course.service.admin.resp.AdminCourseChapterPageResp;
import com.roncoo.education.course.service.admin.resp.AdminCourseChapterViewResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * ADMIN-章节信息
 *
 * @author wujing
 */
@Api(tags = "admin-章节信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/admin/course/chapter")
public class AdminCourseChapterController {

    @NotNull
    private final AdminCourseChapterBiz biz;

    @ApiOperation(value = "章节信息分页", notes = "章节信息分页")
    @PostMapping(value = "/page")
    public Result<Page<AdminCourseChapterPageResp>> page(@RequestBody AdminCourseChapterPageReq req) {
        return biz.page(req);
    }

    @ApiOperation(value = "章节信息列表", notes = "章节信息列表")
    @PostMapping(value = "/list")
    public Result<List<AdminCourseChapterViewResp>> list(@RequestBody AdminCourseChapterListReq req) {
        return biz.list(req);
    }

    @ApiOperation(value = "章节信息添加", notes = "章节信息添加")
    @SysLog(value = "章节信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Valid AdminCourseChapterSaveReq req) {
        return biz.save(req);
    }

    @ApiOperation(value = "章节信息查看", notes = "章节信息查看")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLogCache
    @GetMapping(value = "/view")
    public Result<AdminCourseChapterViewResp> view(@RequestParam Long id) {
        return biz.view(id);
    }

    @ApiOperation(value = "章节信息修改", notes = "章节信息修改")
    @SysLog(value = "章节信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody @Valid AdminCourseChapterEditReq req) {
        return biz.edit(req);
    }

    @ApiOperation(value = "章节信息删除", notes = "章节信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLog(value = "章节信息删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return biz.delete(id);
    }

    @ApiOperation(value = "排序", notes = "排序")
    @SysLog(value = "排序")
    @PutMapping(value = "/sort")
    public Result<String> sort(@RequestBody @Valid List<AdminCourseChapterSortReq> req) {
        return biz.sort(req);
    }
}
