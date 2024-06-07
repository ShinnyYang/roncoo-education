package com.roncoo.education.system.service.admin;

import com.roncoo.education.common.annotation.SysLog;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.service.BaseController;
import com.roncoo.education.system.service.biz.UploadCommonBiz;
import com.roncoo.education.system.service.biz.resp.UploadDocResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;

/**
 * 上传接口
 *
 * @author wuyun
 */
@Api(tags = "admin-上传接口")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/system/admin/upload")
public class AdminUploadController extends BaseController {

    @NotNull
    private final UploadCommonBiz biz;

    @ApiOperation(value = "上传图片", notes = "服务端上传图片接口，只支持图片格式")
    @ApiImplicitParam(name = "picFile", value = "图片文件", dataType = "File", dataTypeClass = File.class, paramType = "query", required = true)
    @SysLog(value = "上传图片")
    @PostMapping(value = "/pic")
    public Result<String> uploadPic(@RequestParam(value = "picFile", required = false) MultipartFile picFile) {
        return biz.uploadPic(picFile);
    }

    @ApiOperation(value = "上传文档", notes = "服务端上传文档接口，只支持文档格式")
    @ApiImplicitParam(name = "docFile", value = "文档文件", dataType = "File", dataTypeClass = File.class, paramType = "query", required = true)
    @SysLog(value = "上传文档")
    @PostMapping(value = "/doc")
    public Result<UploadDocResp> uploadDoc(@RequestParam(name = "docFile", required = false) MultipartFile docFile) {
        return biz.uploadDoc(docFile, false);
    }

    @ApiOperation(value = "上传app", notes = "服务端上传app接口，该接口只支持app格式")
    @ApiImplicitParam(name = "appFile", value = "app文件", dataType = "File", dataTypeClass = File.class, paramType = "query", required = true)
    @SysLog(value = "上传app")
    @PostMapping(value = "/app")
    public Result<UploadDocResp> uploadApp(@RequestParam(name = "appFile", required = false) MultipartFile appFile) {
        return biz.uploadDoc(appFile, true);
    }

}
