/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.common.base.security.inject;

import com.jiuqi.common.base.security.inject.InjectHttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InjectFilter
implements Filter {
    private static Logger logger = LoggerFactory.getLogger(InjectFilter.class);
    private static boolean IS_INCLUDE_RICH_TEXT = false;
    public List<String> excludes = new ArrayList<String>();
    private Boolean xssEnabled;
    private Boolean sqlEnabled;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp;
        HttpServletRequest req;
        if (logger.isDebugEnabled()) {
            logger.debug("xss filter is open");
        }
        if (this.handleExcludeURL(req = (HttpServletRequest)request, resp = (HttpServletResponse)response)) {
            filterChain.doFilter(request, response);
            return;
        }
        InjectHttpServletRequestWrapper xssRequest = new InjectHttpServletRequestWrapper((HttpServletRequest)request, IS_INCLUDE_RICH_TEXT, this.xssEnabled, this.sqlEnabled);
        filterChain.doFilter((ServletRequest)xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (this.sqlEnabled.booleanValue()) {
            return false;
        }
        if (!this.xssEnabled.booleanValue()) {
            return true;
        }
        if (this.excludes == null || this.excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : this.excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (!m.find()) continue;
            return true;
        }
        return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String isIncludeRichText;
        if (logger.isDebugEnabled()) {
            logger.debug("xss filter init~~~~~~~~~~~~");
        }
        if (StringUtils.isNotBlank((CharSequence)(isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText")))) {
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean((String)isIncludeRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        String sqlEnabled = filterConfig.getInitParameter("sqlEnabled");
        String xssEnabled = filterConfig.getInitParameter("xssEnabled");
        this.xssEnabled = Boolean.valueOf(xssEnabled);
        this.sqlEnabled = Boolean.valueOf(sqlEnabled);
        if (!StringUtils.isEmpty((CharSequence)temp)) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; ++i) {
                this.excludes.add(url[i]);
            }
        }
    }

    public void destroy() {
    }
}

