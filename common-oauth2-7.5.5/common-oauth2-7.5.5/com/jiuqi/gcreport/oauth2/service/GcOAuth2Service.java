/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.oauth2.service;

import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public interface GcOAuth2Service {
    public String getAuthUrl(NvwaCertify var1);

    public String getAccessToken(String var1);

    public String getLoginUserName(String var1);

    public void loginSuccessRedirect(String var1, String var2, HttpServletResponse var3) throws IOException;

    public void todoRedirectDataentry(String var1, String var2, HttpServletResponse var3) throws IOException;

    public void agileRedirect(String var1, String var2, HttpServletResponse var3) throws IOException;
}

