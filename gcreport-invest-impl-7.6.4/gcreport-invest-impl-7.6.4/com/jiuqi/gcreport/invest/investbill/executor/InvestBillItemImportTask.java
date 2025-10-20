/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestBillItemImportTask {
    @Autowired
    private DataModelService dataModelService;

    public Map<ArrayKey, List<Map<String, Object>>> investBillImport(Map<String, Object> params, List<Object[]> excelSheetDataList, StringBuilder log) {
        HashMap<ArrayKey, List<Map<String, Object>>> subKey2RecordMap = new HashMap<ArrayKey, List<Map<String, Object>>>(32);
        String mergeUnit = (String)params.get("mergeUnit");
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService tool = OrgUtil.getOrgCenterService((String)orgType, (int)acctYear, (GcAuthorityType)GcAuthorityType.NONE);
        GcOrgCenterService authTool = OrgUtil.getOrgCenterService((String)orgType, (int)acctYear, (GcAuthorityType)GcAuthorityType.WRITE);
        Map<String, String> subLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(authTool, mergeUnit);
        Map<String, String> allLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(tool, null);
        ColumnModelDefine[] fieldDefines = this.parseExcelHeaderColumnCodes(excelSheetDataList);
        HashMap<String, Map<String, String>> tableName2MapDictTitle2CodeCache = new HashMap<String, Map<String, String>>(16);
        int acctPeriod = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        for (int i = 1; i < excelSheetDataList.size(); ++i) {
            Object[] oneRowData = excelSheetDataList.get(i);
            try {
                Map<String, Object> record = this.parseExcelContentRow(oneRowData, fieldDefines, subLeafUnitTitle2OrgCodeMap, allLeafUnitTitle2OrgCodeMap, tableName2MapDictTitle2CodeCache);
                LocalDate changeDate = LocalDate.parse(ConverterUtils.getAsString((Object)record.get("CHANGEDATE")), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (changeDate.getYear() != acctYear || changeDate.getYear() == acctYear && changeDate.getMonthValue() > acctPeriod) {
                    log.append(String.format("\u6295\u8d44\u53f0\u8d26\u53d8\u52a8\u9875\u7b7e-\u7b2c%d\u884c\u53ea\u5141\u8bb8\u5bfc\u5165\u6240\u9009\u5e74\u5ea6\u4e00\u6708\u5230\u5f53\u671f\u7684\u6570\u636e\uff0c\u5386\u53f2\u5e74\u5ea6\u6216\u4ee5\u540e\u671f\u5b50\u8868\u4e0d\u5141\u8bb8\u5bfc\u5165\u5f53\u671f \n", i));
                    continue;
                }
                String unitCode = (String)record.get("UNITCODE");
                String oppUnitCode = (String)record.get("INVESTEDUNIT");
                Assert.isNotEmpty((String)unitCode, (String)"\u672a\u89e3\u6790\u5230\u6295\u8d44\u5355\u4f4d", (Object[])new Object[0]);
                Assert.isNotEmpty((String)oppUnitCode, (String)"\u672a\u89e3\u6790\u5230\u88ab\u6295\u8d44\u5355\u4f4d", (Object[])new Object[0]);
                this.setSrcTypeVal(acctYear, record);
                record.put("VER", System.currentTimeMillis());
                ArrayKey key = new ArrayKey(new Object[]{unitCode, oppUnitCode});
                ArrayList<Map<String, Object>> records = (ArrayList<Map<String, Object>>)subKey2RecordMap.get(key);
                if (null == records) {
                    records = new ArrayList<Map<String, Object>>();
                    subKey2RecordMap.put(key, records);
                }
                records.add(record);
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u6295\u8d44\u53f0\u8d26\u53d8\u52a8\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u6295\u8d44\u53f0\u8d26\u53d8\u52a8\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, e.getMessage()));
                e.printStackTrace();
            }
        }
        return subKey2RecordMap;
    }

    private void setSrcTypeVal(int acctYear, Map<String, Object> record) {
        String changeDateStr = ConverterUtils.getAsString((Object)record.get("CHANGEDATE"));
        if (StringUtils.isEmpty((String)changeDateStr)) {
            return;
        }
        String[] split = changeDateStr.split("-");
        if (split.length > 0 && ConverterUtils.getAsIntValue((Object)split[0]) < acctYear) {
            record.put("SRCTYPE", OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue());
        }
    }

    private ColumnModelDefine[] parseExcelHeaderColumnCodes(List<Object[]> excelSheetDatas) {
        if (CollectionUtils.isEmpty(excelSheetDatas)) {
            return new ColumnModelDefine[0];
        }
        Object[] titles = excelSheetDatas.get(0);
        ColumnModelDefine[] codes = new ColumnModelDefine[titles.length];
        Map columnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_INVESTBILLITEM").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        Map materColumnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_INVESTBILL").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        for (int i = 0; i < titles.length; ++i) {
            codes[i] = (ColumnModelDefine)columnTitle2CodeMap.get(titles[i]);
            if (null != codes[i]) continue;
            codes[i] = (ColumnModelDefine)materColumnTitle2CodeMap.get(titles[i]);
        }
        return codes;
    }

    private Map<String, Object> parseExcelContentRow(Object[] oneRowData, ColumnModelDefine[] fieldDefines, Map<String, String> subLeafUnitTitle2OrgCodeMap, Map<String, String> allLeafUnitTitle2OrgCodeMap, Map<String, Map<String, String>> tableName2MapDictTitle2CodeCache) {
        HashMap<String, Object> record = new HashMap<String, Object>(16);
        String unitTitle = "";
        String oppUnitTitle = "";
        for (int j = 0; j < fieldDefines.length; ++j) {
            String unitCode;
            Object cellValue = oneRowData[j];
            ColumnModelDefine fieldDefine = fieldDefines[j];
            if (null == fieldDefine) continue;
            if ("UNITCODE".equals(fieldDefine.getCode())) {
                unitTitle = (String)cellValue;
                Assert.isNotNull((Object)unitTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\u6216\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s'", unitTitle, oneRowData[j + 1]), (Object[])new Object[0]);
                unitCode = this.getUnitCode(unitTitle, subLeafUnitTitle2OrgCodeMap);
                Assert.isNotEmpty((String)unitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + "\u6216\u6743\u9650\u4e0d\u8db3:" + cellValue), (Object[])new Object[0]);
                record.put(fieldDefine.getCode(), unitCode);
                continue;
            }
            if ("INVESTEDUNIT".equals(fieldDefine.getCode())) {
                oppUnitTitle = (String)cellValue;
                Assert.isNotNull((Object)oppUnitTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\u6216\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s'", unitTitle, oppUnitTitle), (Object[])new Object[0]);
                unitCode = this.getUnitCode(oppUnitTitle, allLeafUnitTitle2OrgCodeMap);
                Assert.isNotEmpty((String)unitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + ":" + cellValue), (Object[])new Object[0]);
                record.put(fieldDefine.getCode(), unitCode);
                continue;
            }
            if (!StringUtils.isEmpty((String)fieldDefine.getReferTableID())) {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(fieldDefine.getReferTableID());
                if (tableModelDefine == null) continue;
                String dictTableName = tableModelDefine.getName();
                record.put(fieldDefine.getCode(), NrTool.getDictCodeByTitle((String)dictTableName, (String)((String)cellValue), tableName2MapDictTitle2CodeCache));
                continue;
            }
            if (fieldDefine.getColumnType() == ColumnModelType.DOUBLE || fieldDefine.getColumnType() == ColumnModelType.BIGDECIMAL || fieldDefine.getColumnType() == ColumnModelType.INTEGER) {
                if (StringUtils.isEmpty((String)((String)cellValue))) continue;
                try {
                    cellValue = cellValue instanceof String ? Double.valueOf(cellValue.toString().replace(",", "")) : Double.valueOf(((Number)cellValue).doubleValue());
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(String.format(" [%1s]\u5b57\u6bb5\u7c7b\u578b\u4e0d\u5339\u914d: \u6295\u8d44\u5355\u4f4d'%2s', \u88ab\u6295\u8d44\u5355\u4f4d'%3s'", fieldDefines[j].getTitle(), unitTitle, oppUnitTitle));
                }
                record.put(fieldDefine.getCode(), cellValue);
                continue;
            }
            if (fieldDefine.getColumnType() == ColumnModelType.DATETIME) {
                try {
                    if (!StringUtils.isEmpty((String)((String)cellValue))) {
                        new SimpleDateFormat("yyyy-MM").parse((String)cellValue);
                    }
                    record.put(fieldDefine.getCode(), cellValue);
                    continue;
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(String.format(" [%1s]\u5b57\u6bb5\u7c7b\u578b\u4e0d\u5339\u914d: \u6295\u8d44\u5355\u4f4d'%2s', \u88ab\u6295\u8d44\u5355\u4f4d'%3s'", fieldDefines[j].getTitle(), unitTitle, oppUnitTitle));
                }
            }
            record.put(fieldDefine.getCode(), cellValue);
        }
        return record;
    }

    private String getUnitCode(String unitTitle, Map<String, String> unitTitle2OrgCodeMap) {
        String[] unitTitleStrs = unitTitle.split("\\|");
        String unitCode = unitTitle2OrgCodeMap.get(unitTitleStrs[0]);
        if (unitTitleStrs.length != 1 && unitCode == null) {
            unitCode = unitTitle2OrgCodeMap.get(unitTitleStrs[1]);
        }
        return unitCode;
    }

    private Map<String, String> leafUnitTitle2OrgCodeMap(GcOrgCenterService orgTool, String parentId) {
        List orgCacheVOs = orgTool.listAllOrgByParentIdContainsSelf(parentId);
        if (orgCacheVOs == null) {
            return new HashMap<String, String>(16);
        }
        Map<String, String> unitTitle2UnitIdMap = orgCacheVOs.stream().filter(org -> null != org && org.isLeaf()).collect(Collectors.toMap(GcOrgCacheVO::getTitle, GcOrgCacheVO::getId, (v1, v2) -> v2));
        unitTitle2UnitIdMap.putAll(orgCacheVOs.stream().filter(org -> null != org && org.isLeaf()).collect(Collectors.toMap(GcOrgCacheVO::getId, GcOrgCacheVO::getId, (v1, v2) -> v2)));
        return unitTitle2UnitIdMap;
    }
}

