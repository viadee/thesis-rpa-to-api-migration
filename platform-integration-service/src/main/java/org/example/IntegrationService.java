package org.example;

import org.example.worker.GenericApiWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@SpringBootApplication
@PropertySources(value = {
        @PropertySource("classpath:application.yml"),
        @PropertySource("classpath:uipath.yml")
})
@EnableScheduling
@ConfigurationPropertiesScan
public class IntegrationService {
    private static final Logger LOG = LoggerFactory.getLogger(IntegrationService.class);
    @Autowired
    protected GenericApiWorker genericApiWorker;

    public static void main(String... args) {
        SpringApplication.run(IntegrationService.class, args);
    }

    @PreDestroy
    public void teardown() {
        if(this.genericApiWorker != null){
            LOG.info("---- Stopping API Task Listener");
            this.genericApiWorker.stop();
            LOG.info("API Task Listener stopped ----");
        }
    }
}