/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class InvestConst {
    public static final String OVERLAYIMPORT = "overlayImport";
    public static final String OVERWRITEIMPOT = "overwriteImpot";
    public static final String BASE = "gc:invest:base";
    public static final Map<String, String> tableName2TitleMap = new ConcurrentHashMap<String, String>(16);

    private InvestConst() {
    }

    static {
        tableName2TitleMap.put("GC_INVESTBILL", "\u6295\u8d44\u5355\u636e\u4e3b\u8868");
        tableName2TitleMap.put("GC_INVESTBILLITEM", "\u6295\u8d44\u5355\u636e\u5b50\u8868");
        tableName2TitleMap.put("GC_FVCH_FIXEDITEM", "\u516c\u5141\u4ef7\u503c\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7\u8868");
        tableName2TitleMap.put("GC_FVCH_OTHERITEM", "\u516c\u5141\u4ef7\u503c\u5176\u5b83\u8d44\u4ea7\u7c7b\u8868");
    }

    public static enum FairValueOffsetStatus {
        DONE("\u5df2\u5b8c\u6210"),
        UN_DO("");

        private String content;

        private FairValueOffsetStatus(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static enum OffsetStatus {
        DONE("\u5df2\u5b8c\u6210"),
        UN_DO("");

        private String content;

        private OffsetStatus(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static enum FairValueAdjustStatus {
        DONE(1),
        UN_DO(0);

        private int code;

        private FairValueAdjustStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }
}

