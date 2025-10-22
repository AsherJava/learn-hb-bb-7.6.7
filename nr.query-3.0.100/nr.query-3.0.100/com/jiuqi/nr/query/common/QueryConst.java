/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryConst {
    public static final String UPDATETIME = "UPDATETIME";
    public static final String TABLE_NAME_CALIBERGROUP = "SYS_QUERYCALIBERGROUP";
    public static final String QCG_ID = "QCG_ID";
    public static final String QCG_TASKID = "QCG_TASKID";
    public static final String QCG_TITLE = "QCG_TITLE";
    public static final String QCG_PARENTID = "QCG_PARENTID";
    public static final String QCG_ORDER = "QCG_ORDER";
    public static final String QCG_UPDATETIME = "UPDATETIME";
    public static final String TABLE_NAME_CALIBERDEFINE = "SYS_QUERYCALIBERDEFINE";
    public static final String QCD_ID = "QCD_ID";
    public static final String QCD_TASKID = "QCD_TASKID";
    public static final String QCD_TITLE = "QCD_TITLE";
    public static final String QCD_GROUPID = "QCD_GROUPID";
    public static final String QCD_ORDER = "QCD_ORDER";
    public static final String QCD_UPDATETIME = "UPDATETIME";
    public static final String TABLE_NAME_CALIBERITEM = "SYS_QUERYCALIBERITEM";
    public static final String QCI_ID = "QCI_ID";
    public static final String QCI_TASKID = "QCI_TASKID";
    public static final String QCI_TITLE = "QCI_TITLE";
    public static final String QCI_CALIBERID = "QCI_CALIBERID";
    public static final String QCI_ORDER = "QCI_ORDER";
    public static final String QCI_FORMULA = "QCI_FORMULA";
    public static final String QCI_UPDATETIME = "UPDATETIME";
    public static final String TABLE_NAME_QUERYMODELGROUP = "SYS_QUERYMODELGROUP";
    public static final String QMG_ID = "QMG_ID";
    public static final String QMG_TASKID = "QMG_TASKID";
    public static final String QMG_TITLE = "QMG_TITLE";
    public static final String QMG_PARENTID = "QMG_PARENTID";
    public static final String QMG_ORDER = "QMG_ORDER";
    public static final String QMG_DESCRIPTION = "QMG_DESCRIPTION";
    public static final String QMG_UPDATETIME = "UPDATETIME";
    public static final String QMG_TYPE = "QMG_TYPE";
    public static final String QMG_CREATOR = "QMG_CREATOR";
    public static final String TABLE_NAME_QUERYMODELDEFINE = "SYS_QUERYMODELDEFINE";
    public static final String QMD_TASKID = "QMD_TASKID";
    public static final String QMD_ID = "QMD_ID";
    public static final String QMD_TITLE = "QMD_TITLE";
    public static final String QMD_ORDER = "QMD_ORDER";
    public static final String QMD_GROUPID = "QMD_GROUPID";
    public static final String QMD_CREATOR = "QMD_CREATOR";
    public static final String QMD_LAYOUT = "QMD_LAYOUT";
    public static final String QMD_CONDITIONS = "QMD_CONDITIONS";
    public static final String QMD_DESCRIPTION = "QMD_DESCRIPTION";
    public static final String QMD_EXTENSION = "QMD_EXTENSION";
    public static final String QMD_UPDATETIME = "UPDATETIME";
    public static final String QMD_SCHEMEID = "QMD_SCHEMEID";
    public static final String QMD_TYPE = "QMD_TYPE";
    public static final String TABLE_NAME_QUERYBLOCKDEFINE = "SYS_QUERYBLOCKDEFINE";
    public static final String QBD_ID = "QBD_ID";
    public static final String QBD_MODELID = "QBD_MODELID";
    public static final String QBD_TITLE = "QBD_TITLE";
    public static final String QBD_HASUSERFORM = "QBD_HASUSERFORM";
    public static final String QBD_QUERYBLOCKTYPE = "QBD_QUERYBLOCKTYPE";
    public static final String QBD_BLOCKINFO = "QBD_BLOCKINFO";
    public static final String QBD_GRIDDATA = "QBD_GRIDDATA";
    public static final String QBD_FORMDATA = "QBD_FORMDATA";
    public static final String QBD_PRINT = "QBD_PRINT";
    public static final String QBD_UPDATETIME = "UPDATETIME";
    public static final String QBD_MASTERINFOR = "QBD_MASTERINFOR";
    public static final String QBD_MODELCATEGORY = "QBD_MODELCATEGORY";
    public static final String TABLE_NAME_QUERYDIMENSIONDEFINE = "SYS_QUERYDIMENSIONDEFINE";
    public static final String TABLE_NAME_QUERYTITLEDEFINE = "SYS_QUERYTITLEDEFINE";
    public static final String TABLE_NAME_QUERYSELECTITEM = "SYS_QUERYSELECTITEM";
    public static final String CONST_BOOTGROUP = "b8079ac0-dc15-11e8-969b-64006a6432d8";
    public static final String[] QUERY_FUNCPOINTS = new String[]{"HAndVQuery", "MixedQuery", "Chart", "ANALYZETABLE", "DASHBOARD", "AnalysisReport", "BigScreen"};
    public static final String PACKAGENAME = "com.jiuqi.nr.query";
    public static final String GATHERTYPE_ACTIONS = "query_actions";
    private static final String COLOR16_REGEX = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
    private static final Pattern pattern = Pattern.compile("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$");
    public static final int COLOR_BLACK = 0x444444;
    public static final int COLOR_GRAY = 0x626262;
    public static final int COLOR_GRAY_WHITE = 0xF8F8F8;
    public static final int COLOR_GRAY_WHITE_2 = 0xF1F1F1;
    public static final int COLOR_WHITE = 0xFFFFFF;
    public static final int COLOR_BLUE = 255;

    public static int htmlColorToInt(String color) {
        Matcher matcher;
        if (color == null) {
            color = "#0F0F0F";
        }
        if (!(matcher = pattern.matcher(color)).matches()) {
            color = "#0F0F0F";
        }
        return Integer.parseInt(color.substring(1), 16);
    }

    public static enum GatherType {
        TEMPLATE("\u6a21\u677f", "TEMPLATE"),
        ACTION("\u6309\u94ae", "ACTION"),
        VIEW("\u89c6\u56fe", "VIEW"),
        INFOVIEW("\u4fe1\u606f\u89c6\u56fe", "INFOVIEW");

        private String code;
        private String title;

        private GatherType(String title, String code) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return this.code;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

