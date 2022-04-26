package com.gmail.burinigor7.apigatewayservice.filter;

import com.gmail.burinigor7.apigatewayservice.exception.UnauthorisedException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class UnauthorizedExceptionHandlerFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (UnauthorisedException e) {
            ((HttpServletResponse) servletResponse).setStatus(401);
            Writer writer = servletResponse.getWriter();
            writer.write(e.getMessage());
            writer.flush();
        }
    }
}
