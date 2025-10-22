/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;

public interface FloatConfigExcelHandle {
    public String getCode();

    public FloatRegionConfigVO getQueryFieldByConfig(FloatRegionConfigData var1, FetchSettingExcelContext var2);

    public String initFloatConfig(FloatRegionConfigVO var1, FetchSettingExportContext var2);
}

