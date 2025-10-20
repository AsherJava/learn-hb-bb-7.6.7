/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.factory.option;

import com.jiuqi.gcreport.inputdata.offsetitem.factory.IUnOffsetExportOptionTask;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.option.UnOffsetDynamicExportTask;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetTabExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="UnOffsetDynamicParentExportTask")
public class UnOffsetDynamicParentExportTask
implements IUnOffsetExportOptionTask {
    @Autowired
    private UnOffsetDynamicExportTask unOffsetDynamicExportTask;

    @Override
    public UnOffsetTabExportExcelSheet createSheet(QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo) {
        return null;
    }
}

