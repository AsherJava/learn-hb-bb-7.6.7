/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.transmission.data.api;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;

public interface IExecuteParam {
    public String getTaskKey();

    public String getDateTime();

    public String getFormSchemeKey();

    public DimensionValueSet getDimensionValueSet();

    public List<String> getForms();

    public int getDoUpload();

    public int getAllowForceUpload();

    public String getExtendParam();

    public String getUploadDesc();

    public Map<String, String> getUploadDimMap();

    public List<String> getAdjustPeriod();
}

