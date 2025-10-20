/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormFoldingDao
extends BaseDao {
    private static String ATTR_FORM_KEY = "formKey";
    private static final String NEW_DESTIME_AFTER = "_DES";
    private Class<DesignFormFoldingDefineImpl> implClass = DesignFormFoldingDefineImpl.class;

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public Class<?> getClz() {
        return this.implClass;
    }

    public void insertFormFoldingDefine(DesignFormFoldingDefine[] defines) throws DBParaException {
        this.insert(defines);
    }

    public void deleteFormFoldingDefine(String[] keys) throws DBParaException {
        this.delete(keys);
    }

    public void updateFormFoldingDefine(DesignFormFoldingDefine[] defines) throws DBParaException {
        this.update(defines);
    }

    public DesignFormFoldingDefine getFormFoldingDefineByKey(String key) {
        return (DesignFormFoldingDefine)this.getByKey(key, this.implClass);
    }

    public List<DesignFormFoldingDefine> getFormFoldingDefineByFormKey(String formKey) {
        return this.list(new String[]{ATTR_FORM_KEY}, new String[]{formKey}, this.implClass);
    }

    public void deleteFormFoldingByForm(String formKey) throws DBParaException {
        this.deleteBy(new String[]{ATTR_FORM_KEY}, new String[]{formKey});
    }

    public void insertObjectsFromDesignTime(Class<?> commonTable, String fieldName, Set<String> fieldValues, boolean isFromDesToSys) {
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

    private List<Object[]> getBatchArgs(Set<String> formKeys) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey};
            batchArgs.add(params);
        }
        return batchArgs;
    }
}

