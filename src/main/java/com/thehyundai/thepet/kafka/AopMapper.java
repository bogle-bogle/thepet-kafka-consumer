package com.thehyundai.thepet.kafka;

import com.thehyundai.thepet.global.timetrace.AopControllerVO;
import com.thehyundai.thepet.global.timetrace.AopServiceVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AopMapper {

    void saveAOPServiceTable(AopServiceVO aopserviceVO);
    void saveAOPControllerTable(AopControllerVO aopcontrollerVO);

}
