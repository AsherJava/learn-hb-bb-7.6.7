/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.common.security.csrf;

import com.jiuqi.common.base.util.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSRFilter
implements Filter {
    private static Logger logger = LoggerFactory.getLogger(CSRFilter.class);
    public List<String> referers = new ArrayList<String>();
    public List<String> excludes = new ArrayList<String>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp;
        HttpServletRequest req;
        if (logger.isDebugEnabled()) {
            logger.debug("csrf filter is open");
        }
        if (this.handleExcludeURL(req = (HttpServletRequest)request, resp = (HttpServletResponse)response)) {
            chain.doFilter(request, response);
            return;
        }
        String referer = ((HttpServletRequest)request).getHeader("Referer");
        boolean b = false;
        for (String vReferer : this.referers) {
            if (referer != null && !referer.trim().startsWith(vReferer)) continue;
            b = true;
            chain.doFilter(request, response);
            break;
        }
        if (!b) {
            logger.error("\u7591\u4f3cCSRF\u653b\u51fb\uff0creferer:" + referer);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String referer;
        String temp;
        if (logger.isDebugEnabled()) {
            logger.debug("csrf filter init~~~~~~~~~~~~");
        }
        if (!StringUtils.isEmpty((String)(temp = filterConfig.getInitParameter("excludes")))) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; ++i) {
                this.excludes.add(url[i]);
            }
        }
        if (!StringUtils.isEmpty((String)(referer = filterConfig.getInitParameter("referer")))) {
            String[] url = referer.split(",");
            for (int i = 0; url != null && i < url.length; ++i) {
                this.referers.add(url[i]);
            }
        }
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
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
}

