/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 */
package com.jiuqi.gcreport.oauth2.service;

import com.jiuqi.nvwa.certification.bean.NvwaCertify;

public interface GcNvwaCertifyService {
    @Deprecated
    public NvwaCertify getGcOAuth2TypeNvwaCertify(String var1);

    public NvwaCertify getNvwaCertifyByTypeFirstIfMoreUseCode(String var1, String var2);

    public NvwaCertify getNvwaCertifyByCode(String var1);
}

