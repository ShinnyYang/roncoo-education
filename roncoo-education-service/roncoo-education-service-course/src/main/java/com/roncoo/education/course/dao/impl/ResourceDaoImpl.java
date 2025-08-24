package com.roncoo.education.course.dao.impl;

import cn.hutool.core.collection.CollUtil;
import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.PageUtil;
import com.roncoo.education.common.tools.IdWorker;
import com.roncoo.education.course.dao.ResourceDao;
import com.roncoo.education.course.dao.impl.mapper.ResourceMapper;
import com.roncoo.education.course.dao.impl.mapper.entity.Resource;
import com.roncoo.education.course.dao.impl.mapper.entity.ResourceExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 课程视频信息 服务实现类
 *
 * @author wujing
 * @date 2022-09-02
 */
@Repository
@RequiredArgsConstructor
public class ResourceDaoImpl implements ResourceDao {

    @NotNull
    private final ResourceMapper mapper;


    @NotNull
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int save(Resource record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
        return this.mapper.insertSelective(record);
    }

    @Override
    public int deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateById(Resource record) {
        record.setGmtCreate(null);
        record.setGmtModified(null);
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Resource getById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<Resource> page(int pageCurrent, int pageSize, ResourceExample example) {
        int count = this.mapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<>(count, totalPage, pageCurrent, pageSize, this.mapper.selectByExample(example));
    }

    @Override
    public List<Resource> listByExample(ResourceExample example) {
        return this.mapper.selectByExample(example);
    }

    @Override
    public int countByExample(ResourceExample example) {
        return this.mapper.countByExample(example);
    }

    @Override
    public Resource getByVideoVid(String videoVid) {
        ResourceExample example = new ResourceExample();
        example.createCriteria().andVideoVidEqualTo(videoVid);
        List<Resource> resourceList = this.mapper.selectByExample(example);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList.get(0);
        }
        return null;
    }

    @Override
    public List<Resource> listByIds(List<Long> ids) {
        ResourceExample example = new ResourceExample();
        example.createCriteria().andIdIn(ids);
        return this.mapper.selectByExample(example);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        ResourceExample example = new ResourceExample();
        example.createCriteria().andIdIn(ids);
        return this.mapper.deleteByExample(example);
    }

    @Override
    public int updateByBatchIds(List<Resource> resources) {
        String sql = "update resource set category_id = :categoryId where id = :id";
        return this.namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(resources)).length;
    }
}
