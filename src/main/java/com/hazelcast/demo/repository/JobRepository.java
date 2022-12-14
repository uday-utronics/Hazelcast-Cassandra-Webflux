package com.hazelcast.demo.repository;

import com.hazelcast.demo.model.JobDto;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobRepository extends ReactiveCassandraRepository<JobDto,String> {

    Mono<JobDto> findByjobId(String jobId);
}
