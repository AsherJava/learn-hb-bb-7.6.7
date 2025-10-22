/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.upload.utils;

import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;

public enum ActionStateEnum {
    ORIGINAL(UploadState.ORIGINAL, "ORIGINAL", "\u672a\u77e5"){

        @Override
        public ActionStateEnum getCurrentState() {
            return ORIGINAL;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return ORIGINAL.getStateName();
        }
    }
    ,
    ORIGINAL_UPLOAD(UploadState.ORIGINAL_UPLOAD, "ORIGINAL_UPLOAD", "\u672a\u4e0a\u62a5"){

        @Override
        public ActionStateEnum getCurrentState() {
            return ORIGINAL_UPLOAD;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return ORIGINAL_UPLOAD.getStateName();
        }
    }
    ,
    ORIGINAL_SUBMIT(UploadState.ORIGINAL_SUBMIT, "ORIGINAL_SUBMIT", "\u672a\u9001\u5ba1"){

        @Override
        public ActionStateEnum getCurrentState() {
            return ORIGINAL_SUBMIT;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return ORIGINAL_SUBMIT.getStateName();
        }
    }
    ,
    SUBMITED(UploadState.SUBMITED, "SUBMITED", "\u5df2\u9001\u5ba1"){

        @Override
        public ActionStateEnum getCurrentState() {
            return SUBMITED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return SUBMITED.getStateName();
        }
    }
    ,
    RETURNED(UploadState.RETURNED, "RETURNED", "\u5df2\u9000\u5ba1"){

        @Override
        public ActionStateEnum getCurrentState() {
            return RETURNED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return RETURNED.getStateName();
        }
    }
    ,
    UPLOADED(UploadState.UPLOADED, "UPLOADED", "\u5df2\u4e0a\u62a5"){

        @Override
        public ActionStateEnum getCurrentState() {
            return UPLOADED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return UPLOADED.getStateName();
        }
    }
    ,
    CANCEL_CONFIRMED(UploadState.UPLOADED, "UPLOADED", "\u5df2\u4e0a\u62a5"){

        @Override
        public ActionStateEnum getCurrentState() {
            return CANCEL_CONFIRMED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return CANCEL_CONFIRMED.getStateName();
        }
    }
    ,
    CONFIRMED(UploadState.CONFIRMED, "CONFIRMED", "\u5df2\u786e\u8ba4"){

        @Override
        public ActionStateEnum getCurrentState() {
            return CONFIRMED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return CONFIRMED.getStateName();
        }
    }
    ,
    REJECTED(UploadState.REJECTED, "REJECTED", "\u5df2\u9000\u56de"){

        @Override
        public ActionStateEnum getCurrentState() {
            return REJECTED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            return REJECTED.getStateName();
        }
    }
    ,
    PART_START(UploadState.PART_START, "PART_START", "\u90e8\u5206\u5f00\u59cb"){

        @Override
        public ActionStateEnum getCurrentState() {
            return PART_START;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u672a\u4e0a\u62a5";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u672a\u4e0a\u62a5";
            }
            return PART_START.getStateName();
        }
    }
    ,
    PART_SUBMITED(UploadState.PART_SUBMITED, "PART_SUBMITED", "\u90e8\u5206\u9001\u5ba1"){

        @Override
        public ActionStateEnum getCurrentState() {
            return PART_SUBMITED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u62a5\u8868\u9001\u5ba1";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u5206\u7ec4\u9001\u5ba1";
            }
            return PART_SUBMITED.getStateName();
        }
    }
    ,
    PART_UPLOADED(UploadState.PART_UPLOADED, "PART_UPLOADED", "\u90e8\u5206\u4e0a\u62a5"){

        @Override
        public ActionStateEnum getCurrentState() {
            return PART_UPLOADED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u62a5\u8868\u4e0a\u62a5";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u5206\u7ec4\u4e0a\u62a5";
            }
            return PART_UPLOADED.getStateName();
        }
    }
    ,
    PART_CONFIRMED(UploadState.PART_CONFIRMED, "PART_CONFIRMED", "\u90e8\u5206\u786e\u8ba4"){

        @Override
        public ActionStateEnum getCurrentState() {
            return PART_CONFIRMED;
        }

        @Override
        public String getStateName(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u62a5\u8868\u786e\u8ba4";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u5206\u7ec4\u786e\u8ba4";
            }
            return PART_CONFIRMED.getStateName();
        }
    };

    private UploadState uploadState;
    private String code;
    private String name;
    private static ActionStateEnum[] TYPES;

    private ActionStateEnum(UploadState uploadState, String code, String name) {
        this.uploadState = uploadState;
        this.code = code;
        this.name = name;
    }

    public UploadState getUploadState() {
        return this.uploadState;
    }

    public String getStateCode() {
        return this.code;
    }

    public String getStateName() {
        return this.name;
    }

    public abstract ActionStateEnum getCurrentState();

    public abstract String getStateName(WorkFlowType var1);

    public static ActionStateEnum fromType(int code) {
        return TYPES[code];
    }

    public static ActionStateEnum formType(String code) {
        ActionStateEnum db = null;
        for (int x = 0; x < TYPES.length; ++x) {
            if (!TYPES[x].getStateCode().equals(code)) continue;
            db = TYPES[x];
        }
        return db;
    }

    static {
        TYPES = new ActionStateEnum[]{ORIGINAL, ORIGINAL_UPLOAD, ORIGINAL_SUBMIT, SUBMITED, RETURNED, UPLOADED, CONFIRMED, REJECTED, PART_START, PART_SUBMITED, PART_UPLOADED, PART_CONFIRMED};
    }
}

