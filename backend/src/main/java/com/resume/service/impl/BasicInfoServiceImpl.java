package com.resume.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.resume.entity.BasicInfo;
import com.resume.mapper.BasicInfoMapper;
import com.resume.service.BasicInfoService;
import org.springframework.stereotype.Service;

@Service
public class BasicInfoServiceImpl extends ServiceImpl<BasicInfoMapper, BasicInfo> implements BasicInfoService {
}
