/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.task.vo;

import com.jiuqi.gcreport.common.task.vo.TaskOrgDataVO;
import java.util.List;

public class TaskOptionVO {
    private String value;
    private String label;
    private String dataScheme;
    private String fromPeriod;
    private String toPeriod;
    private Integer enableMultiOrg = 0;
    private List<TaskOrgDataVO> entityScopeList;

    public TaskOptionVO(String value, String label, String dataScheme, String fromPeriod, String toPeriod) {
        this.value = value;
        this.label = label;
        this.dataScheme = dataScheme;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
    }

    public TaskOptionVO() {
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
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

    public List<TaskOrgDataVO> getEntityScopeList() {
        return this.entityScopeList;
    }

    public void setEntityScopeList(List<TaskOrgDataVO> entityScopeList) {
        this.entityScopeList = entityScopeList;
    }

    public Integer getEnableMultiOrg() {
        return this.enableMultiOrg;
    }

    public void setEnableMultiOrg(Integer enableMultiOrg) {
        this.enableMultiOrg = enableMultiOrg;
    }
}

