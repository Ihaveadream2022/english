package com.english.mapper;

import com.english.entity.User;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;

import java.util.List;

public interface UserMapper
{
    /* Select */
    public List<User> selectLimited(QueryCondition queryCondition);

    public Long count(QueryCondition queryCondition);

    public User findByUsername(String username);

    public User findByPhone(String phone);

    public User findByEmail(String email);

    /* Insert */
    public Long insert(User user);

    /* Update */
    public Long update(User user);

    public Long updateAfterLogin(User user);

    public Long updatePassword(User user);

    /* Delete */
    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}