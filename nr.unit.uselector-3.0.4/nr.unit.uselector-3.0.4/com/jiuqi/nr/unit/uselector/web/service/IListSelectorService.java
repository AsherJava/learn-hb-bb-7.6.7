/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.unit.uselector.web.service;

import com.jiuqi.nr.unit.uselector.dataio.IExcelWriteWorker;
import com.jiuqi.nr.unit.uselector.filter.listselect.FImportedConditions;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableDataSet;
import org.springframework.web.multipart.MultipartFile;

public interface IListSelectorService {
    public FTableDataSet editConditions(FImportedConditions var1);

    public FTableDataSet importConditions(String var1, MultipartFile var2);

    public IExcelWriteWorker exportConditionTemplate(String var1);

    public IExcelWriteWorker exportResultSet(FImportedConditions var1);
}

