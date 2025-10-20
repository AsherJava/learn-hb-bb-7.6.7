/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 */
package com.jiuqi.gcreport.oauth2.service;

import com.jiuqi.gcreport.oauth2.pojo.CustomParam;
import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;

public interface CustomParamService {
    public String getAuthorizationUrl(String var1, NvwaCertify var2, GcCertifyExtInfo var3, CustomParam var4, boolean var5);

    public String getAccessToken(NvwaCertify var1, GcCertifyExtInfo var2, CustomParam var3, String var4);

    public String getUserInfo(NvwaCertify var1, CustomParam var2, String var3, String var4);
}

