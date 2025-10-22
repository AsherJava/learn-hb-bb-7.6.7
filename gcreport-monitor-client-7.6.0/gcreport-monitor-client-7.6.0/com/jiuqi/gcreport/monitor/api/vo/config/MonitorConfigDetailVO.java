/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailItemVO;
import java.util.List;

public class MonitorConfigDetailVO {
    private String recid;
    private String monitorId;
    private String nodeCode;
    private Integer checked;
    private Integer cLeafNodeFlag;
    private Integer nodeOrgType;
    private List<String> unitIds;
    private String showType;
    private String reNodeTitle;
    private List<MonitorConfigDetailItemVO> tableData;

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getMonitorId() {
        return this.monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getNodeCode() {
        return this.nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public Integer getChecked() {
        return this.checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getcLeafNodeFlag() {
        return this.cLeafNodeFlag;
    }

    public void setcLeafNodeFlag(Integer cLeafNodeFlag) {
        this.cLeafNodeFlag = cLeafNodeFlag;
    }

    public Integer getNodeOrgType() {
        return this.nodeOrgType;
    }

    public void setNodeOrgType(Integer nodeOrgType) {
        this.nodeOrgType = nodeOrgType;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getReNodeTitle() {
        return this.reNodeTitle;
    }

    public void setReNodeTitle(String reNodeTitle) {
        this.reNodeTitle = reNodeTitle;
    }

    public List<MonitorConfigDetailItemVO> getTableData() {
        return this.tableData;
    }

    public void setTableData(List<MonitorConfigDetailItemVO> tableData) {
        this.tableData = tableData;
    }
}

