package com.hazelcast.demo.service;


import com.hazelcast.demo.model.JobDto;
import com.hazelcast.demo.repository.JobRepository;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

@Service
public class CacheService {

    private static final Logger LOGGER = Loggers.getLogger(CacheService.class);

    private final IMap<String, JobDto> userCache;
    private final JobRepository jobRepository;

    public CacheService(IMap<String, JobDto> userCache, JobRepository jobRepository) {
        this.userCache = userCache;
        this.jobRepository = jobRepository;
    }

/*
This is the code  to retrieve and update the value of a key in a map synchronously,
which blocks the calling thread.
 */
//
//    public Mono<JobDto> getJobProfileFromCache(String id) {
//
//        if (userCache.containsKey(id)) {
//            return Mono.just(userCache.get(id))
//                    .doOnNext(p-> LOGGER.info("Employee with id " + p.getJobId() + " found in cache"));
//        } else {
//            return jobRepository.findByjobId(id)
//                    .doOnNext(data -> userCache.put(id, data))
//                    .doOnNext(p -> LOGGER.info("Employee with id " + p.getJobId() + " set in cache"));
//        }
//    }
//
//    public Mono<JobDto> addUser(JobDto jobDto) {
//
//        return jobRepository.insert(jobDto)
//                // Store user in cache
//                .doOnSuccess(v -> userCache.put(jobDto.getJobId(), jobDto))
//                .doOnNext(p -> LOGGER.info("Employee with id " + p.getJobId() + " set in cache and database"));
//    }


/*
This  is the code to retrieve and update the value of a key in a map asynchronously,
without blocking the calling thread.
 */
    public Mono<JobDto> getJobProfileFromCache(String id) {

        Mono<JobDto> result = getUserFromCache(id);
        return   result
                .switchIfEmpty(getUserFromDB(id))
                .flatMap(user -> saveUserToCache(user));
    }

    public Mono<JobDto> addUser(JobDto jobDto) {
        return jobRepository.save(jobDto)
                .flatMap(jobDto1 -> saveUserToCache(jobDto));
    }

    private Mono<JobDto> getUserFromCache(String id) {

        return Mono.fromCompletionStage(userCache.getAsync(id));
        //return jobDto;
    }


    private Mono<? extends JobDto> saveUserToCache(JobDto user) {
        userCache.setAsync(user.getJobId(), user);
        return Mono.just(user);
    }

    private Mono<JobDto> getUserFromDB(String id) {
        return jobRepository.findByjobId(id);
    }



}
