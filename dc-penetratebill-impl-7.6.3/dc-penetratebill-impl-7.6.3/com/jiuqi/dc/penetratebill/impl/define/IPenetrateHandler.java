/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.penetratebill.client.common.CustomParam
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateContext
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateParam
 */
package com.jiuqi.dc.penetratebill.impl.define;

import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetrateParam;
import java.util.List;

public interface IPenetrateHandler {
    public String getCode();

    public String getName();

    public String getOrdinal();

    public List<CustomParam> getCustomParam();

    public PenetrateParam handlePenetrate(PenetrateContext var1, List<CustomParam> var2, String var3, String var4);
}

