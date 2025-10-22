/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.invest.investbill.executor.BillImportData;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Arrays;
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
public class InvestBillImportTask {
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private DataModelService dataModelService;

    public StringBuilder investBillImport(Map<String, Object> params, List<Object[]> excelSheetDataList, Map<ArrayKey, List<Map<String, Object>>> subKey2RecordsMap, Map<String, String> unitAndOppUnitCode2SrcIdMap) {
        StringBuilder log = new StringBuilder(16);
        String mergeUnit = (String)params.get("mergeUnit");
        int acctYear = ConverterUtils.getAsIntValue((Object)params.get("acctYear"));
        int acctPeriod = ConverterUtils.getAsIntValue((Object)params.get("acctPeriod"));
        String defineCode = (String)params.get("defineCode");
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService noAuthOrgTool = OrgUtil.getOrgCenterService((String)orgType, (int)acctYear, (GcAuthorityType)GcAuthorityType.NONE);
        GcOrgCenterService authOrgTool = OrgUtil.getOrgCenterService((String)orgType, (int)acctYear, (GcAuthorityType)GcAuthorityType.WRITE);
        Map<String, String> subLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(authOrgTool, mergeUnit);
        Map<String, String> allLeafUnitTitle2OrgCodeMap = this.leafUnitTitle2OrgCodeMap(noAuthOrgTool, null);
        ColumnModelDefine[] fieldDefines = this.parseExcelHeaderColumnCodes(excelSheetDataList);
        HashMap<String, Map<String, String>> tableName2MapDictTitle2CodeCache = new HashMap<String, Map<String, String>>(16);
        String userId = ShiroUtil.getUser().getId();
        BillImportData billImportData = new BillImportData("\u6295\u8d44\u53f0\u8d26");
        billImportData.setSub1Key2RecordMap(subKey2RecordsMap);
        for (int i = excelSheetDataList.size() - 1; i > 0; --i) {
            Object[] oneRowData = excelSheetDataList.get(i);
            try {
                Map<String, Object> record = this.parseExcelContentRow(oneRowData, fieldDefines, subLeafUnitTitle2OrgCodeMap, allLeafUnitTitle2OrgCodeMap, tableName2MapDictTitle2CodeCache, noAuthOrgTool);
                String unitCode = (String)record.get("UNITCODE");
                String oppUnitCode = (String)record.get("INVESTEDUNIT");
                String[] columns = new String[]{"UNITCODE", "INVESTEDUNIT", "ACCTYEAR", "PERIOD"};
                List<Map<String, Object>> curPeriodInvestList = this.investBillDao.listByWhere(columns, new Object[]{unitCode, oppUnitCode, acctYear, acctPeriod});
                curPeriodInvestList = curPeriodInvestList.stream().filter(item -> null == item.get("DISPOSEDATE")).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(curPeriodInvestList)) {
                    record.put("ACCTYEAR", acctYear);
                    record.put("PERIOD", acctPeriod);
                    record.put("OFFSETINITFLAG", 0);
                    record.put("FAIRVALUEADJUSTFLAG", 0);
                    record.put("FAIRVALUEOFFSETFLAG", 0);
                    boolean isDirectInvest = InvestBillTool.isDirectInvest((String)unitCode, (String)((String)record.get("INVESTEDUNIT")), (GcOrgCenterService)noAuthOrgTool);
                    record.put("MERGETYPE", isDirectInvest ? InvestInfoEnum.DIRECT.getCode() : InvestInfoEnum.INDIRECT.getCode());
                    record.put("CREATETIME", new Date());
                    record.put("VER", System.currentTimeMillis());
                    record.put("CREATEUSER", userId);
                    record.put("DEFINECODE", defineCode);
                    record.put("BILLCODE", this.getBillCode(defineCode, unitCode));
                    record.put("BILLDATE", new Date());
                    String srcId = UUIDOrderUtils.newUUIDStr();
                    params.put("SRCID", srcId);
                    record.put("SRCID", srcId);
                } else {
                    record = curPeriodInvestList.get(0);
                    params.put("SRCID", record.get("SRCID"));
                    log.append(String.format("\u6295\u8d44\u53f0\u8d26\u9875\u7b7e-\u7b2c%1d\u884c\uff1a \u5df2\u7ecf\u5b58\u5728\u8be5\u53f0\u8d26\uff1a\u6295\u8d44\u5355\u4f4d'%s(%s)'\u3001\u88ab\u6295\u8d44\u5355\u4f4d'%s(%s)' \r\n", i, noAuthOrgTool.getOrgByCode(unitCode).getTitle(), unitCode, noAuthOrgTool.getOrgByCode(oppUnitCode).getTitle(), oppUnitCode));
                }
                unitAndOppUnitCode2SrcIdMap.put(unitCode + oppUnitCode, (String)record.get("SRCID"));
                ArrayKey key = new ArrayKey(new Object[]{unitCode, oppUnitCode});
                billImportData.addMasterData(key, record, i);
                continue;
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
                log.append(String.format("\u6295\u8d44\u53f0\u8d26\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, e.getMessage()));
                continue;
            }
            catch (BillException e) {
                log.append(String.format("\u6295\u8d44\u53f0\u8d26\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, this.getLogMsg(e)));
                continue;
            }
            catch (Exception e) {
                log.append(String.format("\u6295\u8d44\u53f0\u8d26\u9875\u7b7e-\u7b2c%1d\u884c\uff1a%2s \r\n", i, e.getMessage()));
                e.printStackTrace();
            }
        }
        log.append((CharSequence)billImportData.saveData(defineCode, noAuthOrgTool, "GC_INVESTBILLITEM"));
        return log;
    }

    private String getLogMsg(BillException e) {
        List checkMessages = e.getCheckMessages();
        if (CollectionUtils.isEmpty((Collection)checkMessages)) {
            return e.getMessage();
        }
        return ((CheckResult)checkMessages.get(0)).getCheckMessage();
    }

    private ColumnModelDefine[] parseExcelHeaderColumnCodes(List<Object[]> excelSheetDatas) {
        if (CollectionUtils.isEmpty(excelSheetDatas)) {
            return new ColumnModelDefine[0];
        }
        Object[] titles = excelSheetDatas.get(0);
        ColumnModelDefine[] codes = new ColumnModelDefine[titles.length];
        Map columnTitle2CodeMap = NrTool.queryAllColumnsInTable((String)"GC_INVESTBILL").stream().collect(Collectors.toMap(ColumnModelDefine::getTitle, Function.identity(), (v1, v2) -> v2));
        for (int i = 0; i < titles.length; ++i) {
            codes[i] = (ColumnModelDefine)columnTitle2CodeMap.get(titles[i]);
        }
        return codes;
    }

    private Map<String, Object> parseExcelContentRow(Object[] oneRowData, ColumnModelDefine[] fieldDefines, Map<String, String> subLeafUnitTitle2OrgCodeMap, Map<String, String> allLeafUnitTitle2OrgCodeMap, Map<String, Map<String, String>> tableName2MapDictTitle2CodeCache, GcOrgCenterService noAuthOrgTool) {
        HashMap<String, Object> record = new HashMap<String, Object>(16);
        String unitTitle = "";
        String oppUnitTitle = "";
        List<String> statusFieldTitleList = Arrays.asList("\u5904\u7f6e\u72b6\u6001", "\u516c\u5141\u4ef7\u503c\u8c03\u6574", "\u6295\u8d44\u62b5\u9500\u521d\u59cb", "\u516c\u5141\u4ef7\u503c\u8c03\u6574\u62b5\u9500\u521d\u59cb");
        for (int j = 0; j < fieldDefines.length; ++j) {
            Date date;
            String fieldCode;
            ColumnModelDefine fieldDefine;
            Object cellValue = oneRowData[j];
            if (cellValue instanceof String) {
                cellValue = ((String)cellValue).trim();
            }
            if (null == (fieldDefine = fieldDefines[j])) continue;
            if ("UNITCODE".equals(fieldDefine.getCode())) {
                unitTitle = (String)cellValue;
                Assert.isNotNull((Object)unitTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\u6216\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s'", unitTitle, oneRowData[j + 1]), (Object[])new Object[0]);
                String unitCode = this.getUnitCode(unitTitle, subLeafUnitTitle2OrgCodeMap);
                Assert.isNotEmpty((String)unitCode, (String)("\u627e\u4e0d\u89c1" + fieldDefines[j].getTitle() + "\u6216\u6743\u9650\u4e0d\u8db3:" + cellValue), (Object[])new Object[0]);
                record.put(fieldDefine.getCode(), unitCode);
                continue;
            }
            if ("INVESTEDUNIT".equals(fieldDefine.getCode())) {
                oppUnitTitle = (String)cellValue;
                Assert.isNotNull((Object)oppUnitTitle, (String)String.format("\u6295\u8d44\u5355\u4f4d\u6216\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s'", unitTitle, oppUnitTitle), (Object[])new Object[0]);
                String oppUnitCode = this.getUnitCode(oppUnitTitle, allLeafUnitTitle2OrgCodeMap);
                Assert.isNotEmpty((String)oppUnitCode, (String)String.format("\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u5408\u5e76\u5355\u4f4d: \u6295\u8d44\u5355\u4f4d'%1s', \u88ab\u6295\u8d44\u5355\u4f4d'%2s', \u6216\u8005\u586b\u5199\u5355\u4f4d\u6709\u8bef\uff0c\u627e\u4e0d\u89c1%3s: %2$s ", unitTitle, cellValue, fieldDefines[j].getTitle()), (Object[])new Object[0]);
                GcOrgCacheVO oppUnitVO = noAuthOrgTool.getOrgByCode(oppUnitCode);
                if (oppUnitVO != null && GcOrgKindEnum.DIFFERENCE.equals((Object)oppUnitVO.getOrgKind())) {
                    throw new BusinessRuntimeException("\u88ab\u6295\u8d44\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u5dee\u989d\u5355\u4f4d\uff1a\u6295\u8d44\u5355\u4f4d'" + unitTitle + "', \u88ab\u6295\u8d44\u5355\u4f4d'" + cellValue + "'");
                }
                record.put(fieldDefine.getCode(), oppUnitCode);
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
                    if (cellValue instanceof String) {
                        if ("\u5f55\u5165\u7c7b\u578b".equals(fieldDefine.getTitle()) && OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeName().equals(cellValue)) {
                            record.put(fieldDefine.getCode(), OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue());
                            continue;
                        }
                        if (statusFieldTitleList.contains(fieldDefine.getTitle()) && InvestInfoEnum.DISPOSE_DONE.getTitle().equals(cellValue)) {
                            record.put(fieldDefine.getCode(), InvestInfoEnum.DISPOSE_DONE.getCode());
                            continue;
                        }
                        cellValue = Double.valueOf(cellValue.toString().replace(",", ""));
                    } else {
                        cellValue = ((Number)cellValue).doubleValue();
                    }
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(String.format(" [%1s]\u5b57\u6bb5\u7c7b\u578b\u4e0d\u5339\u914d: \u6295\u8d44\u5355\u4f4d'%2s', \u88ab\u6295\u8d44\u5355\u4f4d'%3s'", fieldDefines[j].getTitle(), unitTitle, oppUnitTitle));
                }
                record.put(fieldDefine.getCode(), cellValue);
                continue;
            }
            if (fieldDefine.getColumnType() == ColumnModelType.DATETIME) {
                fieldCode = fieldDefine.getCode();
                date = DateUtils.parse((String)((String)cellValue), (String)"yyyy-MM");
                record.put(fieldCode, date);
                if (null == date || !"DISPOSEDATE".equals(fieldCode)) continue;
                record.put("DISPOSEFLAG", Integer.valueOf(InvestInfoEnum.DISPOSE_DONE.getCode()));
                continue;
            }
            if (fieldDefine.getColumnType() == ColumnModelType.DATETIME) {
                fieldCode = fieldDefine.getCode();
                date = DateUtils.parse((String)((String)cellValue), (String)"yyyy-MM");
                record.put(fieldCode, date);
                if (null == date || !"DISPOSEDATE".equals(fieldCode)) continue;
                record.put("DISPOSEFLAG", Integer.valueOf(InvestInfoEnum.DISPOSE_DONE.getCode()));
                continue;
            }
            if (InvestInfoEnum.DISPOSE_DONE.getTitle().equals(cellValue)) {
                record.put(fieldDefine.getCode(), Integer.valueOf(InvestInfoEnum.DISPOSE_DONE.getCode()));
                continue;
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

    private Object getBillCode(String uniqueCode, String unitCode) {
        return InvestBillTool.getBillCode((String)uniqueCode, (String)unitCode);
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

