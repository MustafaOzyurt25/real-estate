package com.realestate.service;

import com.realestate.entity.Log;
import com.realestate.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsService
{

    private final LogsRepository logsRepository;
    public void deleteByUserId(Long userId)
    {

        logsRepository.deleteByUserId(userId);
    }

    public void save(Log log) {
        logsRepository.save(log);
    }
}
