package com.seven.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.seven.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/27
 * Time:14:35
 */
@Slf4j
@WebFilter("/*")
public class LoginCheckFilter implements Filter {

    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        //获取请求路径
        String uri = request.getRequestURI();
        String[] urls={"/employee/login","/backend/**","/front/**","/common/**","/user/sendMsg","/user/login"};
        boolean flag=check(uri,urls);
        if (flag) {
            filterChain.doFilter(request,response);
            return;
        }
        //4-2、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("EmployeeEntity")!=null) {
            filterChain.doFilter(request,response);
            return;
        }
        R notlogin = R.error("NOTLOGIN");
        String jsonString = JSON.toJSONString(notlogin);
        response.getWriter().write(jsonString);
    }

    private boolean check(String uri, String[] urls) {
        for (String url : urls) {
            if (antPathMatcher.matchStart(url,uri)) {
                return true;
            }
        }
        return false;
    }


}
