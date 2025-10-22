/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceEO
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nr.jtable.util.FloatOrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.calculate.entity.FloatBalanceEO;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.onekeymerge.dao.FloatBalanceDao;
import com.jiuqi.gcreport.onekeymerge.service.IFloatBalanceService;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class FloatBalanceServiceImpl
implements IFloatBalanceService {
    private Logger logger = LoggerFactory.getLogger(FloatBalanceServiceImpl.class);
    @Autowired
    private FloatBalanceDao floatBalanceDao;

    @Override
    public void batchClearAmt(String unitCode, GcActionParamsVO paramsVO) {
        this.floatBalanceDao.batchClearAmt(unitCode, paramsVO);
    }

    @Override
    public void batchDeleteEmptyRow(String unitCode, GcActionParamsVO paramsVO) {
        this.floatBalanceDao.batchDeleteEmptyRow(unitCode, paramsVO);
    }

    @Override
    public void batchMerge(String unitCode, Map<String, BigDecimal> subjectCode2AmtMap, GcActionParamsVO paramsVO) {
        List<FloatBalanceEO> originRecords = this.floatBalanceDao.list(unitCode, paramsVO);
        Map subjectCode2FloatBalanceEOMap = originRecords.stream().collect(Collectors.toMap(FloatBalanceEO::getSubjectCode, Function.identity(), (v1, v2) -> v2));
        ArrayList<FloatBalanceEO> batchInsertList = new ArrayList<FloatBalanceEO>(64);
        ArrayList<FloatBalanceEO> batchUpdateList = new ArrayList<FloatBalanceEO>(64);
        for (Map.Entry<String, BigDecimal> entry : subjectCode2AmtMap.entrySet()) {
            FloatBalanceEO floatBalanceEO;
            String subjectCode = entry.getKey();
            BigDecimal amt = entry.getValue();
            if (null == amt || BigDecimal.ZERO.compareTo(amt) == 0) continue;
            if (subjectCode2FloatBalanceEOMap.containsKey(subjectCode)) {
                floatBalanceEO = (FloatBalanceEO)subjectCode2FloatBalanceEOMap.get(subjectCode);
                floatBalanceEO.setAmt(Double.valueOf(amt.doubleValue()));
                batchUpdateList.add(floatBalanceEO);
                continue;
            }
            floatBalanceEO = new FloatBalanceEO();
            floatBalanceEO.setSubjectCode(subjectCode);
            floatBalanceEO.setAmt(Double.valueOf(amt.doubleValue()));
            batchInsertList.add(floatBalanceEO);
        }
        this.appendBaseInfo(unitCode, paramsVO, batchInsertList);
        this.floatBalanceDao.addBatch(batchInsertList);
        this.floatBalanceDao.updateBatch(batchUpdateList);
    }

    private void appendBaseInfo(String unitCode, GcActionParamsVO paramsVO, List<FloatBalanceEO> batchInsertList) {
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        Set entityTableNames = NrTool.getEntityTableNames((String)paramsVO.getSchemeId());
        for (FloatBalanceEO floatBalanceEO : batchInsertList) {
            floatBalanceEO.setMdOrg(unitCode);
            floatBalanceEO.setDefaultPeriod(paramsVO.getPeriodStr());
            if (entityTableNames.contains("MD_GCORGTYPE")) {
                floatBalanceEO.setOrgType(paramsVO.getOrgType());
            }
            if (entityTableNames.contains("MD_CURRENCY")) {
                floatBalanceEO.setCurrency(paramsVO.getCurrency());
            }
            floatBalanceEO.setBizKeyOrder(UUIDOrderUtils.newUUIDStr());
            floatBalanceEO.setFloatOrder(Double.valueOf(floatOrderGenerator.next()));
        }
    }

    @Override
    public List<Map<String, Object>> queryOppNotBelongCurrlevelDatas(GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes) {
        return this.queryByMergeCode(true, paramsVO, tableName, currTableAllFieldCodes);
    }

    @Override
    public List<FloatBalanceEO> queryByMergeCode(GcActionParamsVO paramsVO) {
        return this.queryByMergeCode(false, paramsVO);
    }

    private List<FloatBalanceEO> queryByMergeCode(boolean isFilterByOppUnit, GcActionParamsVO paramsVO) {
        return this.floatBalanceDao.queryByMergeCode(isFilterByOppUnit, paramsVO);
    }

    @Override
    public List<Map<String, Object>> queryByMergeCode(GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes) {
        return this.queryByMergeCode(false, paramsVO, tableName, currTableAllFieldCodes);
    }

    private List<Map<String, Object>> queryByMergeCode(boolean isFilterByOppUnit, GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes) {
        return this.floatBalanceDao.queryByMergeCode(isFilterByOppUnit, paramsVO, tableName, currTableAllFieldCodes);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteAllBalance(String unitCode, String tableCode, GcActionParamsVO paramsVO) {
        this.floatBalanceDao.batchDeleteAllBalance(unitCode, tableCode, paramsVO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteAllBalanceByRelateSubjectCodes(String unitCode, String tableName, GcActionParamsVO paramsVO, List<String> subjectCodes) {
        this.floatBalanceDao.batchDeleteAllBalanceByRelateSubjectCodes(unitCode, tableName, paramsVO, subjectCodes);
    }

    @Override
    public List<Map<String, Object>> querySumFieldByMergeCode(GcActionParamsVO paramsVO, String tableName, List<String> curSubjectRewriteFields, List<String> summaryColumnCodes) {
        return this.floatBalanceDao.querySumFieldByMergeCode(paramsVO, tableName, curSubjectRewriteFields, summaryColumnCodes);
    }

    @Override
    public List<Map<String, Object>> queryOneOtherFieldsData(GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes) {
        return this.floatBalanceDao.queryOneOtherFieldsData(paramsVO, tableName, currTableAllFieldCodes);
    }

    @Override
    public Map<String, Object> batchRewriteToBalance(String unitCode, Map<String, Object> offsetItem, String rewriteFieldCode, Map<String, List<Map<String, Object>>> directChildFloatDataMap, GcActionParamsVO paramsVO, String[] masterColumnCodes, List<String> numberAndGatherFiledCodes, List<String> currTableAllFieldCodes, Map<String, BigDecimal> summaryCode2OffsetSumAmt, Set<String> summaryGroupKeySet, Map<String, String> fieldMapping) {
        Map<String, Object> sumDirectChildFloatData;
        HashMap<String, Object> fields = new HashMap<String, Object>();
        String groupKey = "";
        if (ObjectUtils.isEmpty(fieldMapping)) {
            fields.put("SUBJECTCODE", offsetItem.get("SUBJECTCODE") + "||" + offsetItem.get("SYSTEMID"));
            fields.put("OPPUNITCODE", offsetItem.get("OPPUNITID"));
            fields.put("ACCTORGCODE", offsetItem.get("UNITID"));
            fields.put("SJBZ", offsetItem.get("SJBZ"));
            fields.put("MDCODE", offsetItem.get("UNITID"));
        }
        for (int i = 0; i < masterColumnCodes.length; ++i) {
            String fieldCode = masterColumnCodes[i];
            if (fieldMapping.containsKey(fieldCode)) {
                fieldCode = fieldMapping.get(fieldCode);
            }
            if ("SUBJECTCODE".equals(fieldCode)) {
                fields.put("SUBJECTCODE", offsetItem.get("SUBJECTCODE") + "||" + offsetItem.get("SYSTEMID"));
                groupKey = groupKey + fields.get("SUBJECTCODE");
            } else {
                fields.put(fieldCode, offsetItem.get(masterColumnCodes[i]));
                groupKey = groupKey + offsetItem.get(masterColumnCodes[i]);
            }
            if (i == masterColumnCodes.length - 1) continue;
            groupKey = groupKey + "_";
        }
        this.handleEndAmtAndAccountAgeFieldValue(offsetItem, fields, rewriteFieldCode, paramsVO.getCurrency());
        if (new BigDecimal(String.valueOf((Double)fields.get(rewriteFieldCode))).compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        if (!"AMT".equals(rewriteFieldCode) || summaryCode2OffsetSumAmt == null) {
            return fields;
        }
        List<Map<String, Object>> directChildFloatDatas = directChildFloatDataMap.get(groupKey);
        this.logger.info("\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9:{},\u6570\u636e\uff1a{}", (Object)groupKey, (Object)JsonUtils.writeValueAsString(directChildFloatDatas));
        if (directChildFloatDatas == null || directChildFloatDatas.size() != 1) {
            return fields;
        }
        boolean dealOtherFieldValue = false;
        if (!summaryGroupKeySet.contains(groupKey)) {
            dealOtherFieldValue = true;
            summaryGroupKeySet.add(groupKey);
        }
        BigDecimal directChildAmtValue = (sumDirectChildFloatData = directChildFloatDatas.get(0)).get("AMT") == null ? BigDecimal.ZERO : new BigDecimal(sumDirectChildFloatData.get("AMT").toString());
        double rewriteAmtValue = (Double)fields.get("AMT");
        if (Math.abs(summaryCode2OffsetSumAmt.get(groupKey).doubleValue()) != Math.abs(directChildAmtValue.doubleValue())) {
            this.logger.info("\u5f53AMT\u5b57\u6bb5\u4e0e\u5355\u4f53\u6237\u7edd\u5bf9\u503c\u4e0d\u76f8\u7b49\u65f6\uff0c\u56de\u5199\u5b57\u6bb5\uff1a{},\u4e0b\u7ea7\u8282\u70b9\u503c\uff1a{}, \u6c47\u603b\u8282\u70b9\u503c\uff1a{}", rewriteFieldCode, directChildAmtValue.doubleValue(), summaryCode2OffsetSumAmt.get(groupKey));
            dealOtherFieldValue = false;
        }
        BigDecimal proportion = sumDirectChildFloatData == null || sumDirectChildFloatData.get("AMT") == null || summaryCode2OffsetSumAmt.get(groupKey) == null || directChildAmtValue.intValue() == 0 ? new BigDecimal(0) : summaryCode2OffsetSumAmt.get(groupKey).divide(directChildAmtValue, 8, 4);
        this.handleOtherFieldsValue(numberAndGatherFiledCodes, currTableAllFieldCodes, fields, sumDirectChildFloatData, rewriteFieldCode, proportion, dealOtherFieldValue);
        return fields;
    }

    private void handleEndAmtAndAccountAgeFieldValue(Map<String, Object> offsetItem, Map<String, Object> fields, String rewriteFieldCode, String currency) {
        String offsetDebitFiled = "OFFSET_DEBIT";
        String offsetCreditFiled = "OFFSET_CREDIT";
        BigDecimal creditValue = new BigDecimal(offsetItem.get(offsetCreditFiled).toString());
        BigDecimal debitValue = new BigDecimal(offsetItem.get(offsetDebitFiled).toString());
        BigDecimal amt = BigDecimal.ZERO.add(debitValue).subtract(creditValue);
        int subjectOrient = (Integer)offsetItem.get("SUBJECTORIENT");
        double endAmt = amt.doubleValue() * (double)subjectOrient;
        fields.put(rewriteFieldCode, endAmt);
        if (!"AMT".equals(rewriteFieldCode)) {
            fields.put("AMT", 0);
        }
    }

    private void handleOtherFieldsValue(List<String> numberAndGatherFiledCodes, List<String> currTableAllFieldCodes, Map<String, Object> fields, Map<String, Object> sumDirectChildFloatData, String rewriteFieldCode, BigDecimal proportion, boolean dealOtherFieldValue) {
        for (String fieldCode : currTableAllFieldCodes) {
            if (rewriteFieldCode.equals(fieldCode)) continue;
            if (!numberAndGatherFiledCodes.contains(fieldCode)) {
                Object fieldValue = sumDirectChildFloatData.get(fieldCode);
                fields.put(fieldCode.toUpperCase(), fieldValue);
                continue;
            }
            if (!dealOtherFieldValue) continue;
            BigDecimal amountFieldValue = sumDirectChildFloatData.get(fieldCode) == null ? new BigDecimal(0) : proportion.multiply(new BigDecimal(sumDirectChildFloatData.get(fieldCode).toString()));
            fields.put(fieldCode, amountFieldValue.setScale(2, 4));
        }
    }

    private void getOtherZbFields(Map<String, Object> fields, Map<String, Object> sumDirectChildFloatData, List<String> numberAndGatherFiledCodes, List<String> currTableAllFieldCodes) {
        for (String fieldCode : currTableAllFieldCodes) {
            if (fieldCode.equals("AMT") || fieldCode.startsWith("ACCOUNTAGE") || numberAndGatherFiledCodes.contains(fieldCode)) continue;
            Object fieldValue = sumDirectChildFloatData.get(fieldCode);
            fields.put(fieldCode.toUpperCase(), fieldValue);
        }
    }

    private void getAccountAgeFields(Map<String, Object> fields, Map<String, Object> sumDirectChildFloatData, List<String> numberAndGatherFiledCodes) {
        BigDecimal proportion = sumDirectChildFloatData == null || sumDirectChildFloatData.get("AMT") == null || new BigDecimal(sumDirectChildFloatData.get("AMT").toString()).intValue() == 0 ? new BigDecimal(0) : BigDecimal.valueOf((Double)fields.get("AMT")).divide(new BigDecimal(sumDirectChildFloatData.get("AMT").toString()), 8, 4);
        BigDecimal tailDifference = BigDecimal.valueOf((Double)fields.get("AMT"));
        int firstNotZeroData = 1;
        for (int i = 1; i <= 10; ++i) {
            BigDecimal accountAge = sumDirectChildFloatData == null || sumDirectChildFloatData.get("ACCOUNTAGE" + i) == null ? new BigDecimal(0) : new BigDecimal(sumDirectChildFloatData.get("ACCOUNTAGE" + i).toString());
            BigDecimal diffAccountAge = accountAge.multiply(proportion).setScale(2, 4);
            fields.put("ACCOUNTAGE" + i, diffAccountAge);
            if (diffAccountAge.compareTo(BigDecimal.ZERO) == 0) continue;
            tailDifference = tailDifference.subtract(diffAccountAge);
            String firstNotAccountAge = "ACCOUNTAGE" + firstNotZeroData;
            if (new BigDecimal(fields.get(firstNotAccountAge).toString()).compareTo(BigDecimal.ZERO) != 0) continue;
            firstNotZeroData = i;
        }
        if (tailDifference.compareTo(BigDecimal.ZERO) != 0) {
            String firstNotAccountAge = "ACCOUNTAGE" + firstNotZeroData;
            fields.put(firstNotAccountAge, new BigDecimal(fields.get(firstNotAccountAge).toString()).add(tailDifference));
        }
        for (String fieldCode : numberAndGatherFiledCodes) {
            if (fieldCode.equals("AMT") || fieldCode.startsWith("ACCOUNTAGE")) continue;
            BigDecimal floatOtherAmtFieldValue = sumDirectChildFloatData == null || sumDirectChildFloatData.get(fieldCode) == null ? new BigDecimal(0) : new BigDecimal(sumDirectChildFloatData.get(fieldCode).toString());
            BigDecimal otherAmtAfieldValue = floatOtherAmtFieldValue.multiply(proportion).setScale(2, 4);
            fields.put(fieldCode, otherAmtAfieldValue);
        }
    }

    @Override
    public List<Map<String, Object>> queryDifferenceIntermediateDatas(List<String> subjectCodes, GcActionParamsVO paramsVO, String tableName) {
        return this.floatBalanceDao.queryDifferenceIntermediateDatas(subjectCodes, paramsVO, tableName);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchRewrite(String unitCode, GcActionParamsVO paramsVO, List<Map<String, Object>> floatBalanceEOS, String tableName, List<String> currTableAllFieldCodes) {
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        Set entityTableNames = NrTool.getEntityTableNames((String)paramsVO.getSchemeId());
        for (Map<String, Object> floatBalanceEO : floatBalanceEOS) {
            this.appendBaseInfo(unitCode, paramsVO, floatBalanceEO, floatOrderGenerator, entityTableNames);
            this.floatBalanceDao.intertFloatBalanceDetail(tableName, currTableAllFieldCodes, floatBalanceEO);
        }
    }

    private void appendBaseInfo(String unitCode, GcActionParamsVO paramsVO, Map<String, Object> fields, FloatOrderGenerator floatOrderGenerator, Set<String> entityTableNames) {
        fields.put("MDCODE", unitCode);
        fields.put("DATATIME", paramsVO.getPeriodStr());
        fields.put("MD_GCADJTYPE", "BEFOREADJ");
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(paramsVO.getOrgType(), paramsVO.getPeriodStr(), unitCode);
            fields.put("MD_GCORGTYPE", currentUnit.getOrgTypeId());
        }
        if (entityTableNames.contains("MD_CURRENCY")) {
            fields.put("MD_CURRENCY", paramsVO.getCurrency());
        }
        fields.put("BIZKEYORDER", UUIDOrderUtils.newUUIDStr());
        fields.put("FLOATORDER", floatOrderGenerator.next());
    }
}

