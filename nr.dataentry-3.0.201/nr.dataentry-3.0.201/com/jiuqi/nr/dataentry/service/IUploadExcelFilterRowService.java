/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.definition.facade.FormDefine;
import org.apache.poi.ss.usermodel.Row;

public interface IUploadExcelFilterRowService {
    public boolean filterRow(Row var1, FormDefine var2);
}

