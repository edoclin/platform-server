package com.toolgeo.server.service.impl;

import com.toolgeo.server.entity.User;
import com.toolgeo.server.mapper.UserMapper;
import com.toolgeo.server.service.IUserService;
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
open class UserServiceImpl : ServiceImpl<UserMapper, User>(), IUserService {

}
