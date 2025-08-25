package com.roncoo.education.user.dao.impl;

import com.roncoo.education.common.base.page.Page;
import com.roncoo.education.common.base.page.PageUtil;
import com.roncoo.education.common.tools.IdWorker;
import com.roncoo.education.user.dao.RegionDao;
import com.roncoo.education.user.dao.impl.mapper.RegionMapper;
import com.roncoo.education.user.dao.impl.mapper.entity.Region;
import com.roncoo.education.user.dao.impl.mapper.entity.RegionExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegionDaoImpl implements RegionDao {
    @NotNull
    private final RegionMapper regionMapper;

    @Override
    public int save(Region record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
        return this.regionMapper.insertSelective(record);
    }

    @Override
    public int deleteById(Long id) {
        return this.regionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateById(Region record) {
        return this.regionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Region getById(Long id) {
        return this.regionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<Region> page(int pageCurrent, int pageSize, RegionExample example) {
        int count = this.regionMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<Region>(count, totalPage, pageCurrent, pageSize, this.regionMapper.selectByExample(example));
    }

    @Override
    public List<Region> listByLevel(Integer level) {
        RegionExample example = new RegionExample();
        example.createCriteria().andLevelEqualTo(level);
        return this.regionMapper.selectByExample(example);
    }

    @Override
    public List<Region> listByProvinceId(Integer provinceId) {
        RegionExample example = new RegionExample();
        example.createCriteria().andProvinceIdEqualTo(provinceId);
        return this.regionMapper.selectByExample(example);
    }

    @Override
    public List<Region> listByCityId(Integer cityId) {
        RegionExample example = new RegionExample();
        example.createCriteria().andCityIdEqualTo(cityId);
        return this.regionMapper.selectByExample(example);
    }

}
