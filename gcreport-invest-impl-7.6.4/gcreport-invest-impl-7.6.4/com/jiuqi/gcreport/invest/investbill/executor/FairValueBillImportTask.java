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
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.executor.BillImportData;
import com.jiuqi.gcreport.invest.investbill.executor.FairValueBillExportTask;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FairValueBillImportTask {
    @Autowired
    private FairValueBillExportTask fairValueBillExportTask;
    @Autowired
    private FairValueBillDao fairValueBillDao;
    @Autowired
    private DataModelService dataModelService;

    public StringBuilder fairValueBillImport(Map<String, Object> params, List<Object[]> excelSheetDataList, Map<String, String> unitAndOppUnitCode2SrcIdMap) {
        StringBuilder log = new StringBuilder(128);
        String mergeUnit = (String)params.get("mergeUnit");
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService noAuthOrgTool = OrgUtil.getOrgCenterService((String)orgType, (int)acctYear, (GcAuthorityType)GcAuthorityType.NONE);
        GcOrgCenterService authOrgTool = OrgUtil.getOrgCenterService((String)orgType, (int)acctYear, (GcAuthorityType)GcAuthorityType.WRITE);
        Map<String, String> subLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(authOrgTool, mergeUnit);
        Map<String, String> allLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(noAuthOrgTool, null);
        int fixedColumnsSize = this.fairValueBillExportTask.getFixedAssertsColumunsSize();
        ColumnModelDefine[] fieldDefines = this.parseExcelHeaderColumnCodes(excelSheetDataList, fixedColumnsSize);
        HashMap<String, Map<String, String>> tableName2MapDictTitle2CodeCache = new HashMap<String, Map<String, String>>(16);
        String userId = ShiroUtil.getUser().getId();
        String lastUnitCode = "";
        String lastInvestUnitCode = "";
        BillImportData billData = new BillImportData("\u516c\u5141\u4ef7\u503c");
        for (int i = 2; i < excelSheetDataList.size(); ++i) {
            Object[] oneRowData = excelSheetDataList.get(i);
            try {
                HashMap<String, Object> fixedRecord = new HashMap<String, Object>();
                HashMap<String, Object> otherRecord = new HashMap<String, Object>();
                this.parseExcelContentRow(oneRowData, fieldDefines, fixedRecord, otherRecord, subLeafUnitTitle2OrgCodeMap, allLeafUnitTitle2OrgCodeMap, fixedColumnsSize, tableName2MapDictTitle2CodeCache);
                String unitCode = (String)fixedRecord.get("UNITCODE");
                String oppUnitCode = (String)fixedRecord.get("INVESTEDUNIT");
                Assert.isNotEmpty((String)unitCode, (String)"\u672a\u89e3\u6790\u5230\u6295\u8d44\u5355\u4f4d", (Object[])new Object[0]);
                Assert.isNotEmpty((String)oppUnitCode, (String)"\u672a\u89e3\u6790\u5230\u88ab\u6295\u8d44\u5355\u4f4d", (Object[])new Object[0]);
                this.notDirectInvest(unitCode, oppUnitCode, noAuthOrgTool);
                ArrayKey key = new ArrayKey(new Object[]{unitCode, oppUnitCode});
                if (!unitCode.equals(lastUnitCode) || !oppUnitCode.equals(lastInvestUnitCode)) {
                    lastUnitCode = unitCode;
                    lastInvestUnitCode = oppUnitCode;
                    Map<String, Object> master = this.getFairValueBill(unitCode, oppUnitCode, acctYear, userId, unitAndOppUnitCode2SrcIdMap.get(unitCode + oppUnitCode));
                    billData.addMasterData(key, master, i);
                }
                if (this.checkAssertsRecord(fixedRecord)) {
                    billData.addSub1Data(key, fixedRecord);
                }
                if (!this.checkAssertsRecord(otherRecord)) continue;
                billData.addSub2Data(key, otherRecord);
                continue;
            }
            catch (IllegalArgumentException e) {
                log.append(String.format("\u516c\u5141\u4ef7\u503c\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, e.getMessage()));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u516c\u5141\u4ef7\u503c\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, e.getMessage()));
                e.printStackTrace();
            }
        }
        log.append((CharSequence)billData.saveData("GCBILL_B_FVCHBILL", noAuthOrgTool, "GC_FVCH_FIXEDITEM", "GC_FVCH_OTHERITEM"));
        return log;
    }

    private void notDirectInvest(String unitCode, String oppUnitCode, GcOrgCenterService noAuthOrgTool) {
        boolean isDirectInvest = InvestBillTool.isDirectInvest((String)unitCode, (String)oppUnitCode, (GcOrgCenterService)noAuthOrgTool);
        if (!isDirectInvest) {
            throw new BusinessRuntimeException(String.format("\u6295\u8d44\u53f0\u8d26\u4e3a\u95f4\u63a5\u6295\u8d44\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5bfc\u5165\u516c\u5141\u53f0\u8d26\u6570\u636e\uff1a\u6295\u8d44\u5355\u4f4d'%s(%s)'\u3001\u88ab\u6295\u8d44\u5355\u4f4d'%s(%s)'", noAuthOrgTool.getOrgByCode(unitCode).getTitle(), unitCode, noAuthOrgTool.getOrgByCode(oppUnitCode).getTitle(), oppUnitCode));
        }
    }

    private Map<String, Object> getFairValueBill(String unitCode, String investedUnitCode, int acctYear, String userId, String srcId) {
        List list = InvestBillTool.listByWhere((String[])new String[]{"SRCID"}, (Object[])new Object[]{srcId}, (String)"GC_FVCHBILL");
        HashMap<String, Object> fairValueRecord = new HashMap<String, Object>();
        if (CollectionUtils.isEmpty((Collection)list)) {
            fairValueRecord.put("SRCID", srcId);
            fairValueRecord.put("ACCTYEAR", acctYear);
            fairValueRecord.put("UNITCODE", unitCode);
            fairValueRecord.put("INVESTEDUNIT", investedUnitCode);
            fairValueRecord.put("CREATETIME", new Date());
            fairValueRecord.put("VER", System.currentTimeMillis());
            fairValueRecord.put("CREATEUSER", userId);
            fairValueRecord.put("DEFINECODE", "GCBILL_B_FVCHBILL");
            fairValueRecord.put("BILLCODE", this.getBillCode("GCBILL_B_FVCHBILL", unitCode));
            fairValueRecord.put("BILLDATE", new Date());
            fairValueRecord.put("BILLSTATE", BillState.SAVED);
            return fairValueRecord;
        }
        return (Map)list.get(0);
    }

    private boolean checkAssertsRecord(Map<String, Object> fixedRecord) {
        for (String key : fixedRecord.keySet()) {
            if ("UNITCODE".equals(key) || "INVESTEDUNIT".equals(key) || StringUtils.isEmpty((String)((String)fixedRecord.get(key)))) continue;
            return true;
        }
        return false;
    }

    private ColumnModelDefine[] parseExcelHeaderColumnCodes(List<Object[]> excelSheetDatas, int fixedColumnsSize) {
        if (CollectionUtils.isEmpty(excelSheetDatas)) {
            return new ColumnModelDefine[0];
        }
        Object[] secondRowTitles = excelSheetDatas.get(1);
        ColumnModelDefine[] codes = new ColumnModelDefine[secondRowTitles.length - 1];
        Map fixedColumnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_FVCH_FIXEDITEM").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        Map otherColumnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_FVCH_OTHERITEM").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        for (int i = 1; i < secondRowTitles.length; ++i) {
            codes[i - 1] = i <= fixedColumnsSize + 2 ? (ColumnModelDefine)fixedColumnTitle2CodeMap.get(secondRowTitles[i]) : (ColumnModelDefine)otherColumnTitle2CodeMap.get(secondRowTitles[i]);
        }
        return codes;
    }

    private void parseExcelContentRow(Object[] oneRowData, ColumnModelDefine[] fieldDefines, Map<String, Object> fixedRecord, Map<String, Object> otherRecord, Map<String, String> subLeafUnitTitle2OrgCodeMap, Map<String, String> allLeafUnitTitle2OrgCodeMap, int fixedColumnsSize, Map<String, Map<String, String>> tableName2MapDictTitle2CodeCache) {
        String unitCode = null;
        String investedUnitCode = null;
        for (int j = 0; j < fieldDefines.length; ++j) {
            ColumnModelDefine fieldDefine;
            Object cellValue = oneRowData[j + 1];
            if (cellValue instanceof String) {
                cellValue = ((String)cellValue).trim();
            }
            if (null == (fieldDefine = fieldDefines[j])) continue;
            if ("UNITCODE".equals(fieldDefine.getCode())) {
                Assert.isNotNull((Object)cellValue, (String)String.format("\u6295\u8d44\u5355\u4f4d\u6216\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s'", cellValue, oneRowData[j + 2]), (Object[])new Object[0]);
                unitCode = this.getUnitCode((String)cellValue, subLeafUnitTitle2OrgCodeMap);
                Assert.isNotEmpty((String)unitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + "\u6216\u6743\u9650\u4e0d\u8db3:" + cellValue), (Object[])new Object[0]);
                continue;
            }
            if ("INVESTEDUNIT".equals(fieldDefine.getCode())) {
                Assert.isNotNull((Object)cellValue, (String)String.format("\u6295\u8d44\u5355\u4f4d\u6216\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s'", oneRowData[j], cellValue), (Object[])new Object[0]);
                investedUnitCode = this.getUnitCode((String)cellValue, allLeafUnitTitle2OrgCodeMap);
                Assert.isNotEmpty((String)investedUnitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + ":" + cellValue), (Object[])new Object[0]);
                continue;
            }
            if (!StringUtils.isEmpty((String)fieldDefine.getReferTableID())) {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(fieldDefine.getReferTableID());
                if (tableModelDefine == null) continue;
                String dictTableName = tableModelDefine.getName();
                if (!StringUtils.isEmpty((String)((String)cellValue))) {
                    cellValue = NrTool.getDictCodeByTitle((String)dictTableName, (String)((String)cellValue), tableName2MapDictTitle2CodeCache);
                }
            } else if (fieldDefine.getColumnType() == ColumnModelType.DOUBLE || fieldDefine.getColumnType() == ColumnModelType.BIGDECIMAL || fieldDefine.getColumnType() == ColumnModelType.INTEGER) {
                if (StringUtils.isEmpty((String)((String)cellValue))) continue;
                cellValue = String.valueOf(cellValue).replace(",", "");
            }
            if (j < fixedColumnsSize + 2) {
                fixedRecord.put(fieldDefine.getCode(), cellValue);
                continue;
            }
            otherRecord.put(fieldDefine.getCode(), cellValue);
        }
        fixedRecord.put("UNITCODE", unitCode);
        otherRecord.put("UNITCODE", unitCode);
        fixedRecord.put("INVESTEDUNIT", investedUnitCode);
        otherRecord.put("INVESTEDUNIT", investedUnitCode);
    }

    private String getUnitCode(String unitTitle, Map<String, String> unitTitle2OrgCodeMap) {
        String[] unitTitleStrs = unitTitle.split("\\|");
        String unitCode = unitTitle2OrgCodeMap.get(unitTitleStrs[0]);
        if (unitTitleStrs.length != 1 && unitCode == null) {
            unitCode = unitTitle2OrgCodeMap.get(unitTitleStrs[1]);
        }
        return unitCode;
    }

    private Object getBillCode(String uniqueCode, String unitCode) {
        return InvestBillTool.getBillCode((String)uniqueCode, (String)unitCode);
    }

    private Map<String, String> leafUnitTitle2OrgCodeMap(GcOrgCenterService orgTool, String parentId) {
        List orgCacheVOs = orgTool.listAllOrgByParentIdContainsSelf(parentId);
        if (orgCacheVOs == null) {
            return new HashMap<String, String>(16);
        }
        HashMap<String, String> result = new HashMap<String, String>();
        for (GcOrgCacheVO org : orgCacheVOs) {
            if (!org.isLeaf()) continue;
            result.put(org.getCode(), org.getCode());
            result.put(org.getTitle(), org.getCode());
        }
        return result;
    }
}

