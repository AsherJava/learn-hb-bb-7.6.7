/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.common;

import com.jiuqi.nr.single.core.para.consts.PeriodType;

public class SingleConsts {
    public static final String DIR_PARA = "PARA";
    public static final String TASKFILE = "Task.ini";
    public static final String JIO_UNZIP_DIRNAME = "JIOFiles";
    public static final String JIO_IMPORT_PREFIX = "JIO";
    public static final String JQT_SUFFIX = ".JQT";
    public static final String TASK_INFO_STR = "TASK";
    public static final String TASK_PARA_STR = "PARA";
    public static final String TASK_DATA_STR = "DATA";
    public static final String TASK_DATA_DOC_STR = "SYS_DOC";
    public static final String TASK_QUERY_STR = "QUERY";
    public static final String TASK_ANALPARA_STR = "ANALPARA";
    public static final String TASK_ANALDATA_STR = "ANALDATA";
    public static final String TASKSIGNFILE = "TASKSIGN.TSK";
    public static final String TASKLINKFILE = "TaskLink.INI";
    public static final String NBTOROLSETFILE = "NBRolSet.ini";
    public static final String FMDMCODE = "FMDM";
    public static final String ANALFMDMCODE = "CSFMDM";
    public static final String PARA_FMDMJQT = "FMDM.JQT";
    public static final String FMDMTITLE = "\u5c01\u9762\u4ee3\u7801";
    public static final String HZFMDMCODE = "SYS_HZFMDY";
    public static final String COMMONINFOSECTION = "General";
    public static final String FILE_SPLICT_CHAR = "/";
    public static final String SYSLRGSFILE = "SYS_LRGS.DAT";
    public static final String BIZKEYORDERCTRLFILE = "FloatOrderCtrl.dat";
    public static final String BIZKEYORDERFILEDNAME = "SYS_ORDER";
    public static final int BIZKEYORDERFIELDLENGTH = 6;
    public static final int NAME_LENGTH = 30;
    public static final int MBNAME_LENGTH = 50;
    public static final int CHOOSE_NONE = 0;
    public static final int CHOOSE_BASEDATA = 1;
    public static final int CHOOSE_REPORTZB = 16;
    public static final int CHOOSE_FORMULA = 256;
    public static final int CHOOSE_ALL = 273;
    public static final String PARAZBMAPPING_ROOT_TAG = "ZBMAPPING_ROOT";
    public static final String PARAZBMAPPING_REPORT_TAG = "ZBMAPPING_REPORT";
    public static final String PARAZBMAPPING_REPORT_NAME_TAG = "ZBMAPPING_REPORT_NAME";
    public static final String PARAZBMAPPING_ZB_TAG = "ZBMAPPING_ZB";
    public static final String PARAZBMAPPING_ZB_NB_TAG = "ZB_NB";
    public static final String PARAZBMAPPING_ZB_SR_TAG = "ZB_SR";
    public static final String ROLSETTASKSEC = "Task";
    public static final String ROLSETSQTYPEIDENT = "SQType";
    public static final String ROLSETPERIODSEC = "Period";
    public static final String ROLSETPERIODIDENT = "Set";
    public static final String XMLSUFFIX = ".XML";
    public static final String FILED_MAPNEXUS = "MapNexus.Ini";
    public static final String FILED_MAPNEXUSEX = "MapNexusEx.Ini";

    public static PeriodType ChangepeiodType(String ciType) {
        if (new String("N").equals(ciType)) {
            return PeriodType.YEAR;
        }
        if (new String("H").equals(ciType)) {
            return PeriodType.HALFYEAR;
        }
        if (new String("J").equals(ciType)) {
            return PeriodType.SEASON;
        }
        if (new String("Y").equals(ciType)) {
            return PeriodType.MONTH;
        }
        if (new String("X").equals(ciType)) {
            return PeriodType.TENDAY;
        }
        if (new String("R").equals(ciType)) {
            return PeriodType.DAY;
        }
        if (new String("Z").equals(ciType)) {
            return PeriodType.WEEK;
        }
        if (new String("B").equals(ciType)) {
            return PeriodType.CUSTOM;
        }
        return PeriodType.YEAR;
    }

    public static String GetTaskType(String ciType) {
        if (new String("H").equals(ciType)) {
            return "B";
        }
        if (new String("J").equals(ciType)) {
            return "J";
        }
        if (new String("Y").equals(ciType)) {
            return "Y";
        }
        return "N";
    }
}

