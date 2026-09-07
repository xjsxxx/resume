package com.resume.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.resume.entity.Education;
import com.resume.mapper.EducationMapper;
import com.resume.service.EducationService;
import org.springframework.stereotype.Service;

@Service
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Education> implements EducationService {
}
