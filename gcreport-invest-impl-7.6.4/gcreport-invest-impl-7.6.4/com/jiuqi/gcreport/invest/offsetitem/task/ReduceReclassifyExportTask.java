/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.executor.common.IntervalColorExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.offsetitem.task.internal.AbstractLossGainExportTask;
import com.jiuqi.gcreport.offsetitem.executor.common.IntervalColorExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReduceReclassifyExportTask
extends AbstractLossGainExportTask {
    ExportExcelSheet exportExcelSheet(ExportContext context, LossGainOffsetVO lossGainOffsetVO, int sheetNo) {
        List currDeferredIncomeTaxResult = lossGainOffsetVO.getCurrReduceReclassifyResult();
        List<DesignFieldDefineVO> otherShowColumns = this.convert(lossGainOffsetVO.getReduceReclassifyDimension());
        IntervalColorExportExcelSheet exportExcelSheet = this.getExportExcelSheet(context, sheetNo, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.reduceReclassify"));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        this.createHeader(rowDatas, otherShowColumns);
        for (Map record : currDeferredIncomeTaxResult) {
            rowDatas.add(this.excelOneRow(record, otherShowColumns));
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        exportExcelSheet.calcIntervalColorByTwoRow();
        return exportExcelSheet;
    }

    private List<DesignFieldDefineVO> convert(List<Map<String, String>> reclassifyDimension) {
        ArrayList<DesignFieldDefineVO> fieldDefineVOList = new ArrayList<DesignFieldDefineVO>();
        for (Map<String, String> oneDim : reclassifyDimension) {
            DesignFieldDefineVO fieldDefineVO = new DesignFieldDefineVO();
            fieldDefineVO.setKey(oneDim.get("code"));
            fieldDefineVO.setLabel(oneDim.get("title"));
            fieldDefineVOList.add(fieldDefineVO);
        }
        return fieldDefineVOList;
    }
}

