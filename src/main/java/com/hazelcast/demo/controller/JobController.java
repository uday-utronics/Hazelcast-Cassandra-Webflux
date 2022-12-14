package com.hazelcast.demo.controller;

import com.hazelcast.demo.model.JobDto;
import com.hazelcast.demo.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class JobController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/getJobProfileFromCache/{jobId}")
    Mono<JobDto> findJobProfile(@RequestParam String jobId){
        return cacheService.getJobProfileFromCache(jobId);
    }

    @PostMapping("/users")
    public Mono<JobDto> addUser(@RequestBody JobDto jobDto) {
        return cacheService.addUser(jobDto);
    }
}
