/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.nr.bpm.setting.utils;

import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import java.util.List;
import java.util.Map;

public class SettingDeployUtil {
    public static String initField_DATE(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelCreateUtil nvwaDataModelCreateUtil, Map<String, DesignColumnModelDefine> fieldMap) throws Exception {
        return SettingDeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 0, nvwaDataModelCreateUtil, fieldMap);
    }

    public static String initField_String(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size, NvwaDataModelCreateUtil nvwaDataModelCreateUtil, Map<String, DesignColumnModelDefine> fieldMap) throws Exception {
        return SettingDeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, size, nvwaDataModelCreateUtil, fieldMap);
    }

    public static String initField_Clob(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size, NvwaDataModelCreateUtil nvwaDataModelCreateUtil, Map<String, DesignColumnModelDefine> fieldMap) throws Exception {
        return SettingDeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.BLOB, size, nvwaDataModelCreateUtil, fieldMap);
    }

    public static String initField_Boolean(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelCreateUtil nvwaDataModelCreateUtil, Map<String, DesignColumnModelDefine> fieldMap) throws Exception {
        return SettingDeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.BOOLEAN, 0, nvwaDataModelCreateUtil, fieldMap);
    }

    public static String initField_Time_Stamp(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, NvwaDataModelCreateUtil nvwaDataModelCreateUtil, Map<String, DesignColumnModelDefine> fieldMap) throws Exception {
        return SettingDeployUtil.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 6, nvwaDataModelCreateUtil, fieldMap);
    }

    private static String initField(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, ColumnModelType fieldType, int size, NvwaDataModelCreateUtil nvwaDataModelCreateUtil, Map<String, DesignColumnModelDefine> fieldMap) throws Exception {
        DesignColumnModelDefine fieldDefine = fieldMap.get(fieldCode);
        if (fieldDefine == null) {
            fieldDefine = nvwaDataModelCreateUtil.createField();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
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

