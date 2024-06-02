package com.english.service;

import com.english.entity.Role;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.HashMap;
import java.util.List;

public interface RoleService
{
    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public List<Role> list();

    public boolean exist(Role role);

    public boolean existName(Role role);

    public Long insert(Role role);

    public Long update(Role role);

    public Long delete(DeleteRequestBody commonDeleteCondition);

    public Long batchDelete(DeleteRequestBody commonDeleteCondition);
}