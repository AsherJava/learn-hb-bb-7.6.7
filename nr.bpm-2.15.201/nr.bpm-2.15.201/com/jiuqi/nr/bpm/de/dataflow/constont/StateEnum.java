/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.constont;

public interface StateEnum {

    public static enum Confirm implements StateEnum
    {
        ACTION_CONFIRM("act_confirm", "CONFIRMED", "\u786e\u8ba4", "\u5df2\u786e\u8ba4"),
        BATCH_ACTION_CONFIRM("batch_act_confirm", "CONFIRMED", "\u6279\u91cf\u786e\u8ba4", "\u5df2\u786e\u8ba4"),
        CUSTOM_CONFIRM("cus_confirm", "CONFIRMED", "\u786e\u8ba4", "\u5df2\u786e\u8ba4");

        private String code;
        private String stateCode;
        private String name;
        private String stateName;

        private Confirm(String code, String stateCode, String name, String stateName) {
            this.code = code;
            this.stateCode = stateCode;
            this.name = name;
            this.stateName = stateName;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getStateCode() {
            return this.stateCode;
        }

        public String getStateName() {
            return this.stateName;
        }
    }

    public static enum Reject implements StateEnum
    {
        ACTION_REJECT("act_reject", "REJECTED", "\u9000\u56de", "\u5df2\u9000\u56de"),
        BATCH_ACTION_REJECT("batch_act_reject", "REJECTED", "\u6279\u91cf\u9000\u56de", "\u5df2\u9000\u56de"),
        CUSTOM_REJECT("cus_reject", "REJECTED", "\u9000\u56de", "\u5df2\u9000\u56de");

        private String code;
        private String stateCode;
        private String name;
        private String stateName;

        private Reject(String code, String stateCode, String name, String stateName) {
            this.code = code;
            this.stateCode = stateCode;
            this.name = name;
            this.stateName = stateName;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getStateCode() {
            return this.stateCode;
        }

        public String getStateName() {
            return this.stateName;
        }
    }

    public static enum Return implements StateEnum
    {
        ACTION_RETURNCHECK("act_return", "RETURNED", "\u9000\u5ba1", "\u5df2\u9000\u5ba1"),
        BATCH_ACTION_RETURNCHECK("batch_act_return", "RETURNED", "\u6279\u91cf\u9000\u5ba1", "\u5df2\u9000\u5ba1"),
        CUSTOM_RETURN("cus_return", "RETURNED", "\u9000\u5ba1", "\u5df2\u9000\u5ba1");

        private String code;
        private String stateCode;
        private String name;
        private String stateName;

        private Return(String code, String stateCode, String name, String stateName) {
            this.code = code;
            this.stateCode = stateCode;
            this.name = name;
            this.stateName = stateName;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getStateCode() {
            return this.stateCode;
        }

        public String getStateName() {
            return this.stateName;
        }
    }

    public static enum Upload implements StateEnum
    {
        ACTION_UPLOAD("act_upload", "UPLOADED", "\u4e0a\u62a5", "\u5df2\u4e0a\u62a5"),
        BATCH_ACTION_UPLOAD("batch_act_upload", "UPLOADED", "\u6279\u91cf\u4e0a\u62a5", "\u5df2\u4e0a\u62a5"),
        CUSTOM_UOLOAD("cus_upload", "UPLOADED", "\u4e0a\u62a5", "\u5df2\u4e0a\u62a5");

        private String code;
        private String stateCode;
        private String name;
        private String stateName;

        private Upload(String code, String stateCode, String name, String stateName) {
            this.code = code;
            this.stateCode = stateCode;
            this.name = name;
            this.stateName = stateName;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getStateCode() {
            return this.stateCode;
        }

        public String getStateName() {
            return this.stateName;
        }
    }

    public static enum Submit implements StateEnum
    {
        ACTION_SUBMITCHECK("act_submit", "SUBMITED", "\u9001\u5ba1", "\u5df2\u9001\u5ba1"),
        BATCH_ACTION_SUBMITCHECK("batch_act_submit", "SUBMITED", "\u6279\u91cf\u9001\u5ba1", "\u5df2\u9001\u5ba1"),
        CUSTOM_SUBMIT("cus_submit", "SUBMITED", "\u9001\u5ba1", "\u5df2\u9001\u5ba1");

        private String code;
        private String stateCode;
        private String name;
        private String stateName;

        private Submit(String code, String stateCode, String name, String stateName) {
            this.code = code;
            this.stateCode = stateCode;
            this.name = name;
            this.stateName = stateName;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getStateCode() {
            return this.stateCode;
        }

        public String getStateName() {
            return this.stateName;
        }
    }
}

