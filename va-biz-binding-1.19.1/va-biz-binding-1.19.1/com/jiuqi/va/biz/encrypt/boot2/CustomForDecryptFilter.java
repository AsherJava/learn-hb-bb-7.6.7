/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.va.biz.encrypt.boot2;

import com.jiuqi.va.biz.encrypt.EncryptedVO;
import com.jiuqi.va.biz.encrypt.LcdpEncryptDES;
import com.jiuqi.va.biz.encrypt.boot2.CustomHttpServletRequestWrapper;
import com.jiuqi.va.domain.common.JSONUtil;
import java.io.IOException;
import java.io.Reader;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

public class CustomForDecryptFilter
implements Filter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CustomHttpServletRequestWrapper requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            String encryptedString;
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            String lcdpEncryptOn = servletRequest.getParameter("lcdpEncryptOn");
            if (StringUtils.hasText(lcdpEncryptOn) && lcdpEncryptOn.equals("true") && StringUtils.hasText(encryptedString = IOUtils.toString((Reader)httpServletRequest.getReader()))) {
                EncryptedVO vo = (EncryptedVO)JSONUtil.parseObject((String)encryptedString, EncryptedVO.class);
                String decryptedData = LcdpEncryptDES.decrypt(String.valueOf(vo.getEncryptedData()));
                requestWrapper = new CustomHttpServletRequestWrapper(httpServletRequest);
                requestWrapper.setBody(decryptedData);
            }
        }
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}

