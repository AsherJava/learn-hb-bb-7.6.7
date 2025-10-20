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
public class LossGainExportTask
extends AbstractLossGainExportTask {
    protected ExportExcelSheet exportExcelSheet(ExportContext context, LossGainOffsetVO lossGainOffsetVO, int sheetNo) {
        List currLossGainResult = lossGainOffsetVO.currLossGainResultFields();
        MultiTableExportExcelSheet exportExcelSheet = this.getMultiTableExportExcelSheet(context, sheetNo, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.profitlossCarryForward"));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        exportExcelSheet.setFirstRegionLabel(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.currLossTitle") + ":");
        this.createHeader(rowDatas, lossGainOffsetVO.getOtherShowColumns());
        for (Map record : currLossGainResult) {
            rowDatas.add(this.excelOneRow(record, lossGainOffsetVO.getOtherShowColumns()));
        }
        exportExcelSheet.getFirstRegionRowDatas().addAll(rowDatas);
        exportExcelSheet.setSecondRegionLabel(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lastLossTitle") + ":");
        rowDatas.clear();
        this.createHeader(rowDatas, lossGainOffsetVO.getOtherShowColumns());
        for (Map record : lossGainOffsetVO.currLossGainResultFields()) {
            rowDatas.add(this.excelOneRow(record, lossGainOffsetVO.getOtherShowColumns()));
        }
        exportExcelSheet.getSecondRegionRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }
}

