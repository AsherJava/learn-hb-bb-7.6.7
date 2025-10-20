/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.np.sql.da.SQLUniqueConstraintViolationException
 */
package com.jiuqi.gcreport.offsetitem.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetVchrItemBalanceDao;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemBalanceEO;
import com.jiuqi.np.sql.da.SQLUniqueConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class GcOffSetVchrItemBalanceDaoImpl
extends GcDbSqlGenericDAO<GcOffSetVchrItemBalanceEO, String>
implements GcOffSetVchrItemBalanceDao {
    private static final String SQL_UPDATE_RESET_CURRENT_PERIOD_BALANCE = " update GC_OFFSETVCHRITEMBLC  t \n set debit%2$s = %3$s, credit%2$s = %4$s, cf%2$s = %5$s where 1=1 %6$s \n";
    private static final String SQL_QUERY_BEFORE_PERIOD_BALANCE = " select (case when t.cf%2$s is null then 0 else t.cf%2$s end) as BEFORECF \n from GC_OFFSETVCHRITEMBLC  t \n  where 1=1 %3$s \n";
    private static final String SQL_UPDATE_CURRENT_PERIOD_BALANCE = " update GC_OFFSETVCHRITEMBLC  t \n set debit%2$s = t.debit%2$s + %3$s, \n \t\tcredit%2$s = t.credit%2$s + %4$s, \n \t\tcf%2$s = (t.debit%2$s - t.credit%2$s + %5$s)*(%6$s) + %7$s \n where 1=1 %8$s \n";
    private static final String SQL_UPDATE_AFTER_PERIOD_BALANCE = " update GC_OFFSETVCHRITEMBLC  t \n set cf%2$s = (%3$s)*(%4$s) + t.cf%2$s where 1=1 %5$s \n";

    public GcOffSetVchrItemBalanceDaoImpl() {
        super(GcOffSetVchrItemBalanceEO.class);
    }

    @Override
    public void batchInsertBalanceWhenNotExists(List<GcOffSetVchrItemAdjustEO> groupItemEOs) {
        for (GcOffSetVchrItemAdjustEO groupItemEO : groupItemEOs) {
            try {
                String[] offSetCurrs;
                GcOffSetVchrItemBalanceEO insertBalanceEO = new GcOffSetVchrItemBalanceEO();
                insertBalanceEO.setId(UUIDUtils.newUUIDStr());
                GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.stream().forEach(balanceDimensionField -> {
                    Object fieldValue = ReflectionUtils.getFieldValue((Object)groupItemEO, (String)balanceDimensionField);
                    ReflectionUtils.setFieldValue((Object)((Object)insertBalanceEO), (String)balanceDimensionField, (Object)fieldValue);
                });
                for (String offSetCurr : offSetCurrs = new String[]{"CNY", "HKD", "USD"}) {
                    ReflectionUtils.setFieldValue((Object)((Object)insertBalanceEO), (String)("debit" + offSetCurr), (Object)0.0);
                    ReflectionUtils.setFieldValue((Object)((Object)insertBalanceEO), (String)("credit" + offSetCurr), (Object)0.0);
                    ReflectionUtils.setFieldValue((Object)((Object)insertBalanceEO), (String)("cf" + offSetCurr), (Object)0.0);
                }
                this.save(insertBalanceEO);
            }
            catch (SQLUniqueConstraintViolationException sQLUniqueConstraintViolationException) {}
        }
    }

    @Override
    public void updateCurrentPeriodBalance(boolean isPlus, GcOffSetVchrItemAdjustEO groupItemEO) {
    }

    private void updateBalanceByAffectReset(boolean isPlus, GcOffSetVchrItemAdjustEO groupItemEO) {
        String offSetCurr = groupItemEO.getOffSetCurr().toUpperCase();
        double offSetDebitValue = (Double)ReflectionUtils.getFieldValue((Object)groupItemEO, (String)"offSetDebit");
        double offSetCreditValue = (Double)ReflectionUtils.getFieldValue((Object)groupItemEO, (String)"offSetCredit");
        String subjectCode = groupItemEO.getSubjectCode();
        GcBaseData subjectVo = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCOUNTSUBJECT", subjectCode);
        int orient = Integer.valueOf(subjectVo.getFieldVal("ORIENT").toString());
        Map<String, String> paramTypeNames = this.getParamTypeNamesByFieldName();
        ArrayList<String> currentParamList = new ArrayList<String>();
        LinkedHashMap<String, Object> whereCurrentCondValueMap = new LinkedHashMap<String, Object>();
        int size = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.size();
        for (int position = 0; position < size; ++position) {
            String fieldName = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.get(position);
            currentParamList.add("@" + fieldName + " " + paramTypeNames.get(fieldName));
            whereCurrentCondValueMap.put(fieldName, ReflectionUtils.getFieldValue((Object)groupItemEO, (String)fieldName));
        }
        StringBuilder currentParams = new StringBuilder();
        StringBuilder whereCurrentCondition = new StringBuilder();
        for (int position = 0; position < size; ++position) {
            String fieldName = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.get(position);
            currentParams.append((String)currentParamList.get(position));
            if (position < size - 1) {
                currentParams.append(",");
            }
            if (whereCurrentCondValueMap.get(fieldName) == null) {
                whereCurrentCondition.append(" and t.").append(fieldName).append(" is null ");
                continue;
            }
            whereCurrentCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName);
        }
        String updateCurrentPeriodBalanceSql = isPlus ? String.format(SQL_UPDATE_RESET_CURRENT_PERIOD_BALANCE, currentParams, offSetCurr, offSetDebitValue, offSetCreditValue, (offSetDebitValue - offSetCreditValue) * (double)orient, whereCurrentCondition) : String.format(SQL_UPDATE_RESET_CURRENT_PERIOD_BALANCE, currentParams, offSetCurr, 0, 0, 0, whereCurrentCondition);
        try {
            this.execute(updateCurrentPeriodBalanceSql, new Object[]{whereCurrentCondValueMap.values()});
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5f53\u524d\u671f\u4f59\u989d\u8868\u66f4\u65b0\u5931\u8d25", (Throwable)e);
        }
    }

    private void updateBalanceByAffectDetail(boolean isPlus, GcOffSetVchrItemAdjustEO groupItemEO) {
        Map<String, String> paramTypeNames = this.getParamTypeNamesByFieldName();
        ArrayList<String> currentParamList = new ArrayList<String>();
        LinkedHashMap<String, Object> whereCurrentCondValueMap = new LinkedHashMap<String, Object>();
        int size = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.size();
        for (int position = 0; position < size; ++position) {
            String fieldName = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.get(position);
            currentParamList.add("@" + fieldName + " " + paramTypeNames.get(fieldName));
            whereCurrentCondValueMap.put(fieldName, ReflectionUtils.getFieldValue((Object)groupItemEO, (String)fieldName));
        }
        StringBuilder currentParams = new StringBuilder();
        StringBuilder whereCurrentCondition = new StringBuilder();
        StringBuilder whereBeforeCondition = new StringBuilder();
        StringBuilder whereBfCondition = new StringBuilder();
        for (int position = 0; position < size; ++position) {
            String fieldName = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.get(position);
            currentParams.append((String)currentParamList.get(position));
            if (position < size - 1) {
                currentParams.append(",");
            }
            if (fieldName.equalsIgnoreCase("acctPeriod")) {
                whereCurrentCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName);
                whereBeforeCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName).append("-1");
                whereBfCondition.append(" and t.").append(fieldName).append(" = 0");
                continue;
            }
            if (whereCurrentCondValueMap.get(fieldName) == null) {
                whereCurrentCondition.append(" and t.").append(fieldName).append(" is null ");
                whereBeforeCondition.append(" and t.").append(fieldName).append(" is null ");
                whereBfCondition.append(" and t.").append(fieldName).append(" is null ");
                continue;
            }
            whereCurrentCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName);
            whereBeforeCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName);
            whereBfCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName);
        }
        String offSetCurr = groupItemEO.getOffSetCurr().toUpperCase();
        Object[] whereCurrentCondValues = whereCurrentCondValueMap.values().toArray();
        double beforePeriodCf = 0.0;
        String queryBeforePeriodBalanceSql = String.format(SQL_QUERY_BEFORE_PERIOD_BALANCE, currentParams, offSetCurr, whereBeforeCondition);
        List beforePeriodBalanceCfRecord = this.selectMap(queryBeforePeriodBalanceSql, whereCurrentCondValues);
        int recordCount = beforePeriodBalanceCfRecord.size();
        if (recordCount > 1) {
            throw new BusinessRuntimeException("\u540c\u4e00\u552f\u5ea6\u4e0b\u5b58\u5728\u591a\u6761\u4f59\u989d\u8868\u6570\u636e\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u3002");
        }
        if (!beforePeriodBalanceCfRecord.isEmpty()) {
            beforePeriodCf = (Double)((Map)beforePeriodBalanceCfRecord.get(0)).get("BEFORECF");
        } else {
            String queryBfBalanceSql = String.format(SQL_QUERY_BEFORE_PERIOD_BALANCE, currentParams, offSetCurr, whereBfCondition);
            List bfBalanceCfRecord = this.selectMap(queryBfBalanceSql, whereCurrentCondValues);
            if (!bfBalanceCfRecord.isEmpty()) {
                beforePeriodCf = (Double)((Map)bfBalanceCfRecord.get(0)).get("BEFORECF");
            }
        }
        int sign = isPlus ? 1 : -1;
        double offSetDebitValue = (Double)ReflectionUtils.getFieldValue((Object)groupItemEO, (String)"offSetDebit") * (double)sign;
        double offSetCreditValue = (Double)ReflectionUtils.getFieldValue((Object)groupItemEO, (String)"offSetCredit") * (double)sign;
        String subjectCode = groupItemEO.getSubjectCode();
        GcBaseData subjectVo = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCOUNTSUBJECT", subjectCode);
        int orient = Integer.valueOf(subjectVo.getFieldVal("ORIENT").toString());
        double dcValue = offSetDebitValue - offSetCreditValue;
        String updateCurrentPeriodBalanceSql = String.format(SQL_UPDATE_CURRENT_PERIOD_BALANCE, currentParams, offSetCurr, offSetDebitValue, offSetCreditValue, dcValue, orient, beforePeriodCf, whereCurrentCondition);
        try {
            this.execute(updateCurrentPeriodBalanceSql, whereCurrentCondValues);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5f53\u524d\u671f\u4f59\u989d\u8868\u66f4\u65b0\u5931\u8d25", (Throwable)e);
        }
        StringBuilder afterParams = new StringBuilder();
        StringBuilder whereAfterCondition = new StringBuilder();
        for (int position = 0; position < size; ++position) {
            String fieldName = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.get(position);
            afterParams.append((String)currentParamList.get(position));
            if (position < size - 1) {
                afterParams.append(",");
            }
            if (fieldName.equals("acctPeriod")) {
                whereAfterCondition.append(" and t.acctPeriod > @").append(fieldName);
                continue;
            }
            if (whereCurrentCondValueMap.get(fieldName) == null) {
                whereAfterCondition.append(" and t.").append(fieldName).append(" is null ");
                continue;
            }
            whereAfterCondition.append(" and t.").append(fieldName).append(" = @").append(fieldName);
        }
        String updateAfterPeriodBalanceSql = String.format(SQL_UPDATE_AFTER_PERIOD_BALANCE, afterParams, offSetCurr, dcValue, orient, whereAfterCondition);
        try {
            this.execute(updateAfterPeriodBalanceSql, whereCurrentCondValues);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u4ee5\u540e\u671f\u4f59\u989d\u8868\u5931\u8d25", (Throwable)e);
        }
    }

    private Map<String, String> getParamTypeNamesByFieldName() {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> iterator = GcOffSetVchrItemBalanceEO.BALANCE_DIMENSION_FIELDS.iterator();
        block15: while (iterator.hasNext()) {
            String fieldName;
            switch (fieldName = iterator.next()) {
                case "unitId": 
                case "oppUnitId": 
                case "inputUnitId": 
                case "ruleId": {
                    map.put(fieldName, "guid");
                    continue block15;
                }
                case "acctPeriod": 
                case "acctYear": {
                    map.put(fieldName, "int");
                    continue block15;
                }
                case "subjectCode": 
                case "gcBusinessTypeCode": {
                    map.put(fieldName, "string");
                    continue block15;
                }
            }
            map.put(fieldName, "string");
        }
        return map;
    }
}

