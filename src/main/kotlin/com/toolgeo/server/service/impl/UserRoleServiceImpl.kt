package com.toolgeo.server.service.impl;

import com.toolgeo.server.entity.UserRole;
import com.toolgeo.server.mapper.UserRoleMapper;
import com.toolgeo.server.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@Service
open class UserRoleServiceImpl : ServiceImpl<UserRoleMapper, UserRole>(), IUserRoleService {

}
