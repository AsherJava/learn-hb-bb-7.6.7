/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.common;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static final int CONFIG_GENERATE_TYPE = 1;
    public static final int CONFIG_IMPORT_TYPE = 2;
    public static final String REAL_EXPORT_WORK_DIR = "JIOEXPORT";
    public static final String WORK_TEMP_DIR = "JIOIMPORTTEMP";

    public static enum ImportStatus {
        NONE(0, "\u65e0"),
        READY(1, "\u7b49\u5f85\u6267\u884c"),
        DOING(2, "\u5bfc\u5165\u4e2d"),
        SUCCESS(3, "\u5bfc\u5165\u5b8c\u6210"),
        FAIL(4, "\u5bfc\u5165\u5931\u8d25"),
        CANCEL(5, "\u5df2\u53d6\u6d88");

        private int status;
        private String title;
        private static final Map<Integer, String> STATUS_TITLE_MAP;

        private ImportStatus(int status, String title) {
            this.status = status;
            this.title = title;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static String getTitle(int status) {
            return STATUS_TITLE_MAP.get(status);
        }

        static {
            STATUS_TITLE_MAP = new HashMap<Integer, String>();
            for (ImportStatus status : ImportStatus.values()) {
                STATUS_TITLE_MAP.put(status.status, status.title);
            }
        }
    }

    public static enum GenerateStatus {
        NONE(0, "\u65e0"),
        READY(1, "\u7b49\u5f85\u6267\u884c"),
        DOING(2, "\u751f\u6210\u4e2d"),
        SUCCESS(3, "\u5df2\u751f\u6210"),
        FAIL(4, "\u751f\u6210\u5931\u8d25"),
        CANCEL(5, "\u5df2\u53d6\u6d88"),
        DESTROYED(6, "\u5df2\u6e05\u7406");

        private int status;
        private String title;
        private static final Map<Integer, String> STATUS_TITLE_MAP;

        private GenerateStatus(int status, String title) {
            this.status = status;
            this.title = title;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static String getTitle(int status) {
            return STATUS_TITLE_MAP.get(status);
        }

        static {
            STATUS_TITLE_MAP = new HashMap<Integer, String>();
            for (GenerateStatus status : GenerateStatus.values()) {
                STATUS_TITLE_MAP.put(status.status, status.title);
            }
        }
    }
}

