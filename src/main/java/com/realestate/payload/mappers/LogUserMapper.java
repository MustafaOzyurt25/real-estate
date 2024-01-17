package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.LogAdvert;
import com.realestate.entity.LogUser;
import com.realestate.entity.User;
import com.realestate.entity.enums.LogType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogUserMapper {

    public LogUser mapLog(User user, LogType logType){
        return LogUser.builder()
                .user(user)
                .log(logType)
                .createAt(LocalDateTime.now())
                .build();
    }

}
