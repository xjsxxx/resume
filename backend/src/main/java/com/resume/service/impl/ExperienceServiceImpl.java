package com.resume.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.resume.entity.Experience;
import com.resume.mapper.ExperienceMapper;
import com.resume.service.ExperienceService;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl extends ServiceImpl<ExperienceMapper, Experience> implements ExperienceService {
}
