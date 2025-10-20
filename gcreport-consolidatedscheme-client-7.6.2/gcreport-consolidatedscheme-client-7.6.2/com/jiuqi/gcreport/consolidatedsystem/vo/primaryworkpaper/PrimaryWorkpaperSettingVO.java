/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper;

import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.FormSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import java.util.List;

public class PrimaryWorkpaperSettingVO {
    private String id;
    private String taskId;
    private String schemeId;
    private String reportSystem;
    private String primaryTypeId;
    private String boundZbTitle;
    private FormSubjectVO formSubject;
    private String boundZbName;
    private String boundZbId;
    private Integer orient;
    private String subjectCode;
    private List<ConsolidatedSubjectVO> boundSubjects;
    private Integer edit;
    private Double ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public String getPrimaryTypeId() {
        return this.primaryTypeId;
    }

    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }

    public String getBoundZbTitle() {
        return this.boundZbTitle;
    }

    public void setBoundZbTitle(String boundZbTitle) {
        this.boundZbTitle = boundZbTitle;
    }

    public FormSubjectVO getFormSubject() {
        return this.formSubject;
    }

    public void setFormSubject(FormSubjectVO formSubject) {
        this.formSubject = formSubject;
    }

    public String getBoundZbName() {
        return this.boundZbName;
    }

    public void setBoundZbName(String boundZbName) {
        this.boundZbName = boundZbName;
    }

    public String getBoundZbId() {
        return this.boundZbId;
    }

    public void setBoundZbId(String boundZbId) {
        this.boundZbId = boundZbId;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<ConsolidatedSubjectVO> getBoundSubjects() {
        return this.boundSubjects;
    }

    public void setBoundSubjects(List<ConsolidatedSubjectVO> boundSubjects) {
        this.boundSubjects = boundSubjects;
    }

    public Integer getEdit() {
        return this.edit;
    }

    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }
}

