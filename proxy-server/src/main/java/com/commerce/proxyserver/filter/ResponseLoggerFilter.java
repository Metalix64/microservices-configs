package com.commerce.proxyserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ResponseLoggerFilter extends ZuulFilter {
    private static final Logger logger = (Logger) LoggerFactory.
            getLogger(ResponseLoggerFilter.class);
    @Override
    public boolean shouldFilter() {
        return FilterUtil.SHOULD_FILTER;
    }
    @Override
    public String filterType() {
        return FilterUtil.FILTER_TYPE_POST;
    }
    @Override
    public int filterOrder() {
        return FilterUtil.FILTER_ORDER;
    }
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("response status logged " +
                ctx.getResponse().getStatus());
        return null;
    }
}
