/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import java.util.Collection;
import java.util.UUID;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class NativeSqlUtils {
    private static final int IN_MAXNUM = 500;

    public static TempTableCondition getConditionOfIds(Collection<String> ids, String fieldName) {
        String condition = NativeSqlUtils.preGetConditionOfIds(ids, fieldName);
        if (!StringUtils.isEmpty(condition)) {
            return new TempTableCondition(condition, null);
        }
        String batchId = UUID.randomUUID().toString();
        IdTemporaryTableUtils.insertTempDb((String)batchId, ids);
        condition = fieldName + " in (select idtemp.tbId from " + "GC_IDTEMPORARY" + " idtemp where idtemp.groupId = '" + batchId + "') \n";
        return new TempTableCondition(condition, batchId);
    }

    public static String getConditionOfIdsUseOr(Collection<String> ids, String fieldName) {
        String condition = NativeSqlUtils.preGetConditionOfIds(ids, fieldName);
        if (!StringUtils.isEmpty(condition)) {
            return condition;
        }
        String beginStr = fieldName + " in (";
        StringBuilder result = new StringBuilder("(" + beginStr);
        int index = 0;
        for (String id : ids) {
            if (id == null) continue;
            result.append(String.format("'%s',", id));
            if (500 != ++index) continue;
            result.delete(result.length() - 1, result.length()).append(") ").append("or").append(" ").append(beginStr).append(String.format("'%s',", id));
            index = 0;
        }
        result.delete(result.length() - 1, result.length()).append(")) \n");
        return result.toString();
    }

    private static String preGetConditionOfIds(Collection<String> ids, String fieldName) {
        if (StringUtils.isEmpty(fieldName) || CollectionUtils.isEmpty(ids)) {
            return "1 = 2 \n";
        }
        if (ids.size() == 1) {
            return fieldName + " = '" + ids.iterator().next() + "' \n";
        }
        if (ids.size() < 500) {
            StringBuilder sql = new StringBuilder();
            sql.append(fieldName).append(" in (");
            ids.forEach(id -> sql.append("'").append((String)id).append("',"));
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") \n");
            return sql.toString();
        }
        return "";
    }
}

