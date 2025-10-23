/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction$ReportDirection
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;

public class UserActionTemplate {
    public static final String ACTION_CODE_START = "start";
    public static final String ACTION_CODE_SUBMIT = "act_submit";
    private static final String ACTION_ICON_SUBMIT = "#icon-_GJHsongshen";
    public static final String ACTION_CODE_BACK = "act_return";
    private static final String ACTION_ICON_BACK = "#icon-_GJHtuishen";
    public static final String ACTION_CODE_REPORT = "act_upload";
    private static final String ACTION_ICON_REPORT = "#icon-_GJHshangbao";
    public static final String ACTION_CODE_REJECT = "act_reject";
    private static final String ACTION_ICON_REJECT = "#icon-_GJZtuihui";
    public static final String ACTION_CODE_CONFIRM = "act_confirm";
    private static final String ACTION_ICON_CONFIRM = "#icon-_GJHqueren";
    public static final String ACTION_CODE_CANCEL_CONFIRM = "act_cancel_confirm";
    private static final String ACTION_ICON_CANCEL_CONFIRM = "#icon-_GJHtuishen";
    public static final String ACTION_CODE_RETRIEVE = "act_retrieve";
    private static final String ACTION_ICON_RETRIEVE = "#icon-_GJHtuishen";
    public static final String ACTION_CODE_APPLY_FOR_REJECT = "act_apply_reject";
    private static final String ACTION_ICON_APPLY_FOR_REJECT = "#icon-_GJZtuihui";
    public static final UserActionTemplate START = new UserActionTemplate("start", "\u542f\u52a8", IUserAction.ReportDirection.OTHER);
    public static final UserActionTemplate SUBMIT = new UserActionTemplate("act_submit", "\u9001\u5ba1", IUserAction.ReportDirection.OTHER, "#icon-_GJHsongshen");
    public static final UserActionTemplate BACK = new UserActionTemplate("act_return", "\u9000\u5ba1", IUserAction.ReportDirection.OTHER, "#icon-_GJHtuishen");
    public static final UserActionTemplate REPORT = new UserActionTemplate("act_upload", "\u4e0a\u62a5", IUserAction.ReportDirection.REPORT_UPWARD, "#icon-_GJHshangbao");
    public static final UserActionTemplate REJECT = new UserActionTemplate("act_reject", "\u9000\u56de", IUserAction.ReportDirection.REJECT_DOWNWARD, "#icon-_GJZtuihui");
    public static final UserActionTemplate CONFIRM = new UserActionTemplate("act_confirm", "\u786e\u8ba4", IUserAction.ReportDirection.OTHER, "#icon-_GJHqueren");
    public static final UserActionTemplate CANCEL_CONFIRM = new UserActionTemplate("act_cancel_confirm", "\u53d6\u6d88\u786e\u8ba4", IUserAction.ReportDirection.OTHER, "#icon-_GJHtuishen");
    public static final UserActionTemplate RETRIEVE = new UserActionTemplate("act_retrieve", "\u53d6\u56de", IUserAction.ReportDirection.OTHER, "#icon-_GJHtuishen");
    public static final UserActionTemplate APPLY_FOR_REJECT = new UserActionTemplate("act_apply_reject", "\u7533\u8bf7\u9000\u56de", IUserAction.ReportDirection.OTHER, "#icon-_GJZtuihui");
    private final String code;
    private final String title;
    private final IUserAction.ReportDirection reportDirection;
    private final String icon;

    public static UserActionTemplate get(String userActionCode) {
        if (userActionCode == null) {
            throw new IllegalArgumentException("args userActionCode must not be null.");
        }
        switch (userActionCode) {
            case "start": {
                return START;
            }
            case "act_submit": {
                return SUBMIT;
            }
            case "act_return": {
                return BACK;
            }
            case "act_upload": {
                return REPORT;
            }
            case "act_reject": {
                return REJECT;
            }
            case "act_confirm": {
                return CONFIRM;
            }
            case "act_cancel_confirm": {
                return CANCEL_CONFIRM;
            }
            case "act_retrieve": {
                return RETRIEVE;
            }
            case "act_apply_reject": {
                return APPLY_FOR_REJECT;
            }
        }
        return null;
    }

    private UserActionTemplate(String code, String title, IUserAction.ReportDirection reportDirection) {
        this.code = code;
        this.title = title;
        this.reportDirection = reportDirection;
        this.icon = null;
    }

    private UserActionTemplate(String code, String title, IUserAction.ReportDirection reportDirection, String icon) {
        this.code = code;
        this.title = title;
        this.reportDirection = reportDirection;
        this.icon = icon;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public IUserAction.ReportDirection getReportDirection() {
        return this.reportDirection;
    }

    public String getIcon() {
        return this.icon;
    }
}

