package com.resume.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.common.Result;
import com.resume.common.ResultCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 后台接口登录拦截器：校验请求头 Authorization: Bearer <token>
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final String PREFIX = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 预检请求直接放行（跨域）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String auth = request.getHeader("Authorization");
        if (StringUtils.hasText(auth) && auth.startsWith(PREFIX)) {
            String token = auth.substring(PREFIX.length());
            try {
                Claims claims = jwtUtil.parse(token);
                request.setAttribute("username", claims.getSubject());
                request.setAttribute("nickname", claims.get("nickname", String.class));
                return true;
            } catch (Exception ignore) {
                // token 非法或过期，走下方拦截
            }
        }
        writeUnauthorized(response);
        return false;
    }

    private void writeUnauthorized(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(
                Result.fail(ResultCode.UNAUTHORIZED, "登录已过期，请重新登录")));
    }
}
