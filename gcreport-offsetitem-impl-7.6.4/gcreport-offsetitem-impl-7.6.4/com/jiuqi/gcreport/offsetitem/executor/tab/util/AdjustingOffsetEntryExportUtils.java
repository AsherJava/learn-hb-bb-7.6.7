/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.util.DataFieldUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.gcreport.offsetitem.executor.tab.util;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.util.DataFieldUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AdjustingOffsetEntryExportUtils {
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private GcOffSetAppOffsetService gcOffSetItemAdjustService;
    @Autowired
    private GcInputDataOffsetItemService gcInputDataOffsetItemService;
    private Map<String, String> ruleMap = new ConcurrentHashMap<String, String>();
    private final Map<String, String> amtColumnCodeMap = new ConcurrentHashMap<String, String>();

    public String[] msg(String[] keys) {
        String[] msgs = new String[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            msgs[i] = GcI18nUtil.getMessage((String)keys[i]);
        }
        return msgs;
    }

    public void addMergedRegion(ExportExcelSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd && colStart == colEnd) {
            return;
        }
        CellRangeAddress region = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.getCellRangeAddresses().add(region);
    }

    public String getRuleTitle(String id) {
        if (id == null) {
            return null;
        }
        if (this.ruleMap.containsKey(id)) {
            return this.ruleMap.get(id);
        }
        AbstractUnionRule ruleVO = this.unionRuleService.selectUnionRuleDTOById(id);
        this.ruleMap.put(id, ruleVO == null ? "" : ruleVO.getLocalizedName());
        return this.ruleMap.get(id);
    }

    public BitSet getAmtCellColumns(List<String> otherShowColumns, String systemId, String dataSource, int[] amtColumns, List<String> dimensionNumberList, int index) {
        List<DesignFieldDefineVO> unOffsetColumnSelects = this.gcInputDataOffsetItemService.listUnOffsetColumnSelects(systemId, dataSource);
        List<DesignFieldDefineVO> offsetColumnSelects = this.gcOffSetItemAdjustService.listOffsetColumnSelects();
        unOffsetColumnSelects.addAll(offsetColumnSelects);
        BitSet amtCellColumns = new BitSet();
        for (int amtColumn : amtColumns) {
            amtCellColumns.set(amtColumn);
        }
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            Object object = otherShowColumns.iterator();
            while (object.hasNext()) {
                String otherShowColumn = (String)object.next();
                boolean isRepeat = false;
                for (DesignFieldDefineVO designFieldDefineVO : unOffsetColumnSelects) {
                    if (!otherShowColumn.equals(designFieldDefineVO.getKey()) || isRepeat) continue;
                    isRepeat = true;
                    if (!DataFieldUtils.isNumber((ColumnModelType)designFieldDefineVO.getType())) continue;
                    amtCellColumns.set(index);
                    dimensionNumberList.add(otherShowColumn);
                }
                ++index;
            }
        }
        return amtCellColumns;
    }

    public boolean isAmt(String columnStr) {
        if (this.amtColumnCodeMap.size() == 0) {
            this.initAmtColumnCodeMap();
        }
        return this.amtColumnCodeMap.containsKey(columnStr);
    }

    private void initAmtColumnCodeMap() {
        this.amtColumnCodeMap.put("OFFSETDEBIT", "OFFSETDEBIT");
        this.amtColumnCodeMap.put("OFFSETCREDIT", "OFFSETCREDIT");
        this.amtColumnCodeMap.put("DIFF", "DIFF");
        this.amtColumnCodeMap.put("DEBITVALUE", "DEBITVALUE");
        this.amtColumnCodeMap.put("CREDITVALUE", "CREDITVALUE");
        this.amtColumnCodeMap.put("DIFFERENCEVALUETOTAL", "DIFFERENCEVALUETOTAL");
        this.amtColumnCodeMap.put("OFFSETDEBITVALUE", "OFFSETDEBITVALUE");
        this.amtColumnCodeMap.put("OFFSETCREDITVALUE", "OFFSETCREDITVALUE");
        this.amtColumnCodeMap.put("OFFSETDIFFVALUE", "OFFSETDIFFVALUE");
        this.amtColumnCodeMap.put("UNOFFSETDEBITVALUE", "UNOFFSETDEBITVALUE");
        this.amtColumnCodeMap.put("UNOFFSETCREDITVALUE", "UNOFFSETCREDITVALUE");
        this.amtColumnCodeMap.put("UNOFFSETDIFFVALUE", "UNOFFSETDIFFVALUE");
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

    private List<Map<String, Object>> addSubtotalRowByRule(List<Map<String, Object>> content) {
        BigDecimal debitSum = new BigDecimal(0.0);
        BigDecimal creditSum = new BigDecimal(0.0);
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashSet<String> ruleIdSet = new HashSet<String>();
        for (Map<String, Object> item : content) {
            String ruleId = ConverterUtils.getAsString((Object)item.get("UNIONRULEID"));
            if (!ruleIdSet.contains(ruleId)) {
                ruleIdSet.add(ruleId);
                if (ruleIdSet.size() > 1) {
                    result.add(this.createSubtotalRow(debitSum, creditSum));
                    debitSum = new BigDecimal(0.0);
                    creditSum = new BigDecimal(0.0);
                }
            }
            result.add(item);
            debitSum = debitSum.add(ConverterUtils.getAsBigDecimal((Object)item.get("DEBITVALUE")));
            creditSum = creditSum.add(ConverterUtils.getAsBigDecimal((Object)item.get("CREDITVALUE")));
        }
        result.add(this.createSubtotalRow(debitSum, creditSum));
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

    public void adaptMsg(Map<String, Object> stringObjectMap) {
        if (stringObjectMap.containsKey("MEMO")) {
            if ("\u5e73\u8861\u6570".equals(stringObjectMap.get("MEMO"))) {
                stringObjectMap.put("MEMO", GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.equiNumber"));
            }
            if ("\u7ed3\u8f6c\u635f\u76ca".equals(stringObjectMap.get("MEMO"))) {
                stringObjectMap.put("MEMO", GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.lossGain"));
            }
        }
    }
}

