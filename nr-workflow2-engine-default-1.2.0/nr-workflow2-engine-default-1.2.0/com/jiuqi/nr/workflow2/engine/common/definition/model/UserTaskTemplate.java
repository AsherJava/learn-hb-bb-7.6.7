/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

public class UserTaskTemplate {
    public static final String NODE_CODE_START = "tsk_start";
    public static final String NODE_CODE_SUBMIT = "tsk_submit";
    public static final String NODE_CODE_REPORT = "tsk_upload";
    public static final String NODE_CODE_AUDIT = "tsk_audit";
    public static final String NODE_CODE_FINAL = "tsk_audit_after_confirm";
    public static final UserTaskTemplate START = new UserTaskTemplate("tsk_start", "\u5f00\u59cb");
    public static final UserTaskTemplate SUBMIT = new UserTaskTemplate("tsk_submit", "\u9001\u5ba1");
    public static final UserTaskTemplate REPORT = new UserTaskTemplate("tsk_upload", "\u4e0a\u62a5");
    public static final UserTaskTemplate AUDIT = new UserTaskTemplate("tsk_audit", "\u5ba1\u6279");
    public static final UserTaskTemplate FINAL = new UserTaskTemplate("tsk_audit_after_confirm", "\u5ba1\u6279");
    private final String code;
    private final String title;

    public static UserTaskTemplate get(String userTaskCode) {
        if (userTaskCode == null) {
            throw new IllegalArgumentException("args userTaskCode must not be null.");
        }
        switch (userTaskCode) {
            case "tsk_start": {
                return START;
            }
            case "tsk_submit": {
                return SUBMIT;
            }
            case "tsk_upload": {
                return REPORT;
            }
            case "tsk_audit": {
                return AUDIT;
            }
            case "tsk_audit_after_confirm": {
                return FINAL;
            }
        }
        return null;
    }

    public static UserTaskTemplate tryGet(String userTaskCode) {
        if (userTaskCode == null) {
            return null;
        }
        switch (userTaskCode) {
            case "tsk_start": {
                return START;
            }
            case "tsk_submit": {
                return SUBMIT;
            }
            case "tsk_upload": {
                return REPORT;
            }
            case "tsk_audit": {
                return AUDIT;
            }
            case "tsk_audit_after_confirm": {
                return FINAL;
            }
        }
        return null;
    }

    private UserTaskTemplate(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

