/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.offsetitem.task.internal.AbstractLossGainExportTask;
import com.jiuqi.gcreport.invest.offsetitem.task.internal.MultiTableExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DeferredIncomeTaxExportTask
extends AbstractLossGainExportTask {
    ExportExcelSheet exportExcelSheet(ExportContext context, LossGainOffsetVO lossGainOffsetVO, int sheetNo) {
        List currDeferredIncomeTaxResult = lossGainOffsetVO.currDeferredIncomeTaxResultFields();
        MultiTableExportExcelSheet exportExcelSheet = this.getMultiTableExportExcelSheet(context, sheetNo, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTax"));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        exportExcelSheet.setFirstRegionLabel(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.currDeferredEntry") + "\uff1a");
        this.createHeader(rowDatas, lossGainOffsetVO.getOtherShowColumns());
        for (Map record : currDeferredIncomeTaxResult) {
            rowDatas.add(this.excelOneRow(record, lossGainOffsetVO.getOtherShowColumns()));
        }
        exportExcelSheet.getFirstRegionRowDatas().addAll(rowDatas);
        exportExcelSheet.setSecondRegionLabel(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lastDeferredEntry") + "\uff1a");
        rowDatas.clear();
        this.createHeader(rowDatas, lossGainOffsetVO.getOtherShowColumns());
        for (Map record : lossGainOffsetVO.currDeferredIncomeTaxResultFields()) {
            rowDatas.add(this.excelOneRow(record, lossGainOffsetVO.getOtherShowColumns()));
        }
        exportExcelSheet.getSecondRegionRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }
}

