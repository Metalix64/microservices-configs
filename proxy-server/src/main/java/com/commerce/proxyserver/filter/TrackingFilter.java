package com.commerce.proxyserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TrackingFilter extends ZuulFilter {
    private static final Logger logger =
            (Logger) LoggerFactory.getLogger(TrackingFilter.class);
    @Autowired
    private FilterUtil filterUtil;
    @Override
    public boolean shouldFilter() {
        return FilterUtil.SHOULD_FILTER;
    }
    @Override
    public String filterType() {
        return FilterUtil.FILTER_TYPE_PRE;
    }
    @Override
    public int filterOrder() {
        return FilterUtil.FILTER_ORDER;
    }
    @Override
    public Object run() throws ZuulException {
        filterUtil.setTransactionId();
        logger.info("création d'ID de la requête :" +
                filterUtil.getTransactionId());
        return null;
    }
}
