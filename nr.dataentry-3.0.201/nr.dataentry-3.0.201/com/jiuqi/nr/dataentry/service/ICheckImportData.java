/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.jtable.params.base.FormData
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.excelUpload.ReportMatchResult;
import com.jiuqi.nr.jtable.params.base.FormData;

public interface ICheckImportData {
    public ImportResultRegionObject checkImportData(FormData var1, ReportMatchResult var2, UploadParam var3);
}

