package com.tinqinacademy.bffservice.rest.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String MDC_REQUEST_ID_KEY = "requestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String requestId = httpServletRequest.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        //? MDC (Mapped Diagnostic Context)
        MDC.put(MDC_REQUEST_ID_KEY, requestId);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_REQUEST_ID_KEY); //! Clean up after request is complete
        }
    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialize the filter if needed
//    }
//
//    @Override
//    public void destroy() {
//        // Clean up resources if needed
//    }

}