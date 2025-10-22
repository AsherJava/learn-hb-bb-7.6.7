/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import java.util.HashMap;
import java.util.Map;

public class EntityCheckUpConst {
    public static final String ECU_TABLENAME = "SYS_ENTITYCHECKUP_";
    public static final String ECU_TK_KEY = "ECU_TK_KEY";
    public static final String ECU_FC_KEY = "ECU_FC_KEY";
    public static final String ECU_UNIT_KEY = "ECU_UNIT_KEY";
    public static final String ECU_CHECK = "ECU_CHECK";
    public static final String ECU_UNITNAME = "ECU_UNITNAME";
    public static final String ECU_JSYS = "ECU_JSYS";
    public static final String ECU_PERIOD = "PERIOD";
    public static final Map<String, String> EntityCheckUp_INFO = new HashMap<String, String>();
    public static final String XBYS_TITLE = "\u65b0\u62a5\u56e0\u7d20";
    public static final String JSYY_TITLE = "\u51cf\u5c11\u539f\u56e0";
    public static final String ENTITY_CHECK_TYPE = "1";
    public static final String XBYS_CODE = "XBYS";
    public static final String SNDM_CODE = "SNDM";
    public static final String DWDM_CODE = "DWDM";
    public static final String DWDM_CODE_CZ = "CODE";
    public static final String DWYSJC_CODE = "DWYSJC";
    public static final String BBLX_CODE = "BBLX";
    public static final String SNBBLX_CODE = "SNBBLX";
    public static final String JSYY_CODE = "JSYY";
    public static final String WEB_TAB_NAME_JSDW = "\u51cf\u5c11\u5355\u4f4d";
    public static final String WEB_TAB_NAME_YWDW = "\u6709\u8bef\u5355\u4f4d";
    public static final String WEB_TAB_NAME_HDJGFX = "\u6838\u5bf9\u7ed3\u679c\u5206\u6790";
    public static final String FRONT_END_TAB_NAME = "tabName";
    public static final String FRONT_END_TABLE_DATA = "tableData";
    public static final String FRONT_END_CLOUMNS = "columns";
    public static final String FRONT_END_ENUM_LIST = "EnumList";
    public static final String FRONT_END_CURRENT_TITLE = "currentTitle";
    public static final String FRONT_END_CONTRAST_TITLE = "contrastTitle";
    public static final String DEFAULT_VERSION_ID = "00000000-0000-0000-0000-000000000000";
    public static final String DEFAULT_DATATIME_TITLE = "DATATIME";
    public static final String DEFAULT_INDEX_VALUE = "00000000-0000-0000-0000-000000000000";
    public static final String VERSION_DIMENSION = "VERSIONID";
    public static final String FRONT_END_ABNORMAL = "abnormal";
    public static final String FRONT_END_ABNORMAL_MESSAGE = "abnormalMessage";
    public static final String FRONT_END_FROM_SCHEMES = "formSchemes";
    public static final String FRONT_END_SCHEME = "scheme";
    public static final String FRONT_END_CONSTRAST_INFO = "constrastInfo";
    public static final String FRONT_END_TITLE = "title";
    public static final String BBLX_TITLE = "\u62a5\u8868\u7c7b\u578b";
    public static final String SNDM_TITLE = "\u4e0a\u5e74\u4ee3\u7801";
    public static final String DWDM_TITLE = "\u5355\u4f4d\u4ee3\u7801";
    public static final String DWYSJC_TITLE = "\u5355\u4f4d\u9884\u7b97\u7ea7\u6b21";
    public static final String SNBBLX_TITLE = "\u4e0a\u5e74\u62a5\u8868\u7c7b\u578b";
    public static final int TABLE_STRING_FIELD_LENGTH = 50;

    static {
        EntityCheckUp_INFO.put(ECU_TK_KEY, "\u5f53\u524d\u4efb\u52a1key");
        EntityCheckUp_INFO.put(ECU_FC_KEY, "\u5f53\u524d\u62a5\u8868\u65b9\u6848key");
        EntityCheckUp_INFO.put(ECU_UNIT_KEY, "\u5355\u4f4d\u4e3b\u4ee3\u7801key");
        EntityCheckUp_INFO.put(ECU_CHECK, "\u7248\u672c\u53f7");
        EntityCheckUp_INFO.put(ECU_UNITNAME, "\u5355\u4f4d\u540d\u79f0");
        EntityCheckUp_INFO.put(ECU_JSYS, "\u51cf\u5c11\u56e0\u7d20");
    }
}

