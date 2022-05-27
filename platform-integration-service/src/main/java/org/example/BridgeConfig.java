package org.example;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.bridge.rpa.uipath.JobStatusFetcher;
import org.example.bridge.rpa.uipath.auth.AuthToken;
import org.example.bridge.rpa.uipath.auth.Authentication;
import org.example.worker.GenericApiWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;

@Configuration
public class BridgeConfig {
    private static final Logger LOG = LoggerFactory.getLogger(BridgeConfig.class);

    public BridgeConfig() {
    }

    @Bean
    public HttpClient httpClient(){
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30*1000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    @Bean
    public Authentication authentication() {
        return new Authentication();
    }

    @Bean
    public GenericApiWorker getGenericApiWorker() {
        return new GenericApiWorker();
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> getExternalTaskListener() {
        return (event) -> {
            LOG.info("---- Starting API Task Listener");
            this.getGenericApiWorker().start();
            LOG.info("API Task Listener started ----");
        };
    }
    
    @Bean
    public AuthToken authToken(Authentication authentication) throws IOException {
        return authentication.getToken();
    }

    @Bean
    public JobStatusFetcher statusFetcher() {
        return new JobStatusFetcher();
    }

    @Bean
    public TaskSchedulerCustomizer taskSchedulerCustomizer() {
        return (taskScheduler) -> {
            taskScheduler.setAwaitTerminationSeconds(60);
            taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        };
    }
}
