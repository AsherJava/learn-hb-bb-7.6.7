/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.org.api.util;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.Date;
import java.util.UUID;

public class GcOrgQueryUtils {
    public static String orgCodeSubquerySql(OrgTypeVO type, OrgVersionVO ver, boolean isNpsql, String ... codes) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.CODE FROM ").append(type.getTable());
        if (isNpsql) {
            sql.append(" AS ");
        }
        sql.append(" T WHERE ");
        return sql.toString();
    }

    public static String orgCodeJoinquerySql(OrgTypeVO type, OrgVersionVO ver, String codefield, boolean isNpsql) {
        StringBuffer sql = new StringBuffer();
        sql.append(" RIGHT JOIN  ").append(type.getTable());
        if (isNpsql) {
            sql.append(" AS ");
        }
        sql.append(" T ON ");
        sql.append(" T.CODE =  ").append(codefield);
        sql.append(" AND  T.VALIDTIME <=").append(DateUtils.format((Date)ver.getValidTime(), (String)"yyyy-MM-dd"));
        sql.append(" AND T.INVALIDTIME >=").append(DateUtils.format((Date)ver.getInvalidTime(), (String)"yyyy-MM-dd"));
        return sql.toString();
    }

    public static UUID toUUID(String str) {
        return UUIDUtils.fromString36((String)str);
    }

    public static String toString(UUID str) {
        return UUIDUtils.toString36((UUID)str);
    }
}

