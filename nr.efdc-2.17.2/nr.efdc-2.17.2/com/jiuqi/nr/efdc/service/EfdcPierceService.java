/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.service;

import com.jiuqi.nr.efdc.param.EfdcEnableInfo;
import com.jiuqi.nr.efdc.param.EfdcNewRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcResponseInfo;

public interface EfdcPierceService {
    public EfdcResponseInfo getResponseInfo(EfdcRequestInfo var1) throws Exception;

    public EfdcResponseInfo getResponseInfoNew(EfdcNewRequestInfo var1) throws Exception;

    public Integer getBBLX(EfdcNewRequestInfo var1) throws Exception;

    public EfdcEnableInfo getEFDCEnable(EfdcNewRequestInfo var1) throws Exception;
}

