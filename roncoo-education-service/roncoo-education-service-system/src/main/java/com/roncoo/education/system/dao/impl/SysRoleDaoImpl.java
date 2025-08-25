package com.roncoo.education.system.dao.impl;

import com.roncoo.education.common.base.page.Page;
import com.roncoo.education.common.base.page.PageUtil;
import com.roncoo.education.common.tools.IdWorker;
import com.roncoo.education.system.dao.SysRoleDao;
import com.roncoo.education.system.dao.impl.mapper.SysRoleMapper;
import com.roncoo.education.system.dao.impl.mapper.entity.SysRole;
import com.roncoo.education.system.dao.impl.mapper.entity.SysRoleExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SysRoleDaoImpl implements SysRoleDao {
    @NotNull
    private final SysRoleMapper sysRoleMapper;

    @Override
    public int save(SysRole record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
        return this.sysRoleMapper.insertSelective(record);
    }

    @Override
    public int deleteById(Long id) {
        return this.sysRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateById(SysRole record) {
        return this.sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByExampleSelective(SysRole record, SysRoleExample example) {
        return this.sysRoleMapper.updateByExampleSelective(record, example);
    }

    @Override
    public SysRole getById(Long id) {
        return this.sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<SysRole> page(int pageCurrent, int pageSize, SysRoleExample example) {
        int count = this.sysRoleMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<SysRole>(count, totalPage, pageCurrent, pageSize, this.sysRoleMapper.selectByExample(example));
    }

    @Override
    public List<SysRole> listByIds(List<Long> roleIdList) {
        SysRoleExample example = new SysRoleExample();
        example.createCriteria().andIdIn(roleIdList);
        example.setOrderByClause("sort asc, id desc");
        return this.sysRoleMapper.selectByExample(example);
    }
}
