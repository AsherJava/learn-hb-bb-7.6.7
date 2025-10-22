/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dao;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.entity.IdRealTempEO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IdRealTempDao {
    private EntNativeSqlTemplate template = (EntNativeSqlTemplate)SpringContextUtils.getBean(EntNativeSqlTemplate.class);
    private boolean isTempTable = false;
    private String tableName;
    private static final String HANASQL_NAME = "HANA";
    private static final String ORACLESQL_NAME = "ORACLE";
    private static final String DM_NAME = "DM DBMS";

    public static IdRealTempDao newInstance() {
        return new IdRealTempDao();
    }

    public void insert(List<IdRealTempEO> idTemps) {
        String sql = "insert into %s (batchId,orgCode,id) values (?,?,?)";
        String insertSql = String.format("insert into %s (batchId,orgCode,id) values (?,?,?)", this.tableName);
        ArrayList paramValues = new ArrayList();
        idTemps.forEach(idTemp -> paramValues.add(Arrays.asList(idTemp.getBatchId(), idTemp.getId(), UUIDUtils.newUUIDStr())));
        EntDmlBatchSql entDmlBatchSql = EntSqlTool.newDmlBatchInstance((String)insertSql, paramValues);
        this.template.executeBatch(entDmlBatchSql);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void deleteIdRealTempByBatchId(String batchId) {
        if (this.isTempTable) {
            String deleteTableSql = this.buildDeleteTableSql();
            EntDmlSql entDmlSql = EntSqlTool.newDmlInstance((String)deleteTableSql, Arrays.asList(batchId));
            this.template.execute(entDmlSql);
        }
    }

    private IdRealTempDao() {
        String dataBaseName = null;
        try {
            dataBaseName = this.template.getDatabase().getName();
        }
        catch (SQLException e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbtemptablemsg"), (Throwable)e);
        }
        if (HANASQL_NAME.equalsIgnoreCase(dataBaseName) || ORACLESQL_NAME.equalsIgnoreCase(dataBaseName) || DM_NAME.equalsIgnoreCase(dataBaseName)) {
            this.tableName = "GC_IDREALTEMP";
        } else {
            this.isTempTable = true;
            this.tableName = "GC_IDREALNOTEMP";
        }
    }

    private String buildDeleteTableSql() {
        String sql = "DELETE FROM %s where batchId=?";
        return String.format("DELETE FROM %s where batchId=?", this.tableName);
    }
}

