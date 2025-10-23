/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;

public class ProcessStatusTemplate {
    public static final String STATUS_CODE_UNSUBMITED = "unsubmited";
    public static final String STATUS_CODE_SUBMITED = "submited";
    public static final String STATUS_CODE_BACK = "backed";
    public static final String STATUS_CODE_UNREPROTED = "unreported";
    public static final String STATUS_CODE_REPROTED = "reported";
    public static final String STATUS_CODE_REJECTED = "rejected";
    public static final String STATUS_CODE_CONFIRMED = "confirmed";
    public static final String STATUS_CODE_PARTSUBMITED = "part-submited";
    public static final String STATUS_CODE_PARTREPROTED = "part-reported";
    public static final String STATUS_CODE_PARTCONFIRMED = "part-confirmed";
    public static final ProcessStatusTemplate UNSUBMITED = new ProcessStatusTemplate("unsubmited", "\u672a\u9001\u5ba1", IProcessStatus.DataAccessStatus.WRITEABLE, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate SUBMITED = new ProcessStatusTemplate("submited", "\u5df2\u9001\u5ba1", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate BACKED = new ProcessStatusTemplate("backed", "\u5df2\u9000\u5ba1", IProcessStatus.DataAccessStatus.WRITEABLE, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate UNREPORTED = new ProcessStatusTemplate("unreported", "\u672a\u4e0a\u62a5", IProcessStatus.DataAccessStatus.WRITEABLE, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate REPORTED = new ProcessStatusTemplate("reported", "\u5df2\u4e0a\u62a5", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.REPORTED);
    public static final ProcessStatusTemplate REJECTED = new ProcessStatusTemplate("rejected", "\u5df2\u9000\u56de", IProcessStatus.DataAccessStatus.WRITEABLE, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate CONFIRMED = new ProcessStatusTemplate("confirmed", "\u5df2\u786e\u8ba4", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.CONFIRMED);
    public static final String ENTITY_STATUS_ALIAS_TYPE_FORM = "\u5206\u8868";
    public static final String ENTITY_STATUS_ALIAS_TYPE_FORMGROUP = "\u5206\u7ec4";
    public static final ProcessStatusTemplate PARTFORMSUBMITED = new ProcessStatusTemplate("part-submited", "\u5206\u8868\u9001\u5ba1", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate PARTFORMREPORTED = new ProcessStatusTemplate("part-reported", "\u5206\u8868\u4e0a\u62a5", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.REPORTED);
    public static final ProcessStatusTemplate PARTFORMCONFIRMED = new ProcessStatusTemplate("part-confirmed", "\u5206\u8868\u786e\u8ba4", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.CONFIRMED);
    public static final ProcessStatusTemplate PARTGROUPSUBMITED = new ProcessStatusTemplate("part-submited", "\u5206\u7ec4\u9001\u5ba1", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.UNREPORTED);
    public static final ProcessStatusTemplate PARTGROUPREPORTED = new ProcessStatusTemplate("part-reported", "\u5206\u7ec4\u4e0a\u62a5", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.REPORTED);
    public static final ProcessStatusTemplate PARTGROUPCONFIRMED = new ProcessStatusTemplate("part-confirmed", "\u5206\u7ec4\u786e\u8ba4", IProcessStatus.DataAccessStatus.READONLY, IProcessStatus.DataReportStatus.CONFIRMED);
    private final String code;
    private final String title;
    private final IProcessStatus.DataAccessStatus dataAccessStatus;
    private final IProcessStatus.DataReportStatus dataReportStatus;

    public static ProcessStatusTemplate get(String statusCode) {
        if (statusCode == null) {
            throw new IllegalArgumentException("args statusCode must not be null.");
        }
        switch (statusCode) {
            case "unsubmited": {
                return UNSUBMITED;
            }
            case "submited": {
                return SUBMITED;
            }
            case "backed": {
                return BACKED;
            }
            case "unreported": {
                return UNREPORTED;
            }
            case "reported": {
                return REPORTED;
            }
            case "rejected": {
                return REJECTED;
            }
            case "confirmed": {
                return CONFIRMED;
            }
        }
        return null;
    }

    public static ProcessStatusTemplate getEntityStatus(String statusCode, WorkflowObjectType workflowObjectType) {
        ProcessStatusTemplate template = ProcessStatusTemplate.get(statusCode);
        if (template != null) {
            return template;
        }
        if (workflowObjectType == WorkflowObjectType.FORM) {
            switch (statusCode) {
                case "part-submited": {
                    return PARTFORMSUBMITED;
                }
                case "part-reported": {
                    return PARTFORMREPORTED;
                }
                case "part-confirmed": {
                    return PARTFORMCONFIRMED;
                }
            }
            return null;
        }
        if (workflowObjectType == WorkflowObjectType.FORM_GROUP) {
            switch (statusCode) {
                case "part-submited": {
                    return PARTGROUPSUBMITED;
                }
                case "part-reported": {
                    return PARTGROUPREPORTED;
                }
                case "part-confirmed": {
                    return PARTGROUPCONFIRMED;
                }
            }
            return null;
        }
        return null;
    }

    public static ProcessStatusTemplate get(String userTaskCode, String userActionCode) {
        if (userTaskCode == null) {
            throw new IllegalArgumentException("args userTaskCode must not be null.");
        }
        if (userActionCode == null) {
            throw new IllegalArgumentException("args userActionCode must not be null.");
        }
        switch (userTaskCode) {
            case "tsk_start": {
                switch (userActionCode) {
                    case "start": {
                        return null;
                    }
                }
            }
            case "tsk_submit": {
                return SUBMITED;
            }
            case "tsk_upload": {
                return BACKED;
            }
            case "tsk_audit": {
                return UNREPORTED;
            }
            case "tsk_audit_after_confirm": {
                return REPORTED;
            }
        }
        return null;
    }

    private ProcessStatusTemplate(String code, String title, IProcessStatus.DataAccessStatus dataAccessStatus, IProcessStatus.DataReportStatus dataReportStatus) {
        this.code = code;
        this.title = title;
        this.dataAccessStatus = dataAccessStatus;
        this.dataReportStatus = dataReportStatus;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public IProcessStatus.DataAccessStatus getDataAccessStatus() {
        return this.dataAccessStatus;
    }

    public IProcessStatus.DataReportStatus getDataReportStatus() {
        return this.dataReportStatus;
    }
}

