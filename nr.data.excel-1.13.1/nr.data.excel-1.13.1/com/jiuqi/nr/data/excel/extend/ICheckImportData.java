/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.excel.extend;

import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.data.excel.param.UploadParam;
import com.jiuqi.nr.data.excel.param.upload.ReportMatchResult;
import com.jiuqi.nr.definition.facade.FormDefine;

public interface ICheckImportData {
    public ImportResultRegionObject checkImportData(FormDefine var1, ReportMatchResult var2, UploadParam var3);
}

