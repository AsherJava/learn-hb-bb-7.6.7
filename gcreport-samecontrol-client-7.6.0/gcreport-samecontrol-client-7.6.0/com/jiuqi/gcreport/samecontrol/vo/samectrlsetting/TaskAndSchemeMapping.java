/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlsetting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class TaskAndSchemeMapping {
    private String id;
    private String currTaskId;
    private String currSchemeId;
    private String dataType;
    private String lastYearTaskId;
    private String lastYearSchemeId;
    @DateTimeFormat(pattern="yyyy-MM")
    @JsonFormat(pattern="yyyy-MM", timezone="GMT+8")
    private Date fromDate;
    @DateTimeFormat(pattern="yyyy-MM")
    @JsonFormat(pattern="yyyy-MM", timezone="GMT+8")
    private Date toDate;
    private List<SameCtrlChagSubjectMapVO> subjectMappings = new ArrayList<SameCtrlChagSubjectMapVO>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrTaskId() {
        return this.currTaskId;
    }

    public void setCurrTaskId(String currTaskId) {
        this.currTaskId = currTaskId;
    }

    public String getCurrSchemeId() {
        return this.currSchemeId;
    }

    public void setCurrSchemeId(String currSchemeId) {
        this.currSchemeId = currSchemeId;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLastYearTaskId() {
        return this.lastYearTaskId;
    }

    public void setLastYearTaskId(String lastYearTaskId) {
        this.lastYearTaskId = lastYearTaskId;
    }

    public String getLastYearSchemeId() {
        return this.lastYearSchemeId;
    }

    public void setLastYearSchemeId(String lastYearSchemeId) {
        this.lastYearSchemeId = lastYearSchemeId;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<SameCtrlChagSubjectMapVO> getSubjectMappings() {
        return this.subjectMappings;
    }

    public void setSubjectMappings(List<SameCtrlChagSubjectMapVO> subjectMappings) {
        this.subjectMappings = subjectMappings;
    }
}

