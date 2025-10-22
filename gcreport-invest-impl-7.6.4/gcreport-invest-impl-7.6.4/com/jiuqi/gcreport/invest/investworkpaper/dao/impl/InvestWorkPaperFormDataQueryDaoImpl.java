/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.invest.investworkpaper.dao.impl;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.investworkpaper.dao.InvestWorkPaperFormDataQueryDao;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvestWorkPaperFormDataQueryDaoImpl
implements InvestWorkPaperFormDataQueryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Map<String, String>> getNrData(InvestWorkPaperQueryCondition condition, Map<String, List<String>> orgTypeId2MdCodeList, Map<String, String> mergeUnit2InvestedUnitCodeMap, Map<String, List<String>> zbTableName2FieldNamesMap) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MDCODE, %1$s \n");
        sql.append("  FROM %2$s   \n");
        sql.append(" WHERE 1=1 \n");
        sql.append("   AND MD_GCORGTYPE = ? \n");
        sql.append("   AND DATATIME = ? \n");
        sql.append("   AND MD_CURRENCY = ? \n");
        sql.append("   AND %3$s \n");
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            sql.append(" AND ADJUST= '" + condition.getSelectAdjustCode() + "' \n");
        }
        HashMap<String, Map<String, String>> orgCode2ZbFieldNameAndZbValueMap = new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, List<String>> zbTableName2FieldNamesEntry : zbTableName2FieldNamesMap.entrySet()) {
            List<String> zbFieldNames = zbTableName2FieldNamesEntry.getValue();
            String zbFieldNamesStr = zbFieldNames.stream().collect(Collectors.joining(","));
            for (Map.Entry<String, List<String>> orgTypeId2MdCodeEntry : orgTypeId2MdCodeList.entrySet()) {
                ArrayList<Object> params = new ArrayList<Object>();
                params.add(orgTypeId2MdCodeEntry.getKey());
                params.add(condition.getPeriodStr());
                params.add(condition.getCurrencyCode());
                List<String> mdCodeList = orgTypeId2MdCodeEntry.getValue();
                String executeSql = String.format(sql.toString(), zbFieldNamesStr, zbTableName2FieldNamesEntry.getKey(), SqlUtils.getConditionOfIdsUseOr(mdCodeList, (String)"MDCODE"));
                Map<String, Map<String, String>> orgCode2ZbFieldNameAndZbValMap = this.queryFormData(mergeUnit2InvestedUnitCodeMap, executeSql, zbFieldNames, params);
                orgCode2ZbFieldNameAndZbValMap.forEach((orgCode, zbFieldNameAndZbValueMap) -> {
                    if (zbFieldNameAndZbValueMap == null) {
                        return;
                    }
                    if (orgCode2ZbFieldNameAndZbValueMap.containsKey(orgCode)) {
                        ((Map)orgCode2ZbFieldNameAndZbValueMap.get(orgCode)).putAll(zbFieldNameAndZbValueMap);
                    } else {
                        orgCode2ZbFieldNameAndZbValueMap.put((String)orgCode, (Map<String, String>)zbFieldNameAndZbValueMap);
                    }
                });
            }
        }
        return orgCode2ZbFieldNameAndZbValueMap;
    }

    private Map<String, Map<String, String>> queryFormData(Map<String, String> mergeUnit2InvestedUnitCodeMap, String sql, List<String> zbFieldNames, List<Object> params) {
        return (Map)this.jdbcTemplate.query(sql, rs -> {
            HashMap result = new HashMap();
            while (rs.next()) {
                String mdCode = rs.getString("MDCODE");
                mdCode = StringUtils.isEmpty((CharSequence)((CharSequence)mergeUnit2InvestedUnitCodeMap.get(mdCode))) ? mdCode : (String)mergeUnit2InvestedUnitCodeMap.get(mdCode);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                for (String fieldName : zbFieldNames) {
                    if (rs.getString(fieldName) == null) continue;
                    hashMap.put(fieldName, NumberUtils.doubleToString((double)rs.getDouble(fieldName)));
                }
                result.put(mdCode, hashMap);
            }
            return result;
        }, params.toArray());
    }
}

