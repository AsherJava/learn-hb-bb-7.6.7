/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.offsetitem.executor.tab.task;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.executor.tab.sheet.OffsetTabExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.executor.tab.util.AdjustingOffsetEntryExportUtils;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OffsetTabExportTask {
    @Autowired
    private GcOffSetAppOffsetService gcOffSetItemAdjustService;
    @Autowired
    private AdjustingOffsetEntryExportUtils adjustingOffsetEntryExportUtil;

    public ExportExcelSheet createSheet(Workbook workbook, QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo, Pagination<Map<String, Object>> offsetMap) {
        SimpleDateFormat EXPORT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        OffsetTabExportExcelSheet offsetTabExportExcelSheet = new OffsetTabExportExcelSheet(sheetNo, GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.offset"), 1);
        List otherShowColumns = queryParamsVO.getOtherShowColumns();
        ArrayList<String> dimensionNumberList = new ArrayList<String>();
        int[] cellColumns = new int[]{7, 8, 9};
        BitSet amtCellColumns = this.adjustingOffsetEntryExportUtil.getAmtCellColumns(otherShowColumns, queryParamsVO.getSystemId(), queryParamsVO.getDataSourceCode(), cellColumns, dimensionNumberList, 11);
        offsetTabExportExcelSheet.setAmtCellCol(amtCellColumns);
        List gcOffSetVchrItemDTOS = offsetMap.getContent();
        String[] titles = this.adjustingOffsetEntryExportUtil.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.elmMode", "gc.calculate.adjustingentry.showoffset.gcBusinessType", "gc.calculate.adjustingentry.showoffset.ruleTitle", "gc.calculate.adjustingentry.showoffset.thisUnit", "gc.calculate.adjustingentry.showoffset.oppUnit", "gc.calculate.adjustingentry.showoffset.subjectTitle", "gc.calculate.adjustingentry.showoffset.debitAmt", "gc.calculate.adjustingentry.showoffset.creditAmt", "gc.calculate.adjustingentry.showoffset.diff", "gc.calculate.adjustingentry.showoffset.memo"});
        String[] keys = new String[]{"index", "ELMMODETITLE", "GCBUSINESSTYPE", "RULETITLE", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "OFFSETDEBIT", "OFFSETCREDIT", "DIFF", "MEMO"};
        ArrayList titleList = new ArrayList();
        ArrayList keyList = new ArrayList();
        Set<Integer> offSetIntervalRowSet = this.getOffSetIntervalRowSet(offsetMap);
        if (offSetIntervalRowSet != null) {
            offSetIntervalRowSet.forEach(offsetTabExportExcelSheet::appendIntervalColorRow);
        }
        Collections.addAll(titleList, titles);
        Collections.addAll(keyList, keys);
        if (!org.springframework.util.CollectionUtils.isEmpty(otherShowColumns)) {
            keyList.addAll(otherShowColumns);
            titleList.addAll(queryParamsVO.getOtherShowColumnTitles());
        }
        titles = new String[titleList.size()];
        titles = titleList.toArray(titles);
        keys = new String[keyList.size()];
        keys = keyList.toArray(keys);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        rowDatas.add(titles);
        if (templateExportFlag.booleanValue()) {
            offsetTabExportExcelSheet.getRowDatas().addAll(rowDatas);
            return offsetTabExportExcelSheet;
        }
        int mergeStart = -1;
        int rowIndex = 1;
        int sn = 1;
        String curMercid = null;
        for (Map gcOffSetVchrItemDTO : gcOffSetVchrItemDTOS) {
            Object[] rowData = new Object[titleList.size()];
            Map stringObjectMap = gcOffSetVchrItemDTO;
            this.adjustingOffsetEntryExportUtil.adaptMsg(stringObjectMap);
            if (stringObjectMap.containsKey("UNIONRULETITLE")) {
                stringObjectMap.put("UNIONRULEID", stringObjectMap.get("UNIONRULETITLE"));
            } else if (stringObjectMap.containsKey("UNIONRULEID") && !StringUtils.isEmpty(stringObjectMap.get("UNIONRULEID"))) {
                String unionruleTitle = this.adjustingOffsetEntryExportUtil.getRuleTitle((String)stringObjectMap.get("UNIONRULEID"));
                stringObjectMap.put("UNIONRULEID", unionruleTitle);
            }
            if (!stringObjectMap.containsKey("index") && !StringUtils.isEmpty(gcOffSetVchrItemDTO.get("ID"))) {
                stringObjectMap.put("index", sn++);
            }
            for (int j = 0; j < keys.length; ++j) {
                Object valueObj = stringObjectMap.get(keys[j]);
                if (valueObj != null) {
                    if ((this.adjustingOffsetEntryExportUtil.isAmt(keys[j]) || dimensionNumberList.contains(keys[j])) && !"SUBJECTORIENT".equals(keys[j])) {
                        String value;
                        CellType cellType = (CellType)offsetTabExportExcelSheet.getContentCellTypeCache().get(j);
                        if (!CellType.NUMERIC.equals((Object)cellType)) {
                            offsetTabExportExcelSheet.getContentCellTypeCache().put(j, CellType.NUMERIC);
                            CellStyle cellStyle = (CellStyle)offsetTabExportExcelSheet.getContentCellStyleCache().get(j);
                            if (cellStyle == null) {
                                CellStyle contentAmtStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
                                contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
                                contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
                                offsetTabExportExcelSheet.getContentCellStyleCache().put(j, contentAmtStyle);
                            }
                        }
                        if ((value = valueObj.toString()).length() == 0) continue;
                        rowData[j] = ConverterUtils.getAsBigDecimal((Object)value.replace(",", ""), (BigDecimal)BigDecimal.ZERO);
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
            String rowMercid = (String)stringObjectMap.get("MRECID");
            if (null != rowMercid) {
                if (null == curMercid) {
                    curMercid = rowMercid;
                    mergeStart = rowIndex;
                } else if (!curMercid.equals(rowMercid)) {
                    this.adjustingOffsetEntryExportUtil.addMergedRegion(offsetTabExportExcelSheet, mergeStart, rowIndex - 1, 0, 0);
                    this.adjustingOffsetEntryExportUtil.addMergedRegion(offsetTabExportExcelSheet, mergeStart, rowIndex - 1, 1, 1);
                    mergeStart = rowIndex;
                    curMercid = rowMercid;
                } else if (rowIndex == gcOffSetVchrItemDTOS.size()) {
                    this.adjustingOffsetEntryExportUtil.addMergedRegion(offsetTabExportExcelSheet, mergeStart, rowIndex, 0, 0);
                    this.adjustingOffsetEntryExportUtil.addMergedRegion(offsetTabExportExcelSheet, mergeStart, rowIndex, 1, 1);
                }
            }
            rowDatas.add(rowData);
            ++rowIndex;
        }
        offsetTabExportExcelSheet.getRowDatas().addAll(rowDatas);
        return offsetTabExportExcelSheet;
    }

    public ExportExcelSheet createSheet(Workbook workbook, QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo) {
        Pagination<Map<String, Object>> offsetMap = this.gcOffSetItemAdjustService.listOffsetEntrys(queryParamsVO);
        return this.createSheet(workbook, queryParamsVO, templateExportFlag, sheetNo, offsetMap);
    }

    private Set<Integer> getOffSetIntervalRowSet(Pagination<Map<String, Object>> offsetMap) {
        List content = offsetMap.getContent();
        if (CollectionUtils.isEmpty((Collection)content)) {
            return null;
        }
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        HashSet<String> mrecids = new HashSet<String>();
        for (int i = 0; i < content.size(); ++i) {
            Map item = (Map)content.get(i);
            if (item.get("MRECID") != null) {
                mrecids.add(item.get("MRECID").toString());
            }
            if (mrecids.size() % 2 != 1) continue;
            rowNumber.add(i + 1);
        }
        return rowNumber;
    }
}

