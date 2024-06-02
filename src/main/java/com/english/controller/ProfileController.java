package com.english.controller;

import com.english.entity.User;
import com.english.exception.ServiceRuntimeException;
import com.english.model.JsonResponse;
import com.english.model.LoginUser;
import com.english.model.request.UpdatePasswordRequestBody;
import com.english.service.SessionService;
import com.english.service.UserService;
import com.english.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController
{
    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @PutMapping("/updatePassword")
    public JsonResponse updatePassword(@Validated @RequestBody UpdatePasswordRequestBody updatePasswordRequestBody)
    {
        String oldPassword = updatePasswordRequestBody.getOldPassword();
        String newPassword = updatePasswordRequestBody.getNewPassword();

        LoginUser loginUser = SecurityUtil.getLoginUser();
        User user = loginUser.getUser();
        if (!SecurityUtil.matchesPassword(oldPassword, user.getPassword())) {
            throw new ServiceRuntimeException("Old password is wrong.");
        }
        String encodedNewPassword = SecurityUtil.encryptPassword(newPassword);
        user.setPassword(encodedNewPassword);

        Long affectRow = userService.updatePassword(user);
        if (affectRow > 0) {
            loginUser.setUser(user);
            sessionService.updateLoginUser(loginUser);
            return JsonResponse.success();
        }

        return JsonResponse.error();
    }
}
