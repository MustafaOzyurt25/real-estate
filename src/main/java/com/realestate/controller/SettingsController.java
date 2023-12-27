package com.realestate.controller;

import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController
{
    private final SettingsService settingsService;

    // X01 endpointi
    @DeleteMapping("/db-reset")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseMessage dataBaseReset()
    {
        return settingsService.dataBaseReset();
    }
}
