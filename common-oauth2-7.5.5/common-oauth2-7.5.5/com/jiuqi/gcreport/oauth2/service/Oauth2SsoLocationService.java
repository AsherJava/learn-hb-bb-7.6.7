/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.oauth2.service;

import com.jiuqi.gcreport.oauth2.pojo.GcSsoBuildDTO;

public interface Oauth2SsoLocationService {
    public String buildCurSsoLocation(GcSsoBuildDTO var1);

    public String buildCurSsoLocationFromGenUrl(String var1, String var2, String var3);
}

