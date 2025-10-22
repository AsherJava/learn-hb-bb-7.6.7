/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.vo.OptionVO
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.task;

import com.jiuqi.gcreport.common.task.vo.OptionVO;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;

public class TaskInfoVO {
    private OptionVO task;
    private TaskDefine taskDefine;
    private String taskTitle;
    private Integer dflYear;
    private Integer dflPeriod;
    private List<OptionVO> acctYear;
    private List<OptionVO> acctPeriod;
    private String unitDefine;
    private String unitTitle;
    private String currencyDefine;
    private String gcorgtypeDefine;
    private List defines = new ArrayList();
    private Integer periodType;
    private String periodTypeChar;
    private String fromPeriod;
    private String toPeriod;
    private Integer enableMultiOrg = 0;

    public Integer getEnableMultiOrg() {
        return this.enableMultiOrg;
    }

    public void setEnableMultiOrg(Integer enableMultiOrg) {
        this.enableMultiOrg = enableMultiOrg;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public OptionVO getTask() {
        return this.task;
    }

    public void setTask(OptionVO task) {
        this.task = task;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getUnitDefine() {
        return this.unitDefine;
    }

    public void setUnitDefine(String unitDefine) {
        this.unitDefine = unitDefine;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getCurrencyDefine() {
        return this.currencyDefine;
    }

    public void setCurrencyDefine(String currencyDefine) {
        this.currencyDefine = currencyDefine;
    }

    public String getGcorgtypeDefine() {
        return this.gcorgtypeDefine;
    }

    public void setGcorgtypeDefine(String gcorgtypeDefine) {
        this.gcorgtypeDefine = gcorgtypeDefine;
    }

    public List getDefines() {
        return this.defines;
    }

    public void setDefines(List defines) {
        this.defines = defines;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodTypeChar() {
        return this.periodTypeChar;
    }

    public void setPeriodTypeChar(String periodTypeChar) {
        this.periodTypeChar = periodTypeChar;
    }

    public Integer getDflYear() {
        return this.dflYear;
    }

    public void setDflYear(Integer dflYear) {
        this.dflYear = dflYear;
    }

    public Integer getDflPeriod() {
        return this.dflPeriod;
    }

    public void setDflPeriod(Integer dflPeriod) {
        this.dflPeriod = dflPeriod;
    }

    public List<OptionVO> getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(List<OptionVO> acctYear) {
        this.acctYear = acctYear;
    }

    public List<OptionVO> getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(List<OptionVO> acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}

