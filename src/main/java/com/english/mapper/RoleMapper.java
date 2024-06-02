package com.english.mapper;

import com.english.entity.Role;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface RoleMapper
{
    public List<Role> selectLimited(QueryCondition queryCondition);

    public List<Role> select();

    public Long count(QueryCondition queryCondition);

    public Role findByName(String name);

    public Role findByKey(String key);

    public Long insert(Role user);

    public Long update(Role user);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}
