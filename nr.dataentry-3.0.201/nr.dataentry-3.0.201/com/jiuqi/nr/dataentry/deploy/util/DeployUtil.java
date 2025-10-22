/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.nr.dataentry.deploy.util;

import com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import java.util.List;

public class DeployUtil {
    public static String initField_DATE(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        return DeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 0, nvwaDataModelCreateUtil);
    }

    public static String initField_String(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        return DeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, size, nvwaDataModelCreateUtil);
    }

    public static String initField_Clob(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        return DeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.BLOB, size, nvwaDataModelCreateUtil);
    }

    public static String initField_Boolean(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        return DeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.BOOLEAN, 0, nvwaDataModelCreateUtil);
    }

    public static String initField_Time_Stamp(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        return DeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 6, nvwaDataModelCreateUtil);
    }

    public static String initField_Integer(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        return DeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.INTEGER, 2, nvwaDataModelCreateUtil);
    }

    private static String initField(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, ColumnModelType fieldType, int size, NvwaDataModelDeployUtil nvwaDataModelCreateUtil) throws Exception {
        DesignColumnModelDefine fieldDefine = nvwaDataModelCreateUtil.queryFieldDefinesByCode(fieldCode, tableKey);
        if (fieldDefine == null) {
            fieldDefine = nvwaDataModelCreateUtil.createField();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
        fieldDefine.setName(fieldCode);
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(fieldType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }
}

