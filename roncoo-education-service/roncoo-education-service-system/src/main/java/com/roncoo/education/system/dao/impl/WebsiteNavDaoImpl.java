package com.roncoo.education.system.dao.impl;

import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.PageUtil;
import com.roncoo.education.common.tools.IdWorker;
import com.roncoo.education.system.dao.WebsiteNavDao;
import com.roncoo.education.system.dao.impl.mapper.WebsiteNavMapper;
import com.roncoo.education.system.dao.impl.mapper.entity.WebsiteNav;
import com.roncoo.education.system.dao.impl.mapper.entity.WebsiteNavExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WebsiteNavDaoImpl implements WebsiteNavDao {

    @NotNull
    private final WebsiteNavMapper websiteNavMapper;

    @Override
    public int save(WebsiteNav record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
        return this.websiteNavMapper.insertSelective(record);
    }

    @Override
    public int deleteById(Long id) {
        return this.websiteNavMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateById(WebsiteNav record) {
        return this.websiteNavMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public WebsiteNav getById(Long id) {
        return this.websiteNavMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<WebsiteNav> page(int pageCurrent, int pageSize, WebsiteNavExample example) {
        int count = this.websiteNavMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<WebsiteNav>(count, totalPage, pageCurrent, pageSize, this.websiteNavMapper.selectByExample(example));
    }

    @Override
    public List<WebsiteNav> listByStatusId(Integer statusId) {
        WebsiteNavExample example = new WebsiteNavExample();
        example.createCriteria().andStatusIdEqualTo(statusId);
        example.setOrderByClause(" sort asc, id desc ");
        return this.websiteNavMapper.selectByExample(example);
    }

}
