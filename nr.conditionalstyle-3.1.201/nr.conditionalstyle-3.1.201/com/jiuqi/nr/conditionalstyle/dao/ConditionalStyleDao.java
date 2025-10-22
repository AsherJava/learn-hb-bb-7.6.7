/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.conditionalstyle.dao;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.impl.ConditionalStyleImpl;
import com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionalStyleDao
extends BaseDao {
    private Class<ConditionalStyleImpl> implClz = ConditionalStyleImpl.class;
    private static final String NEW_DESTIME_AFTER = "_DES";

    public Class<?> getClz() {
        return this.implClz;
    }

    public List<ConditionalStyle> getCSByPos(String formKey, int x, int y) {
        return super.list("CS_FORM_KEY = ? AND CS_POS_X = ? AND CS_POS_Y = ?", new Object[]{formKey, x, y}, this.implClz);
    }

    public List<ConditionalStyle> getCSByForm(String formKey) {
        return super.list("CS_FORM_KEY = ?", new Object[]{formKey}, this.implClz);
    }

    public void deleteCS(List<ConditionalStyle> param) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("NR_PARAM_CONDITIONAL_STYLE").append(" WHERE CS_KEY = ?");
        String s = sql.toString();
        for (ConditionalStyle cs : param) {
            this.jdbcTemplate.update(s, new Object[]{cs.getKey()});
        }
    }

    public void deleteConditionalStyles(Set<String> ids, boolean isDes) {
        if (isDes) {
            this.deleteObjects("NR_PARAM_CONDITIONAL_STYLE", "CS_KEY", ids);
        } else {
            this.deleteObjects("NR_PARAM_CONDITIONAL_STYLE_DES", "CS_KEY", ids);
        }
    }

    public void insertConditionalStyles(Set<String> ids, boolean isDes) {
        this.insertObjectsFromDesignTime(DesignConditionalStyleImpl.class, "CS_KEY", ids, isDes);
    }

    private void deleteObjects(String tableName, String fieldName, Set<String> fieldValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(fieldName);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private List<Object[]> getBatchArgs(Set<String> formKeys) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey};
            batchArgs.add(params);
        }
        return batchArgs;
    }

    private void insertObjectsFromDesignTime(Class<?> commonTable, String fieldName, Set<String> fieldValues, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable();
        String runtimeTableName = tablename = tablename.substring(0, tablename.indexOf(NEW_DESTIME_AFTER));
        String destimeTableName = tablename + NEW_DESTIME_AFTER;
        if (isFromDesToSys) {
            srcTable = destimeTableName;
            tarTable = runtimeTableName;
        } else {
            srcTable = runtimeTableName;
            tarTable = destimeTableName;
        }
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(tarTable).append(" (");
        Field[] fields = commonTable.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            insertSQL.append(fieldAnno.dbField());
            selectSQL.append(fieldAnno.dbField());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(fieldName);
        insertSQL.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs);
    }
}

