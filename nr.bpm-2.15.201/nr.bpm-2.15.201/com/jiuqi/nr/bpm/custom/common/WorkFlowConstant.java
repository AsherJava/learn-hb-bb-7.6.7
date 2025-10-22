/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.bpm.custom.common;

import org.json.JSONObject;

public class WorkFlowConstant {
    public static final int PARTICIPANT_NOVALUE = 0;
    public static final int PARTICIPANT_USER = 1;
    public static final int PARTICIPANT_ROLE = 2;
    public static final int PARTICIPANT_ROLEORUSER = 3;
    public static final int WFDEFINE_STATE_MAINTENANCE = 0;
    public static final int WFDEFINE_STATE_RELEASE = 1;
    public static final int CHECK_PASS_IGNORE = 0;
    public static final int CHECK_PASS_DESC = 1;
    public static final int CHECK_PASS_NOERROR = 2;

    public static enum FLOWOBJ {
        REPORT("report", "\u62a5\u8868"),
        REPORTSCHEME("reportscheme", "\u62a5\u8868\u65b9\u6848");

        private String code;
        private String desc;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private FLOWOBJ(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FLOWOBJ getValue(String code) {
            for (FLOWOBJ color : FLOWOBJ.values()) {
                if (!color.getCode().equals(code)) continue;
                return color;
            }
            return null;
        }

        public String toString() {
            JSONObject js = new JSONObject();
            js.put("code", (Object)this.code);
            js.put("desc", (Object)this.desc);
            return js.toString();
        }
    }

    public static enum SOLUOBJ {
        TASK_REPORT("task_report", "\u4efb\u52a1/\u62a5\u8868\u65b9\u6848");

        private String code;
        private String desc;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private SOLUOBJ(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static SOLUOBJ getValue(String code) {
            for (SOLUOBJ color : SOLUOBJ.values()) {
                if (!color.getCode().equals(code)) continue;
                return color;
            }
            return null;
        }

        public String toString() {
            JSONObject js = new JSONObject();
            js.put("code", (Object)this.code);
            js.put("desc", (Object)this.desc);
            return js.toString();
        }
    }
}

