/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.factory.option;

import com.jiuqi.gcreport.inputdata.offsetitem.factory.IUnOffsetExportOptionTask;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetTabExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.stereotype.Component;

@Component(value="UnOffsetDynamicExportTask")
public class UnOffsetDynamicExportTask
implements IUnOffsetExportOptionTask {
    @Override
    public UnOffsetTabExportExcelSheet createSheet(QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo) {
        return null;
    }
}

