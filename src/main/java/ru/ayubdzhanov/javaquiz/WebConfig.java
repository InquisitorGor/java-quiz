package ru.ayubdzhanov.javaquiz;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ayubdzhanov.javaquiz.interceptors.LoggerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor())
            .excludePathPatterns("/webjars/**")
            .excludePathPatterns("/images/**");
    }

}
