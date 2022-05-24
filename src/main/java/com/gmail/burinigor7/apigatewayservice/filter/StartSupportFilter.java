package com.gmail.burinigor7.apigatewayservice.filter;

import com.gmail.burinigor7.apigatewayservice.sercurity.jwt.JwtUser;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class StartSupportFilter extends ZuulFilter {
    public static final int FILTER_ORDER = 10;
    public static final String FILTER_TYPE = "pre";

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        final String path = "/tech-support/start-support";
        final String methodName = "patch";
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        return request.getRequestURI().contains(path) &&
                request.getMethod().equalsIgnoreCase(methodName);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        String chatId = context.getRequest().getParameter("chatId");
        context.set(FilterConstants.REQUEST_URI_KEY, "/tech-support/start-support/" + chatId);
        context.setRequestQueryParams(Map.of("username", List.of(getCurrentUsername())));
        return null;
    }

    private String getCurrentUsername() {
        return ((JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername();
    }
}
