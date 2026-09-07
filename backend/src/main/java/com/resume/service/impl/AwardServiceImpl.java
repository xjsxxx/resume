package com.resume.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.resume.entity.Award;
import com.resume.mapper.AwardMapper;
import com.resume.service.AwardService;
import org.springframework.stereotype.Service;

@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements AwardService {
}
