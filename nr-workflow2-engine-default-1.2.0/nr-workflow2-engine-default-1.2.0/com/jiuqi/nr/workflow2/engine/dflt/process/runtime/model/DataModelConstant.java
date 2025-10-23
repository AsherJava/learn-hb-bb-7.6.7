/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import org.springframework.util.Assert;

public final class DataModelConstant {
    public static final String SPLIT_CHAR = ";";
    public static final String FIELDNAME_MDCODE = "MDCODE";
    public static final ColumnModelTemplate FIELD_MDCODE = new ColumnModelTemplate("MDCODE", "\u4e3b\u7ef4\u5ea6\u4ee3\u7801", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_PERIOD = "DATATIME";
    public static final ColumnModelTemplate FIELD_PERIOD = new ColumnModelTemplate("DATATIME", "\u65f6\u671f", "", ColumnModelType.STRING, 9, false);
    public static final String TABLENAME_PRE_INSTANCE = "NR_WF_IST_";
    public static final String FIELDNAME_PRE_INSTANCE = "IST_";
    public static final String FIELDNAME_IST_ID = "IST_ID";
    public static final ColumnModelTemplate FIELD_IST_ID = new ColumnModelTemplate("IST_ID", "\u6d41\u7a0b\u5b9e\u4f8bID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_DEFINITIONID = "IST_DEFINITIONID";
    public static final ColumnModelTemplate FIELD_IST_DEFINITIONID = new ColumnModelTemplate("IST_DEFINITIONID", "\u6d41\u7a0b\u5b9a\u4e49ID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_STARTTIME = "IST_STARTTIME";
    public static final ColumnModelTemplate FIELD_IST_STARTTIME = new ColumnModelTemplate("IST_STARTTIME", "\u6d41\u7a0b\u5b9e\u4f8b\u542f\u52a8\u65f6\u95f4", "", ColumnModelType.DATETIME, 0, true);
    public static final String FIELDNAME_IST_STARTUSER = "IST_STARTUSER";
    public static final ColumnModelTemplate FIELD_IST_STARTUSER = new ColumnModelTemplate("IST_STARTUSER", "\u6d41\u7a0b\u5b9e\u4f8b\u542f\u52a8\u7528\u6237ID", "", ColumnModelType.STRING, 50, true);
    public static final String FIELDNAME_IST_UPDATETIME = "IST_UPDATETIME";
    public static final ColumnModelTemplate FIELD_IST_UPDATETIME = new ColumnModelTemplate("IST_UPDATETIME", "\u6d41\u7a0b\u5b9e\u4f8b\u6700\u540e\u66f4\u65b0\u65f6\u95f4", "", ColumnModelType.DATETIME, 0, true);
    public static final String FIELDNAME_IST_TASK = "IST_TASKID";
    public static final ColumnModelTemplate FIELD_IST_TASK = new ColumnModelTemplate("IST_TASKID", "\u5f53\u524d\u6d41\u7a0b\u4efb\u52a1ID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_NODE = "IST_NODE";
    public static final ColumnModelTemplate FIELD_IST_NODE = new ColumnModelTemplate("IST_NODE", "\u5f53\u524d\u8282\u70b9\u6807\u8bc6", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_STATUS = "IST_STATUS";
    public static final ColumnModelTemplate FIELD_IST_STATUS = new ColumnModelTemplate("IST_STATUS", "\u5f53\u524d\u72b6\u6001\u6807\u8bc6", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_FORMKEY = "IST_FORMKEY";
    public static final ColumnModelTemplate FIELD_IST_FORMKEY = new ColumnModelTemplate("IST_FORMKEY", "\u8868\u5355ID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_FORMGROUPKEY = "IST_FORMGROUPKEY";
    public static final ColumnModelTemplate FIELD_IST_FORMGROUPKEY = new ColumnModelTemplate("IST_FORMGROUPKEY", "\u8868\u5355\u5206\u7ec4ID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_IST_LOCK = "IST_LOCK";
    public static final ColumnModelTemplate FIELD_IST_LOCK = new ColumnModelTemplate("IST_LOCK", "\u6d41\u7a0b\u5b9e\u4f8b\u540c\u6b65\u9501", "", ColumnModelType.STRING, 50, true);
    public static final String FIELDNAME_IST_LASTOPTIONID = "IST_LASTOPTIONID";
    public static final ColumnModelTemplate FIELD_IST_LASTOPTIONID = new ColumnModelTemplate("IST_LASTOPTIONID", "\u6d41\u7a0b\u5b9e\u4f8b\u6700\u540e\u64cd\u4f5c\u8bb0\u5f55ID", "", ColumnModelType.STRING, 50, true);
    public static final String TABLENAME_PRE_OPERATION = "NR_WF_OPT_";
    public static final String FIELDNAME_PRE_OPERATION = "OPT_";
    public static final String FIELDNAME_OPT_ID = "OPT_ID";
    public static final ColumnModelTemplate FIELD_OPT_ID = new ColumnModelTemplate("OPT_ID", "\u64cd\u4f5cID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_OPT_ISTID = "IST_ID";
    public static final ColumnModelTemplate FIELD_OPT_ISTID = new ColumnModelTemplate("IST_ID", "\u6d41\u7a0b\u5b9e\u4f8bID", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_OPT_FROMNODE = "OPT_FROMNODE";
    public static final ColumnModelTemplate FIELD_OPT_FROMNODE = new ColumnModelTemplate("OPT_FROMNODE", "\u64cd\u4f5c\u524d\u6d41\u7a0b\u6240\u5728\u8282\u70b9\u6807\u8bc6", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_OPT_ACTION = "OPT_ACTION";
    public static final ColumnModelTemplate FIELD_OPT_ACTION = new ColumnModelTemplate("OPT_ACTION", "\u64cd\u4f5c\u52a8\u4f5c\u6807\u8bc6", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_OPT_TONODE = "OPT_TONODE";
    public static final ColumnModelTemplate FIELD_OPT_TONODE = new ColumnModelTemplate("OPT_TONODE", "\u64cd\u4f5c\u540e\u6d41\u7a0b\u6240\u5728\u8282\u70b9\u6807\u8bc6", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_OPT_NEWSTATUS = "OPT_NEWSTATUS";
    public static final ColumnModelTemplate FIELD_OPT_NEWSTATUS = new ColumnModelTemplate("OPT_NEWSTATUS", "\u64cd\u4f5c\u540e\u6d41\u7a0b\u7684\u72b6\u6001", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_OPT_OPERATETIME = "OPT_TIME";
    public static final ColumnModelTemplate FIELD_OPT_OPERATETIME = new ColumnModelTemplate("OPT_TIME", "\u64cd\u4f5c\u65f6\u95f4", "", ColumnModelType.DATETIME, 0, true);
    public static final String FIELDNAME_OPT_OPERATEUSER = "OPT_USER";
    public static final ColumnModelTemplate FIELD_OPT_OPERATEUSER = new ColumnModelTemplate("OPT_USER", "\u64cd\u4f5c\u7528\u6237id", "", ColumnModelType.STRING, 50, true);
    public static final String FIELDNAME_OPT_OPERATEIDENTITY = "OPT_IDENTITY";
    public static final ColumnModelTemplate FIELD_OPT_OPERATEIDENTITY = new ColumnModelTemplate("OPT_IDENTITY", "\u64cd\u4f5c\u7528\u6237\u8eab\u4efdid", "", ColumnModelType.STRING, 50, true);
    public static final String FIELDNAME_OPT_COMMENT = "OPT_COMMENT";
    public static final ColumnModelTemplate FIELD_OPT_COMMENT = new ColumnModelTemplate("OPT_COMMENT", "\u64cd\u4f5c\u8bf4\u660e", "", ColumnModelType.STRING, 2000, true);
    public static final String FIELDNAME_OPT_OPERATETYPE = "OPT_TYPE";
    public static final ColumnModelTemplate FIELD_OPT_OPERATETYPE = new ColumnModelTemplate("OPT_TYPE", "\u64cd\u4f5c\u7c7b\u578b", "\u5982\u9000\u56de\u7c7b\u578b\uff0c\u5b58\u50a8\u7684\u662f\u64cd\u4f5c\u7c7b\u578b\u57fa\u7840\u6570\u636e\u9879\u7684Code", ColumnModelType.STRING, 60, true);
    public static final String FIELDNAME_OPT_FORCEREPORT = "OPT_FORCEREPORT";
    public static final ColumnModelTemplate FIELD_OPT_FORCEREPORT = new ColumnModelTemplate("OPT_FORCEREPORT", "\u662f\u5426\u5f3a\u5236\u4e0a\u62a5", "\u5f3a\u5236\u4e0a\u62a5\u6307\u4e0d\u7ecf\u8fc7\u5ba1\u6838\u76f4\u63a5\u4e0a\u62a5", ColumnModelType.INTEGER, 1, true);
    public static final String TABLENAME_PRE_STATUS = "NR_WF_ST_";
    public static final String FIELDNAME_ST_STATUS = "STATUS";
    public static final ColumnModelTemplate FIELD_ST_STATUS = new ColumnModelTemplate("STATUS", "\u6d41\u7a0b\u72b6\u6001", "", ColumnModelType.STRING, 50, false);
    public static final String FIELDNAME_ST_TIMESTAMP = "TIMESTAMP";
    public static final ColumnModelTemplate FIELD_ST_TIMESTAMP = new ColumnModelTemplate("TIMESTAMP", "\u65f6\u95f4\u6233", "", ColumnModelType.DATETIME, 0, true);
    public static final String NOEXISTS_FORM_OR_GROUP_KEY = "_NULL_";

    public static final String getInstanceTableName(FormSchemeDefine formScheme) {
        return DataModelConstant.getTableName(formScheme, TABLENAME_PRE_INSTANCE);
    }

    public static final String getHistoryTableName(FormSchemeDefine formScheme) {
        return DataModelConstant.getTableName(formScheme, TABLENAME_PRE_OPERATION);
    }

    public static final String getStatusTableName(FormSchemeDefine formScheme) {
        return DataModelConstant.getTableName(formScheme, TABLENAME_PRE_STATUS);
    }

    public static final String getInstanceTableName(String formSchemeCode) {
        return DataModelConstant.getTableName(formSchemeCode, TABLENAME_PRE_INSTANCE);
    }

    public static final String getHistoryTableName(String formSchemeCode) {
        return DataModelConstant.getTableName(formSchemeCode, TABLENAME_PRE_OPERATION);
    }

    public static final String getStatusTableName(String formSchemeCode) {
        return DataModelConstant.getTableName(formSchemeCode, TABLENAME_PRE_STATUS);
    }

    private static String getTableName(FormSchemeDefine formScheme, String tableNamePrefix) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", tableNamePrefix, formScheme.getFormSchemeCode()).toUpperCase();
    }

    private static String getTableName(String formSchemCode, String tableNamePrefix) {
        Assert.notNull((Object)formSchemCode, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", tableNamePrefix, formSchemCode).toUpperCase();
    }

    public static String getInstanceBIzKeyIndexName(String tableName) {
        return String.format("%s_IDX_BIZKEY", tableName);
    }

    public static String getInstanceTaskIndexName(String tableName) {
        return String.format("%s_IDX_TSK", tableName);
    }

    public static String getInstanceLockIndexName(String tableName) {
        return String.format("%s_IDX_LCK", tableName);
    }

    public static String getInstanceLastOperationIdIndexName(String tableName) {
        return String.format("%s_IDX_OPT", tableName);
    }

    public static String getOperationInstanceIdIndexName(String tableName) {
        return String.format("%s_IDX_IST", tableName);
    }

    public static String getOperationOperateTimeIndexName(String tableName) {
        return String.format("%s_IDX_TIME", tableName);
    }

    public static class ColumnModelTemplate {
        final String name;
        final String title;
        final String desciption;
        final ColumnModelType datatype;
        final int precision;
        final boolean nullable;

        private ColumnModelTemplate(String name, String title, String desciption, ColumnModelType datatype, int precision, boolean nullable) {
            this.name = name;
            this.title = title;
            this.desciption = desciption;
            this.datatype = datatype;
            this.precision = precision;
            this.nullable = nullable;
        }

        public void applyto(DesignColumnModelDefine columnModel) {
            columnModel.setCode(this.name);
            columnModel.setName(this.name);
            columnModel.setTitle(this.title);
            columnModel.setDesc(this.desciption);
            columnModel.setColumnType(this.datatype);
            columnModel.setPrecision(this.precision);
            columnModel.setNullAble(this.nullable);
        }
    }
}

