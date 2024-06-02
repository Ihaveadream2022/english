package com.english.service.impl;

import com.english.entity.Role;
import com.english.mapper.RoleMapper;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleMapper roleMapper;

    public HashMap<String, Object> pageList(QueryCondition queryCondition)
    {
        HashMap<String, Object> data = new HashMap<>();

        List<Role> list = roleMapper.selectLimited(queryCondition);
        Long total = roleMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    public List<Role> list()
    {
        return roleMapper.select();
    }

    public boolean exist(Role role)
    {
        Long roleId = role.getId() == null ? -1L : role.getId();
        Role roleFind = roleMapper.findByKey(role.getKey());
        return roleFind != null && roleFind.getId().longValue() != roleId.longValue();
    }

    public boolean existName(Role role)
    {
        Long roleId = role.getId() == null ? -1L : role.getId();
        Role roleFind = roleMapper.findByName(role.getName());
        return roleFind != null && roleFind.getId().longValue() != roleId.longValue();
    }

    public Long insert(Role role)
    {
        return roleMapper.insert(role);
    }

    public Long update(Role role)
    {
        return roleMapper.update(role);
    }

    public Long delete(DeleteRequestBody deleteRequestBody)
    {
        if (deleteRequestBody.getId() != null) {
            return roleMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    public Long batchDelete(DeleteRequestBody deleteRequestBody)
    {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            return roleMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }
}
