/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InvestBillTool {
    public static List<DefaultTableEntity> listByUnitCode(String tableName, Collection<String> unitCodes, Integer year) {
        String sql = "select %1$s  from " + tableName + " t  where %2$s";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String whereSql = SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"UNITCODE");
        if (null != year) {
            whereSql = whereSql + " and acctYear=" + year;
        }
        String formatSQL = String.format(sql, columns, whereSql);
        return InvestBillTool.queryBySql(formatSQL, new Object[0]);
    }

    public static List<DefaultTableEntity> listItemByMasterId(Collection<String> masteIds, String tableName) {
        String sql = "select %1$s  from " + tableName + " t  where %2$s";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String whereSql = SqlUtils.getConditionOfIdsUseOr(masteIds, (String)"masterId");
        String formatSQL = String.format(sql, columns, whereSql);
        return InvestBillTool.queryBySql(formatSQL, new Object[0]);
    }

    public static int deleteItemByUnitCode(String masterTableName, String itemTableName, Collection<String> unitCodes, Integer year) {
        String sql = "delete from " + itemTableName + "   where masterId in (select id from " + masterTableName + " where %1$s )";
        String whereSql = SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"UNITCODE");
        if (null != year) {
            whereSql = whereSql + " and acctYear=" + year;
        }
        String formatSQL = String.format(sql, whereSql);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL);
    }

    public static int deleteMasterByUnitCode(String masterTableName, Collection<String> unitCodes, Integer year) {
        String sql = "delete from " + masterTableName + " where %1$s ";
        String whereSql = SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"UNITCODE");
        if (null != year) {
            whereSql = whereSql + " and acctYear=" + year;
        }
        String formatSQL = String.format(sql, whereSql);
        System.out.println(formatSQL);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL);
    }

    public static List<DefaultTableEntity> queryBySql(String sql, Object ... params) {
        ArrayList<DefaultTableEntity> result = new ArrayList<DefaultTableEntity>();
        List fields = EntNativeSqlDefaultDao.getInstance().selectMap(sql, params);
        for (Map field : fields) {
            DefaultTableEntity entity = new DefaultTableEntity();
            entity.setId(String.valueOf(field.get("ID")));
            entity.resetFields(field);
            result.add(entity);
        }
        return result;
    }

    public static Date getDateValue(DefaultTableEntity item, String field) {
        Object value = item.getFieldValue(field);
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date)value;
        }
        return null;
    }

    public static int getIntValue(DefaultTableEntity item, String field) {
        Object value = item.getFieldValue(field);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        return ConverterUtils.getAsInteger((Object)value);
    }
}

