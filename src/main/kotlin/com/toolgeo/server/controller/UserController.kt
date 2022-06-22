package com.toolgeo.server.controller;

import com.toolgeo.server.service.IUserService
import com.toolgeo.server.util.result.Result
import com.toolgeo.server.util.result.ResultUtil
import org.springframework.web.bind.annotation.GetMapping
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
@RequestMapping("/server/user")
class UserController {
    @Resource
    lateinit var iUserService: IUserService

}
