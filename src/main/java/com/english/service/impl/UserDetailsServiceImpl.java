package com.english.service.impl;

import com.english.constant.UserConstant;
import com.english.entity.User;
import com.english.exception.user.UserNotExistException;
import com.english.exception.user.UserStatusException;
import com.english.model.LoginUser;
import com.english.service.PasswordService;
import com.english.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotExistException();
        }
        if (user.getStatus() == UserConstant.STATUS_FORBIDDEN || user.getStatus() == UserConstant.STATUS_DELETED) {
            throw new UserStatusException();
        }

        passwordService.validate(user);

        return new LoginUser(user);
    }
}
