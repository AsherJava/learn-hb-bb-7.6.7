/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.util;

public class MdAgeConst {

    public static interface SubjectAging {
        public static final String TABLENAME = "MD_SUBJECT_AGING";
        public static final String FN_SUBJECTCODE = "SUBJECTCODE";
        public static final String FN_TASKPERIOD = "TASKPERIOD";
        public static final String FN_AGINGCODE = "AGINGCODE";
        public static final String FN_DEFAULTVAL = "DEFAULTVAL";
    }

    public static interface Aging {
        public static final String TABLENAME = "MD_AGING";
        public static final String FN_CODE = "CODE";
        public static final String FN_NAME = "NAME";
        public static final String FN_PERIODTYPE = "PERIODTYPE";
        public static final String FN_BEGINPERIOD = "BEGINPERIOD";
        public static final String FN_ENDPERIOD = "ENDPERIOD";
    }
}

