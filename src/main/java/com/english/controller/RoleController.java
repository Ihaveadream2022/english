package com.english.controller;

import com.english.entity.Role;
import com.english.model.JsonResponse;
import com.english.model.request.DeleteRequestBody;
import com.english.model.request.RoleQueryCondition;
import com.english.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roles")
public class RoleController
{
    @Autowired
    private RoleService roleService;

    /**
     * Roles List
     */
    @GetMapping
    public JsonResponse list(RoleQueryCondition roleQueryCondition)
    {
        Map<String, Object> data = roleService.pageList(roleQueryCondition);

        return JsonResponse.success(data);
    }

    /**
     * Create a role
     */
    @PostMapping
    public JsonResponse insert(@Validated @RequestBody Role role)
    {
        if (role.getKey() != null && role.getKey().trim().length() != 0 && roleService.exist(role)) {
            return JsonResponse.error("标识已存在");
        }
        if (role.getName() != null && role.getName().trim().length() != 0 && roleService.existName(role)) {
            return JsonResponse.error("名称已存在");
        }

        Long rows = roleService.insert(role);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Update a role
     */
    @PutMapping("/{id}")
    public JsonResponse update(@PathVariable Long id, @Validated @RequestBody Role role)
    {
        role.setId(id);

        if (role.getName() != null && role.getName().trim().length() != 0 && roleService.existName(role)) {
            return JsonResponse.error("名称已存在");
        }

        Long rows = roleService.update(role);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Delete a role
     */
    @DeleteMapping( "/{id}")
    public JsonResponse delete(@PathVariable Long id)
    {
        DeleteRequestBody deleteRequestBody = new DeleteRequestBody();
        deleteRequestBody.setId(id);

        Long rows = roleService.delete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Batch Delete roles
     */
    @PostMapping( "/batchDelete")
    public JsonResponse batchDelete(@RequestBody DeleteRequestBody deleteRequestBody)
    {
        Long rows = roleService.batchDelete(deleteRequestBody);

        return rows > 0? JsonResponse.success(): JsonResponse.error();
    }

    /**
     * Selection list
     */
    @GetMapping("/options")
    public JsonResponse selectList()
    {
        List<Role> list = roleService.list();

        return JsonResponse.success(list);
    }
}
