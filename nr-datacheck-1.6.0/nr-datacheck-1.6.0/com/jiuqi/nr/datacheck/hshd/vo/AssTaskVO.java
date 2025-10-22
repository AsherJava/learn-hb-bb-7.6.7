/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.hshd.vo;

import com.jiuqi.nr.datacheck.hshd.vo.AssFormSchemeVO;
import com.jiuqi.nr.datacheck.hshd.vo.TaskOrgLinkVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssTaskVO {
    private String assTask;
    private String assTaskTitle;
    private Map<String, AssFormSchemeVO> formSchemes;
    private List<TaskOrgLinkVO> taskOrgLinks;

    public String getAssTask() {
        return this.assTask;
    }

    public void setAssTask(String assTask) {
        this.assTask = assTask;
    }

    public String getAssTaskTitle() {
        return this.assTaskTitle;
    }

    public void setAssTaskTitle(String assTaskTitle) {
        this.assTaskTitle = assTaskTitle;
    }

    public Map<String, AssFormSchemeVO> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(Map<String, AssFormSchemeVO> formSchemes) {
        this.formSchemes = formSchemes;
    }

    public List<TaskOrgLinkVO> getTaskOrgLinks() {
        return this.taskOrgLinks;
    }

    public void setTaskOrgLinks(List<TaskOrgLinkVO> taskOrgLinks) {
        this.taskOrgLinks = taskOrgLinks;
    }

    public void addTaskOrgLink(TaskOrgLinkVO taskOrgLink) {
        if (this.taskOrgLinks == null) {
            this.taskOrgLinks = new ArrayList<TaskOrgLinkVO>();
        }
        this.taskOrgLinks.add(taskOrgLink);
    }
}

