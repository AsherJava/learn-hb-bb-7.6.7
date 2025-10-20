/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.ncell.org.enums;

import java.util.Date;
import java.util.GregorianCalendar;

public class GcOrgConst {
    public static final String ORG_ROOT_CODE = "-";
    public static final String SPLIT_PARENTS = "/";
    public static final String ORG_TABLE_KEY = "3434C673-DA3F-4C38-A06F-FA312BD5B057";
    public static final String ORG_TABLE_NAME = "MD_ORG";
    public static final String ORG_TABLE_PREFIX = "MD_ORG";
    public static final String TYPE_N_GCREPORT = "MD_ORG_CORPORATE";
    public static final String TYPE_T_GCREPORT = "\u6cd5\u4eba\u7ec4\u7ec7";
    public static final String TYPE_DB_GCREPORT = "MD_ORG_CORPORATE";
    public static final String EXTERNALDATA_KEY = "saveDatas";
    public static final String SDFPATTEN = "yyyy-MM-dd HH:mm:ss";
    public static final String BBLX_MERGEUNIT = "9";
    public static final String BBLX_DIFFUNIT = "1";
    public static final String BBLX_SINGLENIT = "0";
    public static final String MANAGEMENT = "MD_ORG_MANAGEMENT";
    public static final String CORPORATE = "MD_ORG_CORPORATE";
    public static final String[] FILTERFIELD = new String[]{"ID", "LEVE", "lEVEL", "VALIDTIME", "INVALIDTIME", "ORGID", "PARENTS", "MERGEUNITFLAG", "VER", "RECOVERYFLAG", "GCPARENTS"};
    public static final String[] SYSTEMFIELD = new String[]{"NAME", "CODE", "TITLE", "PARENTID", "PARENTCODE", "BASEUNITID", "DIFFUNITID", "BBLX", "CURRENCYID", "CURRENCYIDS", "ORGTYPEID", "ADJTYPEIDS", "YWBKCODE", "GCYWLXCODE", "AREACODE", "MERGERACCOUNTS"};
    public static final String[] MULTIFIELD = new String[]{"CURRENCYIDS", "ADJTYPEIDS"};
    public static final String[] NULLABLEEFIELD = new String[]{"NAME", "CODE"};
    public static final String[] READONLYFIELD = new String[]{"CODE"};
    public static final String[] REF_TABLENAME = new String[]{"PARENTID", "BASEUNITID", "DIFFUNITID"};
    public static final String[] ID_TABLENAME = new String[]{"MD_CURRENCY", "MD_GCORGTYPE", "MD_GCADJTYPE"};
    public static final Date ORG_VERDATE_MAX = new GregorianCalendar(9999, 11, 31, 0, 0, 0).getTime();
    public static final Date ORG_VERDATE_MIN = new GregorianCalendar(1970, 0, 1, 0, 0, 0).getTime();

    public static class FIELDS {
        public static final String FN_ID = "ID";
        public static final String FN_VER = "VER";
        public static final String FN_CODE = "CODE";
        public static final String FN_NAME = "NAME";
        public static final String FN_SHORTNAME = "SHORTNAME";
        public static final String FN_VALIDTIME = "VALIDTIME";
        public static final String FN_INVALIDTIME = "INVALIDTIME";
        public static final String FN_ORGID = "ORGID";
        public static final String FN_PARENTCODE = "PARENTCODE";
        public static final String FN_PARENTS = "PARENTS";
        public static final String FN_GCPARENTS = "GCPARENTS";
        public static final String FN_ORDINAL = "ORDINAL";
        public static final String FN_BASEUNITID = "BASEUNITID";
        public static final String FN_DIFFUNITID = "DIFFUNITID";
        public static final String FN_MERGEUNITID = "MERGEUNITID";
        public static final String FN_ORGKIND = "ORGKIND";
        public static final String FN_LEAF = "LEAF";
        public static final String FN_COMPTYPE = "COMPTYPE";
        public static final String FN_ORGTYPEID = "ORGTYPEID";
        public static final String FN_SPLITID = "SPLITID";
        public static final String FN_SPLITSCALE = "SPLITSCALE";
        public static final String FN_SPLITDIFFFLAG = "SPLITDIFFFLAG";
        public static final String FN_BBLX = "BBLX";
        public static final String FN_STOPFLAG = "STOPFLAG";
        public static final String FN_FREEZEFLAG = "FREEZEFLAG";
        public static final String FN_FREEZETIME = "FREEZETIME";
        public static final String FN_RECOVERYFLAG = "RECOVERYFLAG";
        public static final String FN_CURRENCYIDS = "CURRENCYIDS";
        public static final String FN_CURRENCYID = "CURRENCYID";
        public static final String FN_UPDATETIME = "UPDATETIME";
        public static final String FN_CREATEUSER = "CREATEUSER";
        public static final String FN_CREATETIME = "CREATETIME";
        public static final String FN_ADJTYPEIDS = "ADJTYPEIDS";
        public static final String FN_DISPOSALVIRTUALUNITID = "DISPOSALVIRTUALUNITID";
        public static final String FN_QYDM = "QYDM";
    }
}

