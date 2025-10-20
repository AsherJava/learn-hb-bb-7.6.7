/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringEscapeUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.oauth2.web;

import com.jiuqi.gcreport.oauth2.util.Base64Utils;
import com.jiuqi.gcreport.oauth2.util.UrlEncodeUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrintBrowserController {
    private static final Logger logger = LoggerFactory.getLogger(PrintBrowserController.class);

    @GetMapping(value={"/anon/gcoauth2tips"})
    public void printMsg(@RequestParam(name="msg", required=false) String msg, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String tips = null;
        if (StringUtils.isBlank((CharSequence)msg)) {
            logger.info("output msg to browser but no content.");
            return;
        }
        String base64Decode = Base64Utils.base64Decode(msg);
        tips = StringEscapeUtils.escapeHtml4((String)base64Decode);
        try {
            PrintWriter writer = response.getWriter();
            writer.println(tips);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            logger.warn("output msg to browser occur exception.", e);
        }
    }

    @GetMapping(value={"/anon/gctipsgen"})
    public void printMsg(@RequestParam(name="msg", required=false) String msg, @RequestParam(name="url", required=false) String url, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String tips = null;
        if (!StringUtils.isBlank((CharSequence)msg)) {
            tips = UrlEncodeUtil.encode(Base64Utils.base64Encode(msg));
        } else if (!StringUtils.isBlank((CharSequence)url)) {
            tips = UrlEncodeUtil.encode(url);
        } else {
            logger.warn("/anon/gctipsgen input args no msg nor url.");
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.println(tips);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            logger.warn("output msg to browser occur exception.", e);
        }
    }

    @GetMapping(value={"/api/logoutPage", "/api/oauth2/logoutPage"})
    public void logoutPage(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter();){
            writer.println("<h3>\u60a8\u7684\u8d26\u53f7\u5df2\u9000\u51fa\u767b\u5f55\u6210\u529f\uff0c\u4e3a\u4e86\u8d26\u53f7\u5b89\u5168\u8bf7\u5173\u95ed\u6b64\u9875\u9762\u3002</h3>");
            writer.flush();
        }
        catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}

