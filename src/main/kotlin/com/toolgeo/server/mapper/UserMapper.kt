package com.toolgeo.server.mapper;

import com.toolgeo.server.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@Mapper
interface UserMapper : BaseMapper<User>
