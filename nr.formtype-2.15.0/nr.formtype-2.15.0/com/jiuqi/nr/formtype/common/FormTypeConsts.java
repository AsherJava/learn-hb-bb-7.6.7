/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.common;

import java.util.HashMap;
import java.util.Map;

public class FormTypeConsts {
    public static final String TREE_TITLE_SEPARATOR = " | ";
    public static final String ROOT_GROUP_KEY = "--";
    public static final String ROOT_GROUP_TITLE = "\u5168\u90e8\u62a5\u8868\u7c7b\u578b";
    public static final String FORMTYPE_CODE_REG = "^MD_BBLX\\w{0,24}";
    public static final String TENANT_NAME = "__default_tenant__";
    public static final String BBLX_BASE_DATA_GROUP = "FORMTYPE";
    public static final String BBLX_BASE_DATA_GROUP_TITLE = "\u62a5\u8868\u7c7b\u578b";
    public static final String DEFAULT_BBLX_CODE = "MD_BBLX";
    public static final String DEFAULT_BBLX_TITLE = "\u62a5\u8868\u7c7b\u578b";
    public static final String DEFAULT_BDG_NAME = "NR_GROUP";
    public static final String DEFAULT_BDG_ROOT_NAME = "-";
    public static final String DEFAULT_BDG_TITLE = "\u62a5\u8868";
    public static final String EXTEND_FIELD_ICON = "ICON";
    public static final String EXTEND_FIELD_ICON_TITLE = "\u56fe\u6807";
    public static final String EXTEND_FIELD_NATURE = "UNIT_NATURE";
    public static final String EXTEND_FIELD_NATURE_TITLE = "\u5355\u4f4d\u6027\u8d28";
    public static final String EXTEND_FIELD_UPDATETIME = "UPDATETIME";
    public static final String EXTEND_FIELD_UPDATETIME_TITLE = "\u66f4\u65b0\u65f6\u95f4";
    public static final String EXTEND_FIELD_NAME_EN_US = "NAME_EN_US";
    public static final String EXTEND_FIELD_NAME_EN_US_TITLE = "\u82f1\u6587\u540d\u79f0";
    public static final String ORG_BBLX_ZB_CODE = "BBLX";
    public static final String ORG_BBLX_ZB_TITLE = "\u62a5\u8868\u7c7b\u578b";
    public static final String SYS_OPTIONS_UNIT_TREE = "unit_tree_system_config";
    public static final String SYS_OPTIONS_UNIT_TREE_ICONSCHEME = "name_of_icon_scheme_key";
    public static final String SYS_OPTIONS_UNIT_TREE_ICONSCHEME_NR = "#NR-icon";
    public static final String SYS_OPTIONS_FORMTYPE = "form_type_option_id";
    public static final String SYS_OPTIONS_FORMTYPE_TITLE = "\u62a5\u8868\u7c7b\u578b";
    public static final String SYS_OPTIONS_FORMTYPE_GLMS = "formtype_option_gzwms";
    public static final String SYS_OPTIONS_FORMTYPE_GLMS_TITLE = "\u7ba1\u7406\u6a21\u5f0f";
    public static final String SYS_OPTIONS_FORMTYPE_BZMS_TITLE = "\u6807\u51c6\u6a21\u5f0f";
    public static final String SYS_OPTIONS_FORMTYPE_GZWMS_TITLE = "\u56fd\u8d44\u59d4\u6a21\u5f0f";
    public static final String SYS_OPTIONS_FORMTYPE_BZMS_VALUE = "0";
    public static final String SYS_OPTIONS_FORMTYPE_GZWMS_VALUE = "1";
    public static final String SYS_OPTIONS_FORMTYPE_ICONSCHEME_ID = "formtype_option_iconscheme";
    public static final String SYS_OPTIONS_FORMTYPE_ICONSCHEME_TITLE = "\u7cfb\u7edf\u56fe\u6807\u65b9\u6848";
    public static final String SYS_OPTIONS_FORMTYPE_ICONSCHEME_NR = "\u9ed8\u8ba4\u65b9\u6848";
    public static final String SYS_OPTIONS_FORMTYPE_ICONSCHEME_DJB = "\u7ecf\u5178\u65b9\u6848";
    public static final String SYS_OPTIONS_FORMTYPE_ICONSCHEME_NRV = "0";
    public static final String SYS_OPTIONS_FORMTYPE_ICONSCHEME_DJBV = "1";
    public static final Map<String, String> ICONS_FOLDER = new HashMap<String, String>();
    public static final String UNIT_SUBFIX_BB = "\uff08\u672c\u90e8\uff09";
    public static final String UNIT_SUBFIX_CE = "\uff08\u5dee\u989d\uff09";
    public static final String ORG_EXTEND_IMPORT_STATE = "importstate";
    public static final String ORG_EXTEND_CHECKED = "_ORG_EXTEND_CHECKED";
    public static final String ORG_EXTEND_BATCH_ADD = "_ORG_EXTEND_BATCH_ADD";
    public static final String ORG_EXTEND_IMPORTSTATE = "importstate";
    public static final Integer ORG_EXTEND_IMPORTSTATE_ERRVALUE;
    public static final String ORG_EXTEND_IMPORTMEMO = "importmemo";

    static {
        ICONS_FOLDER.put("0", "nr-icons");
        ICONS_FOLDER.put("1", "djb-icons");
        ORG_EXTEND_IMPORTSTATE_ERRVALUE = 9;
    }
}

