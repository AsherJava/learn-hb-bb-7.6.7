/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  jakarta.servlet.Filter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.FilterConfig
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.va.biz.encrypt.boot3;

import com.jiuqi.va.biz.encrypt.EncryptedVO;
import com.jiuqi.va.biz.encrypt.LcdpEncryptDES;
import com.jiuqi.va.biz.encrypt.boot3.CustomHttpServletRequestWrapper;
import com.jiuqi.va.domain.common.JSONUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;
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

