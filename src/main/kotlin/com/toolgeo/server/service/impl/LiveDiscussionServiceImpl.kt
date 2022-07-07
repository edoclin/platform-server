package com.toolgeo.server.service.impl;

import com.toolgeo.server.entity.LiveDiscussion;
import com.toolgeo.server.mapper.LiveDiscussionMapper;
import com.toolgeo.server.service.ILiveDiscussionService;
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
open class LiveDiscussionServiceImpl : ServiceImpl<LiveDiscussionMapper, LiveDiscussion>(), ILiveDiscussionService {

}
