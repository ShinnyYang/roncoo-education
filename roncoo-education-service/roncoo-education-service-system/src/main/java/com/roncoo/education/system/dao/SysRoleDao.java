package com.roncoo.education.system.dao;

import com.roncoo.education.common.base.page.Page;
import com.roncoo.education.system.dao.impl.mapper.entity.SysRole;
import com.roncoo.education.system.dao.impl.mapper.entity.SysRoleExample;

import java.util.List;

public interface SysRoleDao {
    int save(SysRole record);

    int deleteById(Long id);

    int updateById(SysRole record);

    int updateByExampleSelective(SysRole record, SysRoleExample example);

    SysRole getById(Long id);

    Page<SysRole> page(int pageCurrent, int pageSize, SysRoleExample example);

    List<SysRole> listByIds(List<Long> roleIdList);
}
