/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatDimensionSettingEO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatDimensionSettingEO;

public interface FloatDimensionSettingDao {
    public FloatDimensionSettingEO getSelectedFields(String var1);

    public void save(String var1, String var2, String var3, String var4);

    public void delete(String var1);
}

