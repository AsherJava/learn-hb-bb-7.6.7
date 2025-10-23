/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.common;

public class JQRDataStateConst {
    public static final int usedworkflow = 0;
    public static final int nouseworkflow = 1;
    public static final String startUpTitle = "\u5df2\u542f\u7528";
    public static final String StopUpTitle = "\u672a\u542f\u7528";
    public static final int DATA_STATE_UNKNOWN = 0;
    public static final int DATA_STATE_COMMITED = 1;
    public static final int DATA_STATE_RECEIVED = 2;
    public static final int DATA_STATE_REJECTED = 3;
    public static final int DATA_STATE_PARTCOMMITED = 4;
    public static final int DATA_STATE_PARTREJECTED = 5;
    private static final String DATA_STATE_TITLE_UNKNOWN = "\u672a\u4e0a\u62a5";
    private static final String DATA_STATE_TITLE_COMMITED = "\u5df2\u4e0a\u62a5";
    private static final String DATA_STATE_TITLE_RECEIVED = "\u5df2\u786e\u8ba4";
    private static final String DATA_STATE_TITLE_REJECTED = "\u5df2\u9000\u56de";
    private static final String DATA_STATE_TITLE_PARTCOMMITED = "\u90e8\u5206\u4e0a\u62a5";
    private static final String DATA_STATE_TITLE_PARTREJECTED = "\u90e8\u5206\u9000\u56de";
    public static final int COMMIT_TYPE_UNIT = 0;
    public static final int COMMIT_TYPE_REPORT = 1;
    @Deprecated
    public static final int COMMIT_TYPE_CATEGORY = 4;
    public static final String COMMIT_TYPE_UNIT_TITLE = "\u6309\u5355\u4f4d\u4e0a\u62a5";
    public static final String COMMIT_TYPE_REPORT_TITLE = "\u6309\u62a5\u8868\u4e0a\u62a5";
    public static final int commit_code = 1;
    public static final int reject_code = 3;
    public static final int getback_code = 4;
    public static final int innergetback_code = 5;
    public static final int innerCommit_code = 11;
    public static final int innerReject_code = 22;
    public static final int receive_code = 2;
    public static final int cancelReceive_code = 44;

    public static String getCommitedTypeTitle(int state) {
        switch (state) {
            case 0: {
                return COMMIT_TYPE_UNIT_TITLE;
            }
            case 1: {
                return COMMIT_TYPE_REPORT_TITLE;
            }
        }
        return COMMIT_TYPE_UNIT_TITLE;
    }

    public static String getDataStateTitle(int state) {
        switch (state) {
            case 0: {
                return DATA_STATE_TITLE_UNKNOWN;
            }
            case 1: {
                return DATA_STATE_TITLE_COMMITED;
            }
            case 2: {
                return DATA_STATE_TITLE_RECEIVED;
            }
            case 3: {
                return DATA_STATE_TITLE_REJECTED;
            }
            case 4: {
                return DATA_STATE_TITLE_PARTCOMMITED;
            }
            case 5: {
                return DATA_STATE_TITLE_PARTREJECTED;
            }
        }
        return "";
    }
}

