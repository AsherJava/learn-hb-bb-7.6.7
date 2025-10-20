/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk.service;

import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.va.domain.common.R;

public interface DingtalkService {
    public String getLoginUrl(NvwaCertify var1);

    public String getAccessToken(String var1);

    public String getUserInfo(String var1);

    public String getRelatedPeopleUsername(String var1);

    public R innerLogin(String var1);
}

