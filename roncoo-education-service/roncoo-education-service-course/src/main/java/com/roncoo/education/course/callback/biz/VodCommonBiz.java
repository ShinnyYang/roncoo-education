package com.roncoo.education.course.callback.biz;

import cn.hutool.core.util.ObjectUtil;
import com.roncoo.education.common.base.BaseBiz;
import com.roncoo.education.common.video.VodUtil;
import com.roncoo.education.common.video.resp.VodInfoResp;
import com.roncoo.education.course.dao.ResourceDao;
import com.roncoo.education.course.dao.impl.mapper.entity.Resource;
import com.roncoo.education.system.feign.interfaces.vo.VideoConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;

/**
 * 课程分类
 *
 * @author wujing
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VodCommonBiz extends BaseBiz {

    @NotNull
    private final ResourceDao resourceDao;

    public void completeUpload(String videoId, VideoConfig videoConfig) {
        // 视频审核完成处理
        VodInfoResp videoResponse = VodUtil.getVideoInfo(videoConfig, videoId);
        if (ObjectUtil.isNotNull(videoResponse)) {
            Resource resource = resourceDao.getByVideoVid(videoId);
            if (ObjectUtil.isNotEmpty(resource)) {
                resource.setVideoLength(videoResponse.getDuration());
                resource.setVideoStatus(videoResponse.getVideoStatusEnum().getCode());
                resourceDao.updateById(resource);
            }
        }
    }
}
