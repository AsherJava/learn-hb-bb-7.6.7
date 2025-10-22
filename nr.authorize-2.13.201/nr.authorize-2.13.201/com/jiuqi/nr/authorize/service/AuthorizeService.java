/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authorize.service;

import com.jiuqi.nr.authorize.service.LicenceService;
import java.util.Map;

@Deprecated
public interface AuthorizeService
extends LicenceService {
    @Override
    public String findAuthorizeConfig(String var1, String var2) throws Exception;

    @Override
    public Map<String, String> findAuthorizeConfig(String var1) throws Exception;
}

