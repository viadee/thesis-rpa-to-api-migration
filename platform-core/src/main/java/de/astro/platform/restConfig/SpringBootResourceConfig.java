package de.astro.platform.restConfig;

import de.astro.platform.web.controller.CriteriaWeightsRestController;
import de.astro.platform.web.controller.ProcessInstanceDataRestController;
import de.astro.platform.web.controller.ProcessOverviewRestController;
import de.astro.platform.web.controller.RPABotRestController;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/rest-api")
public class SpringBootResourceConfig extends CamundaJerseyResourceConfig {
    @Override
    protected void registerAdditionalResources() {
        this.register(ProcessOverviewRestController.class);
        this.register(ProcessInstanceDataRestController.class);
        this.register(RPABotRestController.class);
        this.register(CriteriaWeightsRestController.class);
    }

    @Bean
    public FilterRegistrationBean processCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8081/");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
