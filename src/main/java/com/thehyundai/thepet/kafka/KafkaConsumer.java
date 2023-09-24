package com.thehyundai.thepet.kafka;

import com.thehyundai.thepet.global.timetrace.AopServiceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final AopMapper aopMapper;

    @KafkaListener(topics = "service_execution_time_log", groupId = "foo")
    public void consume(AopServiceVO vo){
        log.info(vo.toString());
        aopMapper.saveAOPServiceTable(vo);
    }

}