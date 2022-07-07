package com.toolgeo.server.service.impl;

import com.toolgeo.server.entity.IndexClass;
import com.toolgeo.server.mapper.IndexClassMapper;
import com.toolgeo.server.service.IIndexClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengsx
 * @since 2022-06-27
 */
@Service
open class IndexClassServiceImpl : ServiceImpl<IndexClassMapper, IndexClass>(), IIndexClassService {

}
