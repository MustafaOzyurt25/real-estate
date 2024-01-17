package com.realestate.service;

import com.realestate.entity.LogAdvert;
import com.realestate.repository.LogsAdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsService
{

    private final LogsAdvertRepository logsAdvertRepository;
    public void deleteByUserId(Long userId)
    {

        logsAdvertRepository.deleteByUserId(userId);
    }

    public void save(LogAdvert logAdvert) {
        logsAdvertRepository.save(logAdvert);
    }
}
