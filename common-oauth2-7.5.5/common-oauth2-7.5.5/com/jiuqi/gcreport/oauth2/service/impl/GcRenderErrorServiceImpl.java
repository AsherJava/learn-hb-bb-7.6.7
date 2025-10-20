/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.service.GcRenderErrorService;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyThreadLocal;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class GcRenderErrorServiceImpl
implements GcRenderErrorService {
    private static final Logger logger = LoggerFactory.getLogger(GcRenderErrorServiceImpl.class);

    @Override
    public void renderError(String error, HttpServletResponse response) {
        NvwaCertify nvwaCertify = NvwaCertifyThreadLocal.get();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            if (nvwaCertify == null) {
                writer.println(error);
            } else {
                GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(NvwaCertifyThreadLocal.get());
                if (StringUtils.hasText(ext.getUnionUserLoginFailTips())) {
                    writer.println(ext.getUnionUserLoginFailTips());
                } else {
                    writer.println(error);
                }
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}

