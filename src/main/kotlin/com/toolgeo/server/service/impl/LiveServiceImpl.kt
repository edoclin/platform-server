package com.toolgeo.server.service.impl;

import com.toolgeo.server.entity.Live;
import com.toolgeo.server.mapper.LiveMapper;
import com.toolgeo.server.service.ILiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengsx
 * @since 2022-07-04
 */
@Service
open class LiveServiceImpl : ServiceImpl<LiveMapper, Live>(), ILiveService {

}
