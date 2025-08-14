package com.roncoo.education.system.service.api.biz;

import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.core.enums.StatusIdEnum;
import com.roncoo.education.common.tools.BeanUtil;
import com.roncoo.education.system.dao.WebsiteNavDao;
import com.roncoo.education.system.dao.impl.mapper.entity.WebsiteNav;
import com.roncoo.education.system.service.api.resp.ApiWebsiteNavResp;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 站点导航
 *
 * @author wuyun
 */
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"system"})
public class ApiWebsiteNavBiz {

    private final WebsiteNavDao websiteNavDao;

    @Cacheable
    public Result<List<ApiWebsiteNavResp>> list() {
        List<WebsiteNav> list = websiteNavDao.listByStatusId(StatusIdEnum.YES.getCode());
        return Result.success(BeanUtil.copyProperties(list, ApiWebsiteNavResp.class));
    }

}
