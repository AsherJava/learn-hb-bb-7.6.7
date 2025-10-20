/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.consts;

public final class GCConst {
    public static final String gcCacheNamePrefix = "gcreport:";
    public static final String ONLYQUERYEMPTY = "OnlyQueryEmpty";
    public static final int SIGN_YES = 1;
    public static final int SIGN_NO = 0;
    public static final String NONE = "NONE";
    public static final String FUNCTION_GROUP = "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    public static final String OTHER_FUNCTION_GROUP = "\u5176\u5b83\u51fd\u6570";

    public static interface CacheName {
        public static final String DIMENSION_MANAGEMENT = "gcreport:dimManagement";
        public static final String CONSOLIDATE_OPTION = "gcreport:conOption";
        public static final String CONSOLIDATE_TASK = "gcreport:conTask";
        public static final String FINANCIALCHECKSCHEME = "gcreport:conFinScheme";
        public static final String FLEXIBLE_RULE = "gcreport:flexRule";
        public static final String BASEDATA = "gcreport:conBaseData";
        public static final String SYSTEM_STATE = "gcreport:systemState";
        public static final String INPUTDATASCHEME = "gcreport:inputDataScheme";
        public static final String TEMPLATEENTSQLDAO = "gcreport:templateEntSqlDao";
    }

    public static interface SYSTEM {
        public static final String CNY = "CNY";
        public static final String CNYVALUE = "00000000-0000-0000-0000-000000000001";
        public static final int PERIOD_MONTH_MAX = 12;
        public static final String FORMULA_PHS = "PHS";
        public static final String SPLITCHAR = ";";
        public static final Integer[] YEARS = new Integer[]{2017, 2018, 2019, 2020, 2021};
        public static final String GCBUSINESS_TABLE_PREFIX = "GC_";
    }

    public static interface ORGTYPE {
        public static final String CORPORATE = "MD_ORG_CORPORATE";
        public static final String MANAGEMENT = "MD_ORG_MANAGEMENT";
        public static final String ORG = "MD_ORG";
    }

    public static interface ENAMESET {
        public static final String E_NAME_ORG = "MD_ORG";
        public static final String E_NAME_DATATIME = "DATATIME";
        public static final String E_NAME_CURRENCY = "MD_CURRENCY";
        public static final String E_NAME_ORGTYPE = "MD_GCORGTYPE";
    }

    public static interface FIELDNAME {
        public static final String ACCOUNTSUBJECT_ORIENT = "ORIENT";
        public static final String ACCOUNTSUBJECT_BUSINESSTYPE = "BUSINESSTYPE";
        public static final String NR_FLOATORDER = "FLOATORDER";
        public static final String E_ORG = "MDCODE";
        public static final String E_PERIOD = "DATATIME";
        public static final String E_CURRENCY = "MD_CURRENCY";
        public static final String E_ORGTYPE = "MD_GCORGTYPE";
        public static final String ID = "ID";
        public static final String BIZKEYORDER = "BIZKEYORDER";
        public static final String ORG_ONLINEFLAG = "ONLINEFLAG";
        public static final String ORG_BBLX = "BBLX";
        public static final String ORG_ORGTYPEID = "ORGTYPEID";
        public static final String ORG_ADJTYPEIDS = "ADJTYPEIDS";
        public static final String ORG_CREATETIME = "CREATETIME";
        public static final String ORG_UPDATETIME = "UPDATETIME";
        public static final String ORG_PARENTID = "PARENTID";
        public static final String YWBKCODE = "YWBKCODE";
        public static final String GCYWLXCODE = "GCYWLXCODE";
        public static final String AREACODE = "AREACODE";
        public static final String TZYZMSCODE = "TZYZMSCODE";
        public static final String PROJECTTITLE = "PROJECTTITLE";
    }

    public static interface TABLENAME {
        public static final String MD_ACCOUNTSUBJECT = "MD_ACCOUNTSUBJECT";
        public static final String MD_BUSINESSTYPE = "MD_BUSINESSTYPE";
        public static final String MD_GCBUSINESSTYPE = "MD_GCBUSINESSTYPE";
        public static final String MD_GROSSPROFITRATE = "MD_GROSSPROFITRATE";
        public static final String MD_CURRENCY = "MD_CURRENCY";
        public static final String ORG = "MD_ORG";
        public static final String ORG_CORPORATE = "MD_ORG_CORPORATE";
        public static final String ORG_MANAGEMENT = "MD_ORG_MANAGEMENT";
        public static final String MD_GCORGTYPE = "MD_GCORGTYPE";
        public static final String MD_AREA = "MD_AREA";
        public static final String MD_PROJECT = "MD_PROJECT";
        public static final String MD_YWBK = "MD_YWBK";
        public static final String MD_GCYWLX = "MD_GCYWLX";
        public static final String MD_TZYZMS = "MD_TZYZMS";
        public static final String MD_GCSUBJECT = "MD_GCSUBJECT";
        public static final String MD_ASSETTYPE = "MD_ASSETTYPE";
        public static final String MD_RATETYPE = "MD_RATETYPE";
        public static final String MD_CARRYOVER = "MD_CARRYOVER";
        public static final String MD_TZYSFILED = "MD_TZYSFILED";
        public static final String MD_TZYSFILEDDJ = "MD_TZYSFILEDDJ";
    }

    public static interface ADDRESS {
        public static final String ADD_CERRIFICATE_POOL = "certificatePoolAddress";
        public static final String ADDRESS_MESSAGE_CENTER = "messageCenterAddress";
    }
}

