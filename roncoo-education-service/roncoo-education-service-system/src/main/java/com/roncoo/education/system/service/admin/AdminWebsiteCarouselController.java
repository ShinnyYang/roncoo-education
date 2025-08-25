package com.roncoo.education.system.service.admin;

import com.roncoo.education.common.log.SysLog;
import com.roncoo.education.common.log.SysLogCache;
import com.roncoo.education.common.base.page.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.base.SortReq;
import com.roncoo.education.system.service.admin.biz.AdminWebsiteCarouselBiz;
import com.roncoo.education.system.service.admin.req.AdminWebsiteCarouselEditReq;
import com.roncoo.education.system.service.admin.req.AdminWebsiteCarouselPageReq;
import com.roncoo.education.system.service.admin.req.AdminWebsiteCarouselSaveReq;
import com.roncoo.education.system.service.admin.resp.AdminWebsiteCarouselPageResp;
import com.roncoo.education.system.service.admin.resp.AdminWebsiteCarouselViewResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * ADMIN-广告信息
 *
 * @author wujing
 */
@Api(tags = "admin-广告信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/admin/website/carousel")
public class AdminWebsiteCarouselController {

    @NotNull
    private final AdminWebsiteCarouselBiz biz;

    @ApiOperation(value = "广告信息分页", notes = "广告信息分页")
    @PostMapping(value = "/page")
    public Result<Page<AdminWebsiteCarouselPageResp>> page(@RequestBody AdminWebsiteCarouselPageReq req) {
        return biz.page(req);
    }

    @ApiOperation(value = "广告信息添加", notes = "广告信息添加")
    @SysLog(value = "广告信息添加")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody @Valid AdminWebsiteCarouselSaveReq req) {
        return biz.save(req);
    }

    @ApiOperation(value = "广告信息查看", notes = "广告信息查看")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLogCache
    @GetMapping(value = "/view")
    public Result<AdminWebsiteCarouselViewResp> view(@RequestParam Long id) {
        return biz.view(id);
    }

    @ApiOperation(value = "广告信息修改", notes = "广告信息修改")
    @SysLog(value = "广告信息修改")
    @PutMapping(value = "/edit")
    public Result<String> edit(@RequestBody @Valid AdminWebsiteCarouselEditReq req) {
        return biz.edit(req);
    }

    @ApiOperation(value = "广告信息删除", notes = "广告信息删除")
    @ApiImplicitParam(name = "id", value = "主键ID", dataTypeClass = Long.class, paramType = "query", required = true)
    @SysLog(value = "广告信息删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam Long id) {
        return biz.delete(id);
    }

    @ApiOperation(value = "排序", notes = "排序")
    @SysLog(value = "排序")
    @PutMapping(value = "/sort")
    public Result<Integer> sort(@RequestBody List<SortReq> req) {
        return Result.success(biz.sort(req, "WebsiteCarousel"));
    }
}
