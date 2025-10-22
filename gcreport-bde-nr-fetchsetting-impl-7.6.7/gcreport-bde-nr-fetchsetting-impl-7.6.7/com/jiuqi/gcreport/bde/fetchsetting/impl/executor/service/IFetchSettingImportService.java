/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service;

import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import java.util.List;

public interface IFetchSettingImportService {
    public String getBizType();

    public String importSheet(FetchSettingExcelContext var1, List<ImportExcelSheet> var2);
}

