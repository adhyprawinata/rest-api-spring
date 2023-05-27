package com.restapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Enumeration;
import java.util.Collection;

public class LoggingInterceptor implements HandlerInterceptor {
    private static final String LOG_FILE_PATH = "logfile.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Do nothing before handling the request
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        logRequestAndResponse(request, response);
    }

    private void logRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> logEntry = new LinkedHashMap<>();
        logEntry.put("timestamp", DATE_FORMAT.format(new Date()));
        logEntry.put("request", getRequestDetails(request));
        logEntry.put("response", getResponseDetails(response));

        String jsonLogEntry = convertToJson(logEntry);
        writeLogToFile(jsonLogEntry);
    }

    private Map<String, Object> getRequestDetails(HttpServletRequest request) {
        Map<String, Object> requestDetails = new LinkedHashMap<>();
        requestDetails.put("method", request.getMethod());
        requestDetails.put("url", request.getRequestURL().toString());
        requestDetails.put("headers", getRequestHeaders(request));
    
        // Add more request details as needed
    
        return requestDetails;
    }
    
    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }
    
    private Map<String, Object> getResponseDetails(HttpServletResponse response) throws IOException {
        Map<String, Object> responseDetails = new LinkedHashMap<>();
        responseDetails.put("status", response.getStatus());
        responseDetails.put("headers", getResponseHeaders(response));
        responseDetails.put("body", getResponseBody(response));

        // Add more response details as needed

        return responseDetails;
    }
    
    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headers = new LinkedHashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            String headerValue = response.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    private String getResponseBody(HttpServletResponse response) throws IOException {
        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper cachingResponseWrapper = (ContentCachingResponseWrapper) response;
            byte[] responseBytes = cachingResponseWrapper.getContentAsByteArray();
            if (responseBytes.length > 0) {
                String responseBody = new String(responseBytes, 0, responseBytes.length, cachingResponseWrapper.getCharacterEncoding());
                cachingResponseWrapper.copyBodyToResponse(); // Restore the response body
                return responseBody;
            }
        }
        return null;
    }

    private String convertToJson(Map<String, Object> data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }

    private void writeLogToFile(String logEntry) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE_PATH, true)))) {
            writer.println(logEntry);
        }
    }
}