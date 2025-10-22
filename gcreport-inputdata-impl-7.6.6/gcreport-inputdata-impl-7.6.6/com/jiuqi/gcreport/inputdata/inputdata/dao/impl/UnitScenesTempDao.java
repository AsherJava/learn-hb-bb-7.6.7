/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitScenesTempDao {
    private static EntNativeSqlTemplate template;
    private static final String INSERTSQL = "insert into %s (batchId,mdCode,oppUnitCode,gcBusinessType,id) values (?,?,?,?,?)";
    private static final String HANASQL_NAME = "HANA";
    private static final String ORACLESQL_NAME = "ORACLE";
    private static final String DM_NAME = "DM DBMS";
    private boolean isTempTable = false;
    private String tableName;

    public static UnitScenesTempDao newInstance() {
        return new UnitScenesTempDao();
    }

    public String insert(List<ArrayKey> unitScenesTempList) {
        String batchId = UUIDUtils.newUUIDStr();
        String insertSql = String.format(INSERTSQL, this.tableName);
        ArrayList paramValues = new ArrayList();
        unitScenesTempList.forEach(oneRow -> paramValues.add(Arrays.asList(batchId, oneRow.get(0), oneRow.get(1), oneRow.get(2), UUIDOrderUtils.newUUIDStr())));
        EntDmlBatchSql entDmlBatchSql = EntSqlTool.newDmlBatchInstance((String)insertSql, paramValues);
        template.executeBatch(entDmlBatchSql);
        return batchId;
    }

    public void deleteIdRealTempByBatchId(String batchId) {
        if (this.isTempTable) {
            String deleteTableSql = this.buildDeleteTableSql();
            EntDmlSql entDmlSql = EntSqlTool.newDmlInstance((String)deleteTableSql, Arrays.asList(batchId));
            template.execute(entDmlSql);
        }
    }

    public String getTableName() {
        return this.tableName;
    }

    private UnitScenesTempDao() {
        String dataBaseName;
        if (null == template) {
            template = (EntNativeSqlTemplate)SpringContextUtils.getBean(EntNativeSqlTemplate.class);
        }
        try {
            dataBaseName = template.getDatabase().getName();
        }
        catch (SQLException e) {
            throw new BusinessRuntimeException("\u4e34\u65f6\u8868\u83b7\u53d6\u6570\u636e\u5e93NAME\u5f02\u5e38\u3002", (Throwable)e);
        }
        if (HANASQL_NAME.equalsIgnoreCase(dataBaseName) || ORACLESQL_NAME.equalsIgnoreCase(dataBaseName) || DM_NAME.equalsIgnoreCase(dataBaseName)) {
            this.tableName = "GC_UNITSCENESTEMP";
        } else {
            this.isTempTable = true;
            this.tableName = "GC_UNITSCENESNOTEMP";
        }
    }

    private String buildDeleteTableSql() {
        String sql = "DELETE FROM %s where batchId=?";
        return String.format("DELETE FROM %s where batchId=?", this.tableName);
    }
}

