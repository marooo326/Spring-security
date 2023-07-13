package com.jwt.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터3");
        HttpServletRequest req =  (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        String headerAuth = req.getHeader("Authorization");

        if(req.getMethod().equals("POST")){
            System.out.println("Post 요청");
            System.out.println(headerAuth);
            if(headerAuth.equals("cos")){
                chain.doFilter(req,res);
            }else{
                PrintWriter writer = res.getWriter();
                writer.println("No Auth");
            }
        }

    }
}
