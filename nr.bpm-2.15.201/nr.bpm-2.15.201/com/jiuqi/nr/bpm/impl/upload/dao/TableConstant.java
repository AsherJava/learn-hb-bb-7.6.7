/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.impl.upload.dao;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.security.SecureRandom;
import org.springframework.util.Assert;

public class TableConstant {
    public static final String SPLIT_CHAR = ";";
    public static final String TABLE_NAMEPREFIX_STATE = "UP_ST_";
    public static final String SYS_TABLE_NAMEPREFIX_STATE = "SYS_UP_ST_";
    public static final String SYS_TABLE_NAMEPREFIX_FORM = "SYS_UP_FM_";
    public static final String STATE_FILED_PERIOD = "PERIOD";
    public static final String STATE_FILED_ENTITIES = "ENTITIES";
    public static final String STATE_FILED_FORMID = "FORMID";
    public static final String STATE_FILED_STATE = "STATE";
    public static final String STATE_FILED_PREVEVENT = "PREVEVENT";
    public static final String STATE_FILED_CURNODE = "CURNODE";
    public static final String STATE_FILED_FORCE = "FORCE_STATE";
    public static final String STATE_FILED_STARTTIME = "START_TIME";
    public static final String STATE_FILED_UPDATETIME = "UPDATE_TIME";
    public static final String STATE_FILED_SERIAL_NUMBER = "SERIAL_NUMBER";
    public static final String TABLE_NAMEPREFIX_ACTION = "UP_HI_";
    public static final String SYS_TABLE_NAMEPREFIX_ACTION = "SYS_UP_HI_";
    public static final String ACTION_FILED_PERIOD = "PERIOD";
    public static final String ACTION_FILED_ENTITIES = "ENTITIES";
    public static final String ACTION_FILED_FORMID = "FORMID";
    public static final String ACTION_FILED_RETURN_TYPE = "RETURN_TYPE";
    public static final String ACTION_FILED_ACTION = "ACTION";
    public static final String ACTION_FILED_COMMENT = "CMT";
    public static final String ACTION_FILED_TIME = "TIME_";
    public static final String ACTION_FILED_OPERATOR = "OPERATOR";
    public static final String ACTION_FILED_OPERATIONID = "OPERATIONID";
    public static final String ACTION_FILED_CUREVENT = "CUREVENT";
    public static final String ACTION_FILED_CURNODE = "CURNODE";
    public static final String ACTION_FILED_BIZKEY_ORDER = "BIZKEYORDER";
    public static final String ACTION_FILED_ORDER = "EXECUTE_ORDER";
    public static final String ACTION_FILED_ROLE_KEY = "ROLE_KEY";
    public static final String ACTION_RECORDKEY = "RECORDKEY";
    public static final String TABLE_VERSION_STATE = "1.0.0";
    public static final String TABLE_VERSION_ACTION = "1.0.0";
    public static final String ENTITYFILEDPREFIX = "entity_";
    private static final String NULL_UUID_STRING = "00000000-0000-0000-0000-000000000000";
    private static final String NULL_STRING = "_null_";
    private static final String EMPTY_STRING = "_empty_";
    public static final String TABLE_INDEX_PREFIX = "IDX";
    public static final String TABLE_INDEX_SEPARATOR = "_";

    public static String wrapperEmptyString(String str) {
        if (str == null) {
            return NULL_STRING;
        }
        if (str == "") {
            return EMPTY_STRING;
        }
        return str;
    }

    public static String parsingEmptyString(String str) {
        if (NULL_UUID_STRING.equals(str)) {
            return null;
        }
        if (EMPTY_STRING.equals(str)) {
            return "";
        }
        return str;
    }

    public static String getUploadStateTableName(String formSchemeKey, NrParameterUtils nrParameterUtils) {
        return TableConstant.getTableName(formSchemeKey, TABLE_NAMEPREFIX_STATE, nrParameterUtils);
    }

    public static String getUploadRecordTableName(String formSchemeKey, NrParameterUtils nrParameterUtils) {
        return TableConstant.getTableName(formSchemeKey, TABLE_NAMEPREFIX_ACTION, nrParameterUtils);
    }

    private static String getTableName(String formSchemeKey, String tableNamePrefix, NrParameterUtils nrParameterUtils) {
        return String.format("%s%s", tableNamePrefix, nrParameterUtils.getFormScheme(formSchemeKey).getFormSchemeCode());
    }

    public static String getUploadStateTableName(FormSchemeDefine formScheme) {
        return TableConstant.getTableName(formScheme, TABLE_NAMEPREFIX_STATE);
    }

    public static String getSysUploadStateTableName(FormSchemeDefine formScheme) {
        return TableConstant.getTableName(formScheme, SYS_TABLE_NAMEPREFIX_STATE);
    }

    public static String getSysUploadStateTableName(String formSchemeCode) {
        return TableConstant.getTableName(formSchemeCode, SYS_TABLE_NAMEPREFIX_STATE);
    }

    public static String getSysUploadFormTableName(FormSchemeDefine formScheme) {
        return TableConstant.getTableName(formScheme, SYS_TABLE_NAMEPREFIX_FORM);
    }

    public static String getUploadRecordTableName(FormSchemeDefine formScheme) {
        return TableConstant.getTableName(formScheme, TABLE_NAMEPREFIX_ACTION);
    }

    public static String getSysUploadRecordTableName(FormSchemeDefine formScheme) {
        return TableConstant.getTableName(formScheme, SYS_TABLE_NAMEPREFIX_ACTION);
    }

    public static String getSysUploadRecordTableName(String formSchemeKey) {
        return TableConstant.getTableName(formSchemeKey, SYS_TABLE_NAMEPREFIX_ACTION);
    }

    public static String getSysVersionTableName(FormSchemeDefine formScheme) {
        return TableConstant.getTableName(formScheme, "SYS_VER_REL_");
    }

    private static String getTableName(FormSchemeDefine formScheme, String tableNamePrefix) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", tableNamePrefix, formScheme.getFormSchemeCode());
    }

    private static String getTableName(String formSchemCode, String tableNamePrefix) {
        Assert.notNull((Object)formSchemCode, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", tableNamePrefix, formSchemCode);
    }

    public static String generateTempTableName() {
        SecureRandom rand = new SecureRandom();
        String tableName = OrderGenerator.newOrder();
        return String.format("up_bk_%s_%s", tableName, rand.nextInt(100000));
    }
}

