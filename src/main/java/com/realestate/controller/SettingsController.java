package com.realestate.controller;

import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController
{
    private final SettingsService settingsService;

    @PostMapping("/db-reset")
    public ResponseMessage dataBaseReset()
    {
        return settingsService.dataBaseReset();
    }
}
