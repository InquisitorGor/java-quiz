package ru.ayubdzhanov.javaquiz.interceptors;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getMethod() + " REQUEST " + request.getRequestURI() + getRequestParams(request.getParameterMap()));
        return super.preHandle(request, response, handler);
    }

    private String getRequestParams(Map<String, String[]> requestParams) {
        return requestParams.entrySet().stream().map(entry -> " value " + entry.getKey() + ":: params : " + Arrays.toString(entry.getValue())).collect(Collectors.joining("\n"));
    }
}
