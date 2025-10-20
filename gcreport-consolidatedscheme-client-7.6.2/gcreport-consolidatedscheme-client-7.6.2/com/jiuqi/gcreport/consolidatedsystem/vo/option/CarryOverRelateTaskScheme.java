/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;

public class CarryOverRelateTaskScheme {
    private String taskId;
    private String schemeId;
    private String historyTaskId;
    private String historySchemeId;
    private String fromDate;
    private String toDate;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getHistoryTaskId() {
        return this.historyTaskId;
    }

    public void setHistoryTaskId(String historyTaskId) {
        this.historyTaskId = historyTaskId;
    }

    public String getHistorySchemeId() {
        return this.historySchemeId;
    }

    public void setHistorySchemeId(String historySchemeId) {
        this.historySchemeId = historySchemeId;
    }

    public String getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return this.toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTaskTitle() {
        return this.getTaskTitle(this.taskId);
    }

    public String getSchemeTitle() {
        return this.getSchemeTitle(this.schemeId);
    }

    public String getHistoryTaskTitle() {
        return this.getTaskTitle(this.historyTaskId);
    }

    public String getHistorySchemeTitle() {
        return this.getSchemeTitle(this.historySchemeId);
    }

    private String getSchemeTitle(String schemeId) {
        if (null == schemeId) {
            return "";
        }
        IRuntimeFormSchemeService formSchemeService = (IRuntimeFormSchemeService)SpringContextUtils.getBean(IRuntimeFormSchemeService.class);
        FormSchemeDefine formScheme = formSchemeService.getFormScheme(schemeId);
        if (null == formScheme) {
            return "";
        }
        return formScheme.getTitle();
    }

    private String getTaskTitle(String taskId) {
        if (null == taskId) {
            return "";
        }
        IRuntimeTaskService taskService = (IRuntimeTaskService)SpringContextUtils.getBean(IRuntimeTaskService.class);
        TaskDefine taskDefine = taskService.queryTaskDefine(taskId);
        if (null == taskDefine) {
            return "";
        }
        return taskDefine.getTitle();
    }
}

