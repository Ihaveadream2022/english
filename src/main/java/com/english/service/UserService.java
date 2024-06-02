package com.english.service;

import com.english.entity.User;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import java.util.HashMap;

public interface UserService
{
    public User findByUsername(String username);

    public HashMap<String, Object> pageList(QueryCondition queryCondition);

    public boolean usernameExist(User user);

    public boolean phoneExist(User user);

    public boolean emailExist(User user);

    public void isAllowed(User user);

    public void isAllowed(Long userId);

    public Long insert(User user);

    public Long update(User user);

    public void updateAfterLogin(User user);

    public Long updatePassword(User user);

    public Long delete(DeleteRequestBody deleteRequestBody);

    public Long batchDelete(DeleteRequestBody deleteRequestBody);
}