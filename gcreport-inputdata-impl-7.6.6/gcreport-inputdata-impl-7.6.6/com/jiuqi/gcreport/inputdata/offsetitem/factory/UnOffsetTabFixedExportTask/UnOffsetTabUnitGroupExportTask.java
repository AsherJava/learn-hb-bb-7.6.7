/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.OffsetItemExportParam
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.executor.tab.util.AdjustingOffsetEntryExportUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.factory.UnOffsetTabFixedExportTask;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.IUnOffsetExportOptionTask;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.sheet.UnOffsetTabExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.dto.OffsetItemExportParam;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.executor.tab.util.AdjustingOffsetEntryExportUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class UnOffsetTabUnitGroupExportTask
implements IUnOffsetExportOptionTask,
GcOffSetItemAction {
    @Autowired
    private AdjustingOffsetEntryExportUtils adjustingOffsetEntryExportUtil;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetAppOffsetService;
    private final DateFormat EXPORT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public String code() {
        return "export";
    }

    public String title() {
        return "\u6309\u76f4\u63a5\u4e0b\u7ea7\u5206\u7ec4\u53cc\u4efd\u5c55\u793a";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        Map paramName2Value = (Map)gcOffsetExecutorVO.getParamObject();
        return this.createSheet((QueryParamsVO)paramName2Value.get("queryParamsVO"), (Boolean)paramName2Value.get("templateExportFlag"), (Integer)paramName2Value.get("sheetNo"));
    }

    @Override
    public UnOffsetTabExportExcelSheet createSheet(QueryParamsVO queryParamsVO, Boolean templateExportFlag, int sheetNo) {
        queryParamsVO.setActionCode("query");
        Pagination unOffsetMap = this.gcOffSetAppOffsetService.listOffsetRecordsForAction(queryParamsVO);
        queryParamsVO.setActionCode("export");
        UnOffsetTabExportExcelSheet unOffsetTabExportExcelSheet = new UnOffsetTabExportExcelSheet(sheetNo, GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.unOffset"), 1);
        List otherShowColumns = queryParamsVO.getOtherShowColumns();
        ArrayList dimensionNumberList = new ArrayList();
        int[] cellColumns = new int[]{5, 6, 7};
        String[] titles = this.adjustingOffsetEntryExportUtil.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.thisUnit", "gc.calculate.adjustingentry.showoffset.oppUnit", "gc.calculate.adjustingentry.showoffset.ruleTitle", "gc.calculate.adjustingentry.showoffset.subjectTitle", "gc.calculate.adjustingentry.showoffset.debitAmt", "gc.calculate.adjustingentry.showoffset.creditAmt", "gc.calculate.adjustingentry.showoffset.diff", "gc.calculate.adjustingentry.showoffset.memo"});
        String[] keys = new String[]{"index", "UNITTITLE", "OPPUNITTITLE", "UNIONRULEID", "SUBJECTTITLE", "DEBITVALUE", "CREDITVALUE", "DIFFERENCEVALUETOTAL", "MEMO"};
        int index = 9;
        if (unOffsetMap.getContent().size() > 0) {
            unOffsetMap.setContent(this.addSubtotalRowByUnit(unOffsetMap.getContent()));
        }
        List gcOffSetVchrItemDTOS = unOffsetMap.getContent();
        Map<String, Set<Integer>> unitGroupBoldRows = this.unitGroupFontBoldRows((OffsetItemExportParam)queryParamsVO, (Pagination<Map<String, Object>>)unOffsetMap);
        Set<Integer> boldRowSet = unitGroupBoldRows.get("unitGroupFontBoldRows");
        Set<Integer> intervalColorRowSet = unitGroupBoldRows.get("unitGroupFontBoldColorRows");
        for (int count = 0; count < keys.length + otherShowColumns.size(); ++count) {
            unOffsetTabExportExcelSheet.setIntervalFontBoldColorRowAndCol(count, intervalColorRowSet);
        }
        boldRowSet.forEach(arg_0 -> ((UnOffsetTabExportExcelSheet)unOffsetTabExportExcelSheet).appendIntervalFontBoldRow(arg_0));
        BitSet amtCellColumns = this.adjustingOffsetEntryExportUtil.getAmtCellColumns(otherShowColumns, queryParamsVO.getSystemId(), queryParamsVO.getDataSourceCode(), cellColumns, dimensionNumberList, index);
        unOffsetTabExportExcelSheet.setAmtCellCol(amtCellColumns);
        Set<Integer> notOffSetIntervalRowSet = this.getSetIntervalRowSet((Pagination<Map<String, Object>>)unOffsetMap);
        if (notOffSetIntervalRowSet != null) {
            notOffSetIntervalRowSet.forEach(arg_0 -> ((UnOffsetTabExportExcelSheet)unOffsetTabExportExcelSheet).appendIntervalColorRow(arg_0));
        }
        ArrayList titleList = new ArrayList();
        ArrayList keyList = new ArrayList();
        Collections.addAll(titleList, titles);
        Collections.addAll(keyList, keys);
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
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
            unOffsetTabExportExcelSheet.getRowDatas().addAll(rowDatas);
            return unOffsetTabExportExcelSheet;
        }
        int rowIndex = 1;
        int sn = 1;
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
                    if (this.adjustingOffsetEntryExportUtil.isAmt(keys[j]) || dimensionNumberList.contains(keys[j])) {
                        String value = valueObj.toString();
                        if (value.length() == 0) continue;
                        rowData[j] = Double.valueOf(value.replace(",", ""));
                        continue;
                    }
                    if (valueObj instanceof Date) {
                        rowData[j] = this.EXPORT_DATE_FORMAT.format((Date)valueObj);
                        continue;
                    }
                    rowData[j] = valueObj.toString();
                    continue;
                }
                rowData[j] = null;
            }
            if (!stringObjectMap.containsKey("ID")) {
                if (stringObjectMap.containsKey("even")) {
                    rowData[0] = stringObjectMap.get("UNITTITLE");
                    this.adjustingOffsetEntryExportUtil.addMergedRegion((ExportExcelSheet)unOffsetTabExportExcelSheet, rowIndex, rowIndex, 0, 2);
                } else {
                    this.adjustingOffsetEntryExportUtil.addMergedRegion((ExportExcelSheet)unOffsetTabExportExcelSheet, rowIndex, rowIndex, 1, keys.length - 1);
                }
            }
            rowDatas.add(rowData);
            ++rowIndex;
        }
        unOffsetTabExportExcelSheet.getRowDatas().addAll(rowDatas);
        return unOffsetTabExportExcelSheet;
    }

    private Map<String, Set<Integer>> unitGroupFontBoldRows(OffsetItemExportParam queryParamsVO, Pagination<Map<String, Object>> map) {
        HashMap<String, Set<Integer>> type2Row = new HashMap<String, Set<Integer>>();
        HashSet<Integer> boldRowSet = new HashSet<Integer>();
        type2Row.put("unitGroupFontBoldRows", boldRowSet);
        HashSet<Integer> intervalColorRowSet = new HashSet<Integer>();
        type2Row.put("unitGroupFontBoldColorRows", intervalColorRowSet);
        if (FilterMethodEnum.UNITGROUP.getCode().equals(queryParamsVO.getFilterMethod())) {
            List gcOffSetVchrItemDTOS = map.getContent();
            for (int count = 0; count < gcOffSetVchrItemDTOS.size(); ++count) {
                Map gcOffSetVchrItemDTO = (Map)gcOffSetVchrItemDTOS.get(count);
                if (gcOffSetVchrItemDTO.containsKey("ID") || GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.subtotal").equals(gcOffSetVchrItemDTO.get("UNITTITLE"))) continue;
                if (gcOffSetVchrItemDTO.containsKey("even")) {
                    intervalColorRowSet.add(count + 1);
                    continue;
                }
                boldRowSet.add(count + 1);
            }
        }
        return type2Row;
    }

    private List<Map<String, Object>> addSubtotalRowByUnit(List<Map<String, Object>> content) {
        BigDecimal debitSum = new BigDecimal(0.0);
        BigDecimal creditSum = new BigDecimal(0.0);
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashSet<String> concatUnitSet = new HashSet<String>();
        for (int i = 0; i < content.size(); ++i) {
            String oppUnitId;
            String unitId;
            String concatUnit;
            Map<String, Object> item = content.get(i);
            if (!item.containsKey("ID")) {
                if (concatUnitSet.size() >= 1) {
                    Map<String, Object> subtotalRow = this.createSubtotalRow(debitSum, creditSum);
                    subtotalRow.put("ID", "");
                    result.add(subtotalRow);
                    debitSum = new BigDecimal(0.0);
                    creditSum = new BigDecimal(0.0);
                    concatUnitSet.clear();
                }
                if (!item.containsKey("even")) {
                    item.put("UNITTITLE", item.get("UNIONRULETITLE"));
                }
                if (!content.get(i + 1).containsKey("ID")) {
                    result.add(item);
                    continue;
                }
                item.put("UNITID", content.get(i + 1).get("UNITID"));
                item.put("OPPUNITID", content.get(i + 1).get("OPPUNITID"));
            }
            if (!concatUnitSet.contains(concatUnit = this.getConcatUnitString(unitId = ConverterUtils.getAsString((Object)item.get("UNITID")), oppUnitId = ConverterUtils.getAsString((Object)item.get("OPPUNITID"))))) {
                concatUnitSet.add(concatUnit);
                if (concatUnitSet.size() > 1) {
                    Map<String, Object> subtotalRow = this.createSubtotalRow(debitSum, creditSum);
                    subtotalRow.put("ID", "");
                    result.add(subtotalRow);
                    debitSum = new BigDecimal(0.0);
                    creditSum = new BigDecimal(0.0);
                }
            }
            result.add(item);
            debitSum = debitSum.add(ConverterUtils.getAsBigDecimal((Object)item.get("DEBITVALUE")));
            creditSum = creditSum.add(ConverterUtils.getAsBigDecimal((Object)item.get("CREDITVALUE")));
        }
        Map<String, Object> subtotalRow = this.createSubtotalRow(debitSum, creditSum);
        subtotalRow.put("ID", "");
        result.add(subtotalRow);
        return result;
    }

    private Map<String, Object> createSubtotalRow(BigDecimal debitSum, BigDecimal creditSum) {
        HashMap<String, Object> subtotalRow = new HashMap<String, Object>();
        subtotalRow.put("UNITTITLE", GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.subtotal"));
        subtotalRow.put("DEBITVALUE", debitSum.doubleValue());
        subtotalRow.put("CREDITVALUE", creditSum.doubleValue());
        subtotalRow.put("DIFFERENCEVALUETOTAL", debitSum.subtract(creditSum).doubleValue());
        return subtotalRow;
    }

    private String getConcatUnitString(String unit, String oppUnit) {
        if (unit == null || oppUnit == null) {
            return null;
        }
        if (unit.compareTo(oppUnit) > 0) {
            return oppUnit + "|" + unit;
        }
        return unit + "|" + oppUnit;
    }

    public Set<Integer> getSetIntervalRowSet(Pagination<Map<String, Object>> dataMap) {
        List content = dataMap.getContent();
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        for (int i = 0; i < content.size(); ++i) {
            Map item = (Map)content.get(i);
            if (!"\u5c0f\u8ba1".equals(item.get("UNITTITLE"))) continue;
            rowNumber.add(i + 1);
        }
        return rowNumber;
    }
}

