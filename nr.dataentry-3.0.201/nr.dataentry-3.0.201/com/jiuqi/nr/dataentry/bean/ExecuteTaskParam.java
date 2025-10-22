/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.nr.jtable.uniformity.service.JUniformityService
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ExecuteTaskParam
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String taskId;
    private String taskCode;
    private String actionCode;
    private String actionTitle;
    private String comment;
    private boolean forceCommit;
    private boolean sendEmail;
    private String returnType;
    private List<UUID> formKeys;
    private List<String> rejectFormKeys;
    private DUserActionParam userActionParam;
    public String contextTaskKey;
    public String contextEntityId;
    public String contextFormSchemeKey;
    public String contextFilterExpression;
    private List<String> signBootMode;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSendEmail() {
        return this.sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<UUID> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<UUID> formKeys) {
        this.formKeys = formKeys;
    }

    public DUserActionParam getUserActionParam() {
        return this.userActionParam;
    }

    public void setUserActionParam(DUserActionParam userActionParam) {
        this.userActionParam = userActionParam;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public boolean isForceCommit() {
        return this.forceCommit;
    }

    public void setForceCommit(boolean forceCommit) {
        this.forceCommit = forceCommit;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public List<String> getRejectFormKeys() {
        return this.rejectFormKeys;
    }

    public void setRejectFormKeys(List<String> rejectFormKeys) {
        this.rejectFormKeys = rejectFormKeys;
    }

    public String getActionTitle() {
        return this.actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setTitle(this.actionTitle);
        logParam.setKeyInfo("\u6d41\u7a0b\u64cd\u4f5c");
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        HashMap<String, List<String>> orherMsg = new HashMap<String, List<String>>();
        if (this.rejectFormKeys != null) {
            orherMsg.put("forms", this.rejectFormKeys);
            logParam.setOrherMsg(orherMsg);
        }
        return logParam;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public List<String> getSignBootMode() {
        return this.signBootMode;
    }

    public void setSignBootMode(List<String> signBootMode) {
        this.signBootMode = signBootMode;
    }
}

