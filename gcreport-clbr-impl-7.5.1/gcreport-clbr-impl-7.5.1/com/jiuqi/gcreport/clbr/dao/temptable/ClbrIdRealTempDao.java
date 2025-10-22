/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary
 */
package com.jiuqi.gcreport.clbr.dao.temptable;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClbrIdRealTempDao {
    private static final Logger log = LoggerFactory.getLogger(ClbrIdRealTempDao.class);
    private EntNativeSqlTemplate template = (EntNativeSqlTemplate)SpringContextUtils.getBean(EntNativeSqlTemplate.class);
    private boolean isTempTable = false;
    private String tableName;
    private static final String HANASQL_NAME = "HANA";
    private static final String ORACLESQL_NAME = "ORACLE";
    private static final String DM_NAME = "DM";

    public static ClbrIdRealTempDao newInstance() {
        return new ClbrIdRealTempDao();
    }

    public void insert(List<IdTemporary> idTemps) {
        String sql = "insert into %s (ID,GROUP_ID,TBID) values (?,?,?)";
        String insertSql = String.format("insert into %s (ID,GROUP_ID,TBID) values (?,?,?)", this.tableName);
        ArrayList paramValues = new ArrayList();
        idTemps.forEach(idTemp -> paramValues.add(Arrays.asList(UUIDUtils.newUUIDStr(), idTemp.getGroupId(), idTemp.getTbId())));
        EntDmlBatchSql entDmlBatchSql = EntSqlTool.newDmlBatchInstance((String)insertSql, paramValues);
        this.template.executeBatch(entDmlBatchSql);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void deleteIdRealTempByBatchId(String batchId) {
        if (this.isTempTable) {
            if (StringUtils.isEmpty((String)batchId)) {
                return;
            }
            log.info("\u6e05\u7a7a\u4e34\u65f6\u8868\uff1a{},\u6279\u6b21id:{}", (Object)this.tableName, (Object)batchId);
            String deleteTableSql = this.buildDeleteTableSql();
            EntDmlSql entDmlSql = EntSqlTool.newDmlInstance((String)deleteTableSql, Arrays.asList(batchId));
            this.template.execute(entDmlSql);
        }
    }

    private ClbrIdRealTempDao() {
        String dataBaseName = null;
        try {
            dataBaseName = this.template.getDatabase().getName();
        }
        catch (SQLException e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u6570\u636e\u5e93\u7c7b\u578b\u5931\u8d25", (Throwable)e);
        }
        if (HANASQL_NAME.equalsIgnoreCase(dataBaseName) || ORACLESQL_NAME.equalsIgnoreCase(dataBaseName) || DM_NAME.equalsIgnoreCase(dataBaseName)) {
            this.tableName = "GC_IDREALTEMPORARY";
        } else {
            this.isTempTable = true;
            this.tableName = "GC_IDTEMPORARY";
        }
    }

    private String buildDeleteTableSql() {
        String sql = "DELETE FROM %s where GROUP_ID=?";
        return String.format("DELETE FROM %s where GROUP_ID=?", this.tableName);
    }
}

