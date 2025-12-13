package com.eventify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "eventifyAsyncExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("Async-Eventify-");
        executor.setRejectedExecutionHandler((r, exec) -> {
            log.warn("Async task rejected: ActiveThreads={}, QueueSize={}, PoolSize={}, LargestPoolSize={}, TaskCount={}, CompletedTaskCount={}",
                exec.getActiveCount(),
                exec.getQueue().size(),
                exec.getPoolSize(),
                exec.getLargestPoolSize(),
                exec.getTaskCount(),
                exec.getCompletedTaskCount()
            );
            new ThreadPoolExecutor.CallerRunsPolicy().rejectedExecution(r, exec);
        });
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }
}