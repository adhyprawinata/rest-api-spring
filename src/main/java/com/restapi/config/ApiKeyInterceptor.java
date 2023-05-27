package com.restapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
public class ApiKeyInterceptor implements HandlerInterceptor {
    private static final String API_KEY = "CIMBNiaga";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("x-api-key");

        if (!API_KEY.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
            errorResponse.put("message", "Unauthorized");
            errorResponse.put("data", null);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            PrintWriter writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
            return false;
        }

        return true;
    }
}