package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.Log;
import com.realestate.entity.User;
import com.realestate.entity.enums.LogType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogMapper {

    public Log mapLog(User user, Advert advert, LogType logType){
        return Log.builder()
                .user(user)
                .log(logType)
                .advert(advert)
                .createAt(LocalDateTime.now())
                .build();
    }
}
