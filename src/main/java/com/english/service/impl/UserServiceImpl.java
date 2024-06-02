package com.english.service.impl;

import com.english.entity.User;
import com.english.exception.ServiceRuntimeException;
import com.english.mapper.UserMapper;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.QueryCondition;
import com.english.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserMapper userMapper;

    public User findByUsername(String username)
    {
        return userMapper.findByUsername(username);
    }

    public HashMap<String, Object> pageList(QueryCondition queryCondition)
    {
        HashMap<String, Object> data = new HashMap<>();

        List<User> list = userMapper.selectLimited(queryCondition);
        Long total = userMapper.count(queryCondition);

        data.put("list", list);
        data.put("total", total);
        data.put("pageSize", queryCondition.getPageSize());
        data.put("pageNo", queryCondition.getPageNo());

        return data;
    }

    @Override
    public boolean usernameExist(User user)
    {
        Long userId = user.getId() == null ? -1L : user.getId();
        User userFind = userMapper.findByUsername(user.getUsername());
        return userFind != null && userFind.getId().longValue() != userId.longValue();
    }

    @Override
    public boolean phoneExist(User user)
    {
        Long userId = user.getId() == null ? -1L : user.getId();
        User userFind = userMapper.findByPhone(user.getPhone());
        return userFind != null && userFind.getId().longValue() != userId.longValue();
    }

    @Override
    public boolean emailExist(User user)
    {
        Long userId = user.getId() == null ? -1L : user.getId();
        User userFind = userMapper.findByEmail(user.getEmail());
        return userFind != null && userFind.getId().longValue() != userId.longValue();
    }

    @Override
    public void isAllowed(User user)
    {
        if (user.getId() != null && user.isAdmin()) {
            throw new ServiceRuntimeException("不允许操作超级管理员用户");
        }
    }

    @Override
    public void isAllowed(Long userId)
    {
        if (User.isAdmin(userId)) {
            throw new ServiceRuntimeException("不允许操作超级管理员用户");
        }
    }

    @Override
    @Transactional
    public Long insert(User user)
    {
        return userMapper.insert(user);
    }

    @Override
    @Transactional
    public Long update(User user)
    {
        isAllowed(user);

        return userMapper.update(user);
    }

    @Override
    @Transactional
    public void updateAfterLogin(User user)
    {
        userMapper.updateAfterLogin(user);
    }

    @Override
    @Transactional
    public Long updatePassword(User user)
    {
        return userMapper.updatePassword(user);
    }

    @Override
    @Transactional
    public Long delete(DeleteRequestBody deleteRequestBody)
    {
        if (deleteRequestBody.getId() != null) {
            isAllowed(deleteRequestBody.getId());

            return userMapper.delete(deleteRequestBody);
        }

        return 0L;
    }

    @Override
    @Transactional
    public Long batchDelete(DeleteRequestBody deleteRequestBody)
    {
        if (deleteRequestBody.getIds() != null && deleteRequestBody.getIds().length > 0) {
            for (Long id: deleteRequestBody.getIds()) {
                isAllowed(id);
            }
            return userMapper.batchDelete(deleteRequestBody);
        }

        return 0L;
    }
}
