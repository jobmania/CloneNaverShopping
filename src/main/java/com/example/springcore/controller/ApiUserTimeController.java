package com.example.springcore.controller;

import com.example.springcore.apiusetime.ApiUseTime;
import com.example.springcore.apiusetime.ApiUseTimeRepository;
import com.example.springcore.user.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiUserTimeController {
    private final ApiUseTimeRepository apiUseTimeRepository;



    @GetMapping("/api/use/time")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public List<ApiUseTime> getAllApiUseTime(){
        return apiUseTimeRepository.findAll();
    }


}
