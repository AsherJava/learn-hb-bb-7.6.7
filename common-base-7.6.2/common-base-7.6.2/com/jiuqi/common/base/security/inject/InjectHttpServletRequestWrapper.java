/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ReadListener
 *  javax.servlet.ServletInputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringEscapeUtils
 */
package com.jiuqi.common.base.security.inject;

import com.jiuqi.common.base.security.inject.JsoupUtil;
import com.jiuqi.common.base.security.inject.SqlInjectValidUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

public class InjectHttpServletRequestWrapper
extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;
    private boolean isIncludeRichText = false;
    private Boolean xssEnabled;
    private Boolean sqlEnabled;

    public InjectHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText, Boolean xssEnabled, Boolean sqlEnabled) {
        super(request);
        this.orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
        this.sqlEnabled = sqlEnabled;
        this.xssEnabled = xssEnabled;
    }

    public String getParameter(String name) {
        Boolean flag = "content".equals(name) || name.endsWith("WithHtml");
        if (flag.booleanValue() && !this.isIncludeRichText) {
            return super.getParameter(name);
        }
        String value = super.getParameter(name = this.injectHandle(name));
        if (StringUtils.isNotBlank((CharSequence)value)) {
            value = this.injectHandle(value);
        }
        return value;
    }

    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = this.injectHandle(arr[i]);
            }
        }
        return arr;
    }

    public String getHeader(String name) {
        String value = super.getHeader(name = this.injectHandle(name));
        if (StringUtils.isNotBlank((CharSequence)value)) {
            value = this.injectHandle(value);
        }
        return value;
    }

    public ServletInputStream getInputStream() throws IOException {
        String contentType = super.getHeader("Content-Type");
        if (!"application/json".equalsIgnoreCase(contentType) && !"application/json;charset=UTF-8".equalsIgnoreCase(contentType)) {
            return super.getInputStream();
        }
        String json = IOUtils.toString((InputStream)super.getInputStream(), (String)"utf-8");
        if (StringUtils.isBlank((CharSequence)json)) {
            return super.getInputStream();
        }
        json = this.injectHandle(json);
        json = StringEscapeUtils.unescapeHtml4((String)json);
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
        return new ServletInputStream(){

            public boolean isFinished() {
                return true;
            }

            public boolean isReady() {
                return true;
            }

            public void setReadListener(ReadListener readListener) {
            }

            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    public HttpServletRequest getOrgRequest() {
        return this.orgRequest;
    }

    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof InjectHttpServletRequestWrapper) {
            return ((InjectHttpServletRequestWrapper)req).getOrgRequest();
        }
        return req;
    }

    private String injectHandle(String content) {
        if (StringUtils.isNotBlank((CharSequence)content)) {
            if (this.sqlEnabled.booleanValue()) {
                SqlInjectValidUtil.valid(content);
            }
            if (this.xssEnabled.booleanValue()) {
                content = JsoupUtil.clean(content);
            }
        }
        return content;
    }
}

