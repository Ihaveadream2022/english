package com.english.controller;

import com.english.entity.User;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.UserQueryCondition;
import com.english.service.UserService;
import com.english.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * List User
     */
    @GetMapping
    public JsonResponse list(UserQueryCondition userQueryCondition)
    {
        Map<String, Object> data = userService.pageList(userQueryCondition);

        return JsonResponse.success(data);
    }

    /**
     * Create User
     */
    @PostMapping
    public JsonResponse create(@Validated @RequestBody User user)
    {
        if (user.getUsername() != null && user.getUsername().trim().length() != 0 && userService.usernameExist(user)) {
            return JsonResponse.error("登录账号已存在");
        }
        if (user.getPhone() != null && user.getPhone().trim().length() != 0 && userService.phoneExist(user)) {
            return JsonResponse.error("手机账号已存在");
        }
        if (user.getEmail() != null && user.getEmail().trim().length() != 0 && userService.emailExist(user)) {
            return JsonResponse.error("邮箱已存在");
        }
        user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));

        Long rows = userService.insert(user);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Edit User
     */
    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody User user)
    {
        user.setId(id);

        if (user.getPhone() != null && user.getPhone().trim().length() != 0 && userService.phoneExist(user)) {
            return JsonResponse.error("手机账号已存在");
        }
        if (user.getEmail() != null && user.getEmail().trim().length() != 0 && userService.emailExist(user)) {
            return JsonResponse.error("邮箱已存在");
        }

        Long rows = userService.update(user);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Delete User
     *
     * @param id
     * @return
     */
    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id)
    {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = userService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Batch Delete
     *
     * @param deleteRequestBody
     * @return
     */
    @PostMapping( "/delete")
    public JsonResponse batchDelete(@RequestBody DeleteRequestBody deleteRequestBody)
    {
        Long rows = userService.batchDelete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }
}
