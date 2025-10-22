/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.financialcheckcore.sqlutils;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.dao.ReltxIdTemporaryDao;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.Collection;
import org.springframework.util.CollectionUtils;

public class ReltxSqlUtils {
    private static final int IN_MAXNUM = 500;

    public static TempTableCondition getConditionOfMulStr(Collection<String> ids, String fieldName) {
        return ReltxSqlUtils.getConditionOfMulStr(ids, fieldName, Relation.POSITIVE);
    }

    public static TempTableCondition getConditionOfMulStr(Collection<String> ids, String fieldName, Relation relation) {
        String condition = ReltxSqlUtils.preGetConditionOfIds(ids, fieldName, relation);
        if (!StringUtils.isEmpty((String)condition)) {
            return new TempTableCondition(condition, null);
        }
        String batchId = ((ReltxIdTemporaryDao)SpringBeanUtils.getBean(ReltxIdTemporaryDao.class)).saveAll(ids);
        String inFilter = Relation.POSITIVE == relation ? "in" : "not in";
        condition = fieldName + " " + inFilter + " (select idtemp.tbCode  id from " + "GC_RELTX_IDTEMPORARY" + " idtemp where idtemp.GROUP_ID = '" + batchId + "') \n";
        return new TempTableCondition(condition, batchId);
    }

    private static String preGetConditionOfIds(Collection<String> ids, String fieldName, Relation relation) {
        if (org.springframework.util.StringUtils.isEmpty(fieldName) || CollectionUtils.isEmpty(ids)) {
            return "1 = 2 \n";
        }
        if (ids.size() == 1) {
            return fieldName + (relation == Relation.NEGATIVE ? " <> '" : " = '") + ids.iterator().next() + "' \n";
        }
        if (ids.size() < 500) {
            StringBuilder sql = new StringBuilder();
            sql.append(fieldName).append(relation == Relation.NEGATIVE ? " not in (" : " in (");
            ids.forEach(id -> sql.append("'").append((String)id).append("',"));
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") \n");
            return sql.toString();
        }
        return "";
    }

    public static void deteteByGroupId(String groupId) {
        if (StringUtils.isEmpty((String)groupId)) {
            return;
        }
        ((ReltxIdTemporaryDao)SpringBeanUtils.getBean(ReltxIdTemporaryDao.class)).deleteByGroupId(groupId);
    }

    public static void deleteByGroupIds(Collection<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        ((ReltxIdTemporaryDao)SpringBeanUtils.getBean(ReltxIdTemporaryDao.class)).deleteByGroupIds(groupIds);
    }

    public static enum Relation {
        POSITIVE,
        NEGATIVE;

    }
}

