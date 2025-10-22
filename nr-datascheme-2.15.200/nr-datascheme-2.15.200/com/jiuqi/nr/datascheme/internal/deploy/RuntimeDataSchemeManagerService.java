/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RuntimeDataSchemeManagerService {
    private static final Class<?> dataSchemeClz = DataSchemeDO.class;
    private static final Class<?> dataDimClz = DataDimDO.class;
    private static final Class<?> dataGroupClz = DataGroupDO.class;
    private static final Class<?> dataTableClz = DataTableDO.class;
    private static final Class<?> dataFieldClz = DataFieldDO.class;
    private static final Class<?> i18nClz = DataSchemeI18nDO.class;
    private static final Class<?> dataTableRel = DataTableRelDO.class;
    private static final Class<?> deployInfo = DataFieldDeployInfoDO.class;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private static final String DB_TABLE_SUFFIX = "_DES";

    public void deleteRuntimeDataScheme(String dataSchemeKey) {
        this.deleteRuntimeObjects(dataSchemeClz, "DS_KEY", Collections.singleton(dataSchemeKey));
    }

    public void updateRuntimeDataScheme(String dataSchemeKey) {
        this.deleteRuntimeDataScheme(dataSchemeKey);
        this.insertObjects(dataSchemeClz, "DS_KEY", Collections.singleton(dataSchemeKey), true);
    }

    public void updateAllRuntimeDataScheme(String dataSchemeKey) {
        this.deleteRuntimeObjects(dataSchemeClz, "DS_KEY", Collections.singleton(dataSchemeKey));
        this.deleteRuntimeObjects(dataDimClz, "DD_DS_KEY", Collections.singleton(dataSchemeKey));
        this.deleteRuntimeObjects(dataGroupClz, "DG_DS_KEY", Collections.singleton(dataSchemeKey));
        this.deleteRuntimeObjects(dataTableClz, "DT_DS_KEY", Collections.singleton(dataSchemeKey));
        this.deleteRuntimeObjects(dataFieldClz, "DF_DS_KEY", Collections.singleton(dataSchemeKey));
        this.deleteRuntimeObjects(i18nClz, "DI_DS_KEY", Collections.singleton(dataSchemeKey));
        this.deleteRuntimeObjects(dataTableRel, "DTR_DS_KEY", Collections.singleton(dataSchemeKey));
        this.insertObjects(dataSchemeClz, "DS_KEY", Collections.singleton(dataSchemeKey), true);
        this.insertObjects(dataDimClz, "DD_DS_KEY", Collections.singleton(dataSchemeKey), true);
        this.insertObjects(dataGroupClz, "DG_DS_KEY", Collections.singleton(dataSchemeKey), true);
        this.insertObjects(dataTableClz, "DT_DS_KEY", Collections.singleton(dataSchemeKey), true);
        this.insertObjects(dataFieldClz, "DF_DS_KEY", Collections.singleton(dataSchemeKey), true);
        this.insertObjects(i18nClz, "DI_DS_KEY", Collections.singleton(dataSchemeKey), true);
        this.insertObjects(dataTableRel, "DTR_DS_KEY", Collections.singleton(dataSchemeKey), true);
    }

    public void updateAllRuntimeDataTable(String dataTableKey) {
        this.deleteRuntimeObjects(dataTableClz, "DT_KEY", Collections.singleton(dataTableKey));
        this.deleteRuntimeObjects(dataFieldClz, "DF_DT_KEY", Collections.singleton(dataTableKey));
        this.deleteRuntimeObjects(dataTableRel, "DTR_DESTTABLE_KEY", Collections.singleton(dataTableKey));
        this.insertObjects(dataTableClz, "DT_DS_KEY", Collections.singleton(dataTableKey), true);
        this.insertObjects(dataFieldClz, "DF_DS_KEY", Collections.singleton(dataTableKey), true);
        this.insertObjects(dataTableRel, "DTR_DESTTABLE_KEY", Collections.singleton(dataTableKey), true);
        this.updateRuntimeI18nByTable(dataTableKey);
    }

    public void deleteRuntimeDataSchemeDim(String dataSchemeKey) {
        this.deleteRuntimeObjects(dataDimClz, "DD_DS_KEY", Collections.singleton(dataSchemeKey));
    }

    public void updateRuntimeDataSchemeDim(String dataSchemeKey) {
        this.deleteRuntimeDataSchemeDim(dataSchemeKey);
        this.insertObjects(dataDimClz, "DD_DS_KEY", Collections.singleton(dataSchemeKey), true);
    }

    public void deleteRuntimeDataGroup(String dataSchemeKey) {
        this.deleteRuntimeObjects(dataGroupClz, "DG_DS_KEY", Collections.singleton(dataSchemeKey));
    }

    public void updateRuntimeDataGroup(String dataSchemeKey) {
        this.deleteRuntimeDataGroup(dataSchemeKey);
        this.insertObjects(dataGroupClz, "DG_DS_KEY", Collections.singleton(dataSchemeKey), true);
    }

    public void deleteRuntimeDataGroup(Set<String> dataGroupKeys) {
        this.deleteRuntimeObjects(dataGroupClz, "DG_KEY", dataGroupKeys);
    }

    public void updateRuntimeDataGroup(Set<String> dataGroupKeys) {
        this.deleteRuntimeDataGroup(dataGroupKeys);
        this.insertObjects(dataGroupClz, "DG_KEY", dataGroupKeys, true);
    }

    public void deleteRuntimeDataTable(String dataTableKey) {
        this.deleteRuntimeDataTables(Collections.singleton(dataTableKey));
    }

    public void updateRuntimeDataTable(String dataTableKey) {
        this.updateRuntimeDataTables(Collections.singleton(dataTableKey));
    }

    public void deleteRuntimeDataTables(Set<String> dataTableKeys) {
        this.deleteRuntimeObjects(dataTableClz, "DT_KEY", dataTableKeys);
    }

    public void updateRuntimeDataTables(Set<String> dataTableKeys) {
        this.deleteRuntimeDataTables(dataTableKeys);
        this.insertObjects(dataTableClz, "DT_KEY", dataTableKeys, true);
    }

    public void deleteRuntimeDataFieldByTable(String dataTableKey) {
        this.deleteRuntimeObjects(dataFieldClz, "DF_DT_KEY", Collections.singleton(dataTableKey));
    }

    public void updateRuntimeDataFieldByTable(String dataTableKey) {
        this.deleteRuntimeDataFieldByTable(dataTableKey);
        this.insertObjects(dataFieldClz, "DF_DT_KEY", Collections.singleton(dataTableKey), true);
    }

    public void deleteRuntimeDataField(String dataFieldKey) {
        this.deleteRuntimeDataFields(Collections.singleton(dataFieldKey));
    }

    public void updateRuntimeDataField(String dataFieldKey) {
        this.updateRuntimeDataFields(Collections.singleton(dataFieldKey));
    }

    public void deleteRuntimeDataFields(Set<String> dataFieldKeys) {
        this.deleteRuntimeObjects(dataFieldClz, "DF_KEY", dataFieldKeys);
    }

    public void updateRuntimeDataFields(Set<String> dataFieldKeys) {
        this.deleteRuntimeDataFields(dataFieldKeys);
        this.insertObjects(dataFieldClz, "DF_KEY", dataFieldKeys, true);
    }

    public void deleteRuntimeI18n(String dataSchemeKey) {
        this.deleteRuntimeObjects(i18nClz, "DI_DS_KEY", Collections.singleton(dataSchemeKey));
    }

    public void updateRuntimeI18n(String dataSchemeKey) {
        this.deleteRuntimeI18n(dataSchemeKey);
        this.insertObjects(i18nClz, "DI_DS_KEY", Collections.singleton(dataSchemeKey), true);
    }

    public void deleteRuntimeI18nByTable(String dataTableKey) {
        String sql = String.format("DELETE FROM %s WHERE %s IN (SELECT %s FROM %s WHERE %s=?)", "NR_DATASCHEME_I18N", "DI_KEY", "DF_KEY", "NR_DATASCHEME_FIELD", "DF_DT_KEY");
        this.jdbcTemplate.update(sql, new Object[]{dataTableKey});
    }

    public void updateRuntimeI18nByTable(String dataTableKey) {
        this.deleteRuntimeI18nByTable(dataTableKey);
        String sql = String.format("INSERT INTO %s SELECT i.* FROM %s i LEFT JOIN %s f ON i.%s=f.%s WHERE f.%s=?", "NR_DATASCHEME_I18N", "NR_DATASCHEME_I18N_DES", "NR_DATASCHEME_FIELD_DES", "DI_KEY", "DF_KEY", "DF_DT_KEY");
        this.jdbcTemplate.update(sql, new Object[]{dataTableKey});
    }

    public void deleteRuntimeDataTableRel(String dataTableKey) {
        this.deleteRuntimeObjects(dataTableRel, "DTR_SRCTABLE_KEY", Collections.singleton(dataTableKey));
    }

    public void updateRuntimeDataTableRel(String dataTableKey) {
        this.deleteRuntimeDataTableRel(dataTableKey);
        this.insertObjects(dataTableRel, "DTR_SRCTABLE_KEY", Collections.singleton(dataTableKey), true);
    }

    public void deleteDeployInfo(String dataSchemeKey) {
        this.deleteRuntimeObjects(deployInfo, "DS_DS_KEY", Collections.singleton(dataSchemeKey));
    }

    public void deleteDeployInfoByTable(String dataTableKey) {
        this.deleteRuntimeObjects(deployInfo, "DS_SDT_KEY", Collections.singleton(dataTableKey));
    }

    private void deleteRuntimeObjects(Class<?> commonTable, String fieldName, Set<String> fieldValues) {
        String runtimeTableName = this.getRuntimeTableName(commonTable);
        this.deleteObjects(runtimeTableName, fieldName, fieldValues);
    }

    private String getRuntimeTableName(Class<?> commonTable) {
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable().toUpperCase();
        return tablename.replace(DB_TABLE_SUFFIX, "");
    }

    private void deleteObjects(String tableName, String fieldName, Set<String> fieldValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName.toUpperCase());
        deleteSql.append(" where ");
        deleteSql.append(fieldName.toUpperCase());
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

    private void insertObjects(Class<?> commonTable, String fieldName, Set<String> fieldValues, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        String runtimeTableName = this.getRuntimeTableName(commonTable);
        String destimeTableName = runtimeTableName + DB_TABLE_SUFFIX;
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
            insertSQL.append(fieldAnno.dbField().toUpperCase());
            selectSQL.append(fieldAnno.dbField().toUpperCase());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(fieldName.toUpperCase());
        insertSQL.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs);
    }

    public void updateRuntimeAdjust(String dataSchemeKey) {
        this.deleteRuntimeObjects(AdjustPeriodDO.class, "AP_DS_KEY", Collections.singleton(dataSchemeKey));
        this.insertObjects(AdjustPeriodDO.class, "AP_DS_KEY", Collections.singleton(dataSchemeKey), true);
    }
}

