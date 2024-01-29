package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.LogAdvert;
import com.realestate.entity.User;
import com.realestate.entity.enums.LogType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogMapper {

    public LogAdvert mapLog(User user, Advert advert, LogType logType){
        return LogAdvert.builder()
                .user(user)
                .log(logType)
                .advert(advert)
                .createAt(LocalDateTime.now())
                .build();
    }
}
