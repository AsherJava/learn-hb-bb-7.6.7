/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.executor.tab.task;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.executor.tab.sheet.SumTabExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.executor.tab.util.AdjustingOffsetEntryExportUtils;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumTabExportTask {
    @Autowired
    private GcOffSetAppOffsetService gcOffSetItemAdjustService;
    @Autowired
    private AdjustingOffsetEntryExportUtils adjustingOffsetEntryExportUtil;

    public ExportExcelSheet createSheet(QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo, Pagination<Map<String, Object>> sumTabMap) {
        SimpleDateFormat EXPORT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SumTabExportExcelSheet sumTabExportExcelSheet = new SumTabExportExcelSheet(sheetNo, GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.sumtab"), 2);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List otherShowColumns = queryParamsVO.getSumTabOtherColumns();
        ArrayList<String> dimensionNumberList = new ArrayList<String>();
        int[] cellColumns = new int[]{3, 4, 5, 6, 7, 8};
        BitSet amtCellColumns = this.adjustingOffsetEntryExportUtil.getAmtCellColumns(otherShowColumns, queryParamsVO.getSystemId(), queryParamsVO.getDataSourceCode(), cellColumns, dimensionNumberList, 9);
        sumTabExportExcelSheet.setAmtCellCol(amtCellColumns);
        int i = 1;
        for (Map item : sumTabMap.getContent()) {
            item.put("index", i++);
        }
        List gcOffSetVchrItemDTOS = sumTabMap.getContent();
        String[] excelHeader0 = this.adjustingOffsetEntryExportUtil.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.subjectCode", "gc.calculate.adjustingentry.showoffset.subjectName", "gc.calculate.adjustingentry.showoffset.offsetAmt", "gc.calculate.adjustingentry.showoffset.offsetAmt", "gc.calculate.adjustingentry.showoffset.offsetAmt", "gc.calculate.adjustingentry.showoffset.unOffsetAmt", "gc.calculate.adjustingentry.showoffset.unOffsetAmt", "gc.calculate.adjustingentry.showoffset.unOffsetAmt"});
        String[] excelHeader1 = this.adjustingOffsetEntryExportUtil.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.subjectCode", "gc.calculate.adjustingentry.showoffset.subjectName", "gc.calculate.adjustingentry.showoffset.debitOffsetAmt", "gc.calculate.adjustingentry.showoffset.creditOffsetAmt", "gc.calculate.adjustingentry.showoffset.creditOffsetDiffAmt", "gc.calculate.adjustingentry.showoffset.debitUnOffsetAmt", "gc.calculate.adjustingentry.showoffset.creditUnOffsetAmt", "gc.calculate.adjustingentry.showoffset.creditUnOffsetDiffAmt"});
        String[] keys = new String[]{"index", "SECONDSUB", "SUBJECTTITLE", "OFFSETDEBITVALUE", "OFFSETCREDITVALUE", "OFFSETDIFFVALUE", "UNOFFSETDEBITVALUE", "UNOFFSETCREDITVALUE", "UNOFFSETDIFFVALUE"};
        ArrayList keyList = new ArrayList();
        ArrayList excelHeader0List = new ArrayList();
        ArrayList excelHeader1List = new ArrayList();
        Set<Integer> sumTabIntervalRowSet = this.getSumTabIntervalRowSet(sumTabMap);
        if (sumTabIntervalRowSet != null) {
            sumTabIntervalRowSet.forEach(sumTabExportExcelSheet::appendIntervalColorRow);
        }
        Collections.addAll(excelHeader0List, excelHeader0);
        Collections.addAll(excelHeader1List, excelHeader1);
        Collections.addAll(keyList, keys);
        if (!org.springframework.util.CollectionUtils.isEmpty(otherShowColumns)) {
            keyList.addAll(otherShowColumns);
            excelHeader0List.addAll(queryParamsVO.getSumTabOtherTitles());
            excelHeader1List.addAll(queryParamsVO.getSumTabOtherTitles());
            excelHeader0 = new String[excelHeader0List.size()];
            excelHeader1 = new String[excelHeader1List.size()];
            excelHeader0 = excelHeader0List.toArray(excelHeader0);
            excelHeader1 = excelHeader1List.toArray(excelHeader1);
            keys = new String[keyList.size()];
            keys = keyList.toArray(keys);
        }
        int[] columnWidths = new int[keyList.size()];
        Arrays.fill(columnWidths, 5500);
        rowDatas.add(excelHeader0);
        rowDatas.add(excelHeader1);
        sumTabExportExcelSheet.setColumnWidths(columnWidths);
        if (templateExportFlag.booleanValue()) {
            sumTabExportExcelSheet.getRowDatas().addAll(rowDatas);
            return sumTabExportExcelSheet;
        }
        for (Map gcOffSetVchrItemDTO : gcOffSetVchrItemDTOS) {
            Object[] rowData = new Object[excelHeader0List.size()];
            Map stringObjectMap = gcOffSetVchrItemDTO;
            for (int j = 0; j < keys.length; ++j) {
                Object valueObj = stringObjectMap.get(keys[j]);
                if (valueObj != null) {
                    if (this.adjustingOffsetEntryExportUtil.isAmt(keys[j]) || dimensionNumberList.contains(keys[j])) {
                        String value = valueObj.toString();
                        if (value.length() == 0) continue;
                        rowData[j] = Double.valueOf(value.replace(",", ""));
                        continue;
                    }
                    if (valueObj instanceof Date) {
                        rowData[j] = EXPORT_DATE_FORMAT.format((Date)valueObj);
                        continue;
                    }
                    rowData[j] = valueObj.toString();
                    continue;
                }
                rowData[j] = null;
            }
            rowDatas.add(rowData);
        }
        sumTabExportExcelSheet.getRowDatas().addAll(rowDatas);
        return sumTabExportExcelSheet;
    }

    public ExportExcelSheet createSheet(QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo) {
        Pagination<Map<String, Object>> sumTabMap = this.gcOffSetItemAdjustService.listSumTabRecords(queryParamsVO);
        return this.createSheet(queryParamsVO, templateExportFlag, sheetNo, sumTabMap);
    }

    private Set<Integer> getSumTabIntervalRowSet(Pagination<Map<String, Object>> sumTabMap) {
        List content = sumTabMap.getContent();
        if (CollectionUtils.isEmpty((Collection)content)) {
            return null;
        }
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        for (int i = 0; i < content.size(); ++i) {
            Map item = (Map)content.get(i);
            if (item.get("SUBJECTTITLE") != null) continue;
            rowNumber.add(i + 2);
        }
        return rowNumber;
    }
}

