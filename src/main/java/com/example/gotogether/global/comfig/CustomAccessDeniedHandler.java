package com.example.gotogether.global.comfig;

import com.example.gotogether.auth.exception.ErrorCode;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setErrorResponse(response,accessDeniedException.getMessage());
    }
    public void setErrorResponse(HttpServletResponse response,String message) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", HttpStatus.FORBIDDEN);
        responseJson.put("code", HttpStatus.FORBIDDEN.value());
        responseJson.put("message", message);
        response.getWriter().print(responseJson);
    }
}
