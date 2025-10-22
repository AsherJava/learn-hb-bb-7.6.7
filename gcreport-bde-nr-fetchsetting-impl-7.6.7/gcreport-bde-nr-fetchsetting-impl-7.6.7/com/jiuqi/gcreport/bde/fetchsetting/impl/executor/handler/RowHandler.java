/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.RegionFetchSetting;
import java.util.List;

public interface RowHandler {
    public List<Object[]> handleExportData(FetchSettingExportContext var1, FetchSettingCond var2);

    public RegionFetchSetting handleImportData(ExcelSettingDTO var1, FetchSettingExcelContext var2);
}

