package com.toolgeo.server.controller;

import com.toolgeo.server.entity.Role
import com.toolgeo.server.service.IRoleService
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/server/role")
class RoleController {
    @Resource
    lateinit var iRoleService: IRoleService

    @PostMapping("/cms")
    fun postRoleCms(@RequestBody role: Role): ResultBean {
        iRoleService.save(role)
        return ResultUtil.ok()
    }
}
