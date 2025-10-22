/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.common;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.DBTypeUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmptyZeroCheckHelper {
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    DBTypeUtil dbTypeUtil;
    @Resource
    private IDataDefinitionRuntimeController dataCtrl;
    @Autowired
    IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    DataQueryHelper dataQueryHelper;
    @Autowired
    EntityQueryHelper entityQueryHelper;

    public String getEmptyMySqlSql(String taskKey, String period, String tempTableName, DataTable tableDefine, String masterDimName) throws Exception {
        String tableName = this.entityQueryHelper.getTableNameByTableCode(tableDefine.getCode());
        boolean hasVersionKey = this.hasVersionKey(tableName);
        String libTableName = this.dataQueryHelper.getLibraryTableName(taskKey, tableName);
        StringBuffer querySql = new StringBuffer();
        querySql.append("select distinct(dw) from ").append(libTableName);
        querySql.append(" where ");
        querySql.append(String.format(" exists (select 1 from %s ass where ass.code = %s.dw) ", tempTableName, libTableName));
        querySql.append(String.format(" and %s.%s='%s' ", libTableName, "DATATIME", period));
        if (hasVersionKey) {
            querySql.append(" and ").append(String.format(" %s.%s='%s' ", libTableName, "VERSION", "0"));
        }
        return querySql.toString();
    }

    public String getEmptyOracleSql(String taskKey, String period, String tempTableName, DataTable tableDefine, String masterDimName) throws Exception {
        StringBuffer querySql = new StringBuffer();
        String tableName = this.entityQueryHelper.getTableNameByTableCode(tableDefine.getCode());
        boolean hasVersionKey = false;
        String libTableName = this.dataQueryHelper.getLibraryTableName(taskKey, tableName);
        String sqSql = "";
        sqSql = period.length() == 0 ? "1=1" : String.format(" %s.%s='%s' ", libTableName, "DATATIME", period);
        String versionSql = String.format(" %s.%s='%s' ", libTableName, "VERSION", "0");
        String dmKeySql = String.format(" %s.%s=%s.CODE ", libTableName, "MDCODE", tempTableName);
        querySql.append("select ").append(tempTableName).append(".CODE from ").append(tempTableName).append(" where not exists(select 1 from ").append(libTableName).append(" where ");
        querySql.append(dmKeySql).append(" and ");
        querySql.append(sqSql);
        if (hasVersionKey) {
            querySql.append(" and ").append(versionSql);
        }
        querySql.append(")");
        return querySql.toString();
    }

    public Boolean hasVersionKey(String tableName) throws Exception {
        DataTable tableDefine = this.runtimeDataSchemeService.getDataTableByCode(tableName);
        if (null == tableDefine || tableDefine.getBizKeys() == null || tableDefine.getBizKeys().length == 0) {
            return false;
        }
        DataField field = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(tableDefine.getKey(), "VERSION");
        if (field != null) {
            return true;
        }
        return false;
    }

    public String getEmptyZeroCheckSql(String taskKey, String period, String tempTableName, Map<String, List<DataField>> tableFields, String masterDimName) throws Exception {
        StringBuffer checkSql = new StringBuffer();
        if (tableFields.size() > 1) {
            checkSql.append("Select ass.FMID, MAX(ass.FLAG) AS FLAG FROM (");
        }
        for (Map.Entry<String, List<DataField>> entry : tableFields.entrySet()) {
            String mapKey = entry.getKey();
            List<DataField> tableFieldsItem = entry.getValue();
            this.buildTableSql(checkSql, taskKey, period, mapKey, tempTableName, tableFieldsItem, masterDimName);
            checkSql.append(" UNION ALL ");
        }
        this.RemoveLastString(checkSql, " UNION ALL ");
        if (tableFields.size() == 1) {
            return checkSql.toString();
        }
        checkSql.append(") ass GROUP BY ass.FMID");
        return checkSql.toString();
    }

    private void RemoveLastString(StringBuffer sb, String str) {
        sb.delete(sb.length() - str.length(), sb.length());
    }

    private void buildTableSql(StringBuffer checkSql, String taskKey, String period, String tableName, String tempTableName, List<DataField> fields, String masterDimName) throws Exception {
        boolean hasVersionKey = false;
        String versionSql = String.format(" %s='%s' ", "VERSION", "0");
        String libTableName = this.dataQueryHelper.getLibraryTableName(taskKey, tableName);
        StringBuffer sqSql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        if (period.length() == 0) {
            sqSql.append("1=1");
        } else {
            sqSql.append("DATATIME").append("='").append(period).append("'");
        }
        checkSql.append("Select ").append("MDCODE").append(" AS FMID, ");
        checkSql.append("sign(");
        checkSql.append(" case ");
        for (DataField field : fields) {
            this.buildFieldSql(checkSql, field);
            checkSql.append(" ");
            whereSql.append(this.entityQueryHelper.getFieldNameByFieldKey(field.getKey()).toUpperCase()).append(" is not null OR ");
        }
        checkSql.append(" else  0  end ");
        checkSql.append(") AS FLAG ");
        checkSql.append(" from ").append(libTableName).append(" where ");
        checkSql.append(" exists (select 1 from ").append(tempTableName).append(" where ").append(tempTableName).append(".code=").append(libTableName).append(".").append("MDCODE").append(") ");
        if (sqSql.length() > 0) {
            checkSql.append(" And ").append(sqSql);
        }
        if (hasVersionKey) {
            checkSql.append(" And ").append(versionSql);
        }
        if (whereSql.length() > 3) {
            whereSql.delete(whereSql.length() - 3, whereSql.length());
            checkSql.append(" And (").append(whereSql.toString()).append(")");
        }
        checkSql.append(" GROUP BY ").append("MDCODE");
    }

    private void buildFieldSql(StringBuffer checkSql, DataField DataField2) {
        String fullFieldName = this.entityQueryHelper.getFieldNameByFieldKey(DataField2.getKey()).toUpperCase();
        String fullFieldCode = DataField2.getCode().toUpperCase();
        if (DataField2.getDataFieldType() == DataFieldType.BIGDECIMAL || DataField2.getDataFieldType() == DataFieldType.INTEGER) {
            this.buildNumFieldSql(checkSql, fullFieldName, fullFieldCode);
        } else {
            this.buildNNumFieldSql(checkSql, fullFieldName, fullFieldCode);
        }
    }

    private void buildNumFieldSql(StringBuffer checkSql, String fieldCode, String asName) {
        String nulStr = this.getNullFunction(this.dbTypeUtil.getDbType());
        checkSql.append(" when  sum(abs(").append(nulStr).append("(").append(fieldCode).append(",0))) > 0 then sum(abs(").append(nulStr).append("(").append(fieldCode).append(",0))) ");
    }

    private void buildNNumFieldSql(StringBuffer checkSql, String fieldCode, String asName) {
        String nulStr = this.getNullFunction(this.dbTypeUtil.getDbType());
        checkSql.append(" when sum(").append(nulStr).append("(length(").append(fieldCode).append("),0)) > 0 then  sum(").append(nulStr).append("(length(").append(fieldCode).append("),0)) ");
    }

    private String getNullFunction(DBTypeUtil.DbType dbType) {
        switch (dbType) {
            case MYSQL: {
                return "IFNULL";
            }
            case DERBY: {
                return "NULLIF";
            }
            case POSTGRESQL: 
            case KINGBASE: 
            case OSCAR: {
                return "COALESCE";
            }
        }
        return "nvl";
    }
}

