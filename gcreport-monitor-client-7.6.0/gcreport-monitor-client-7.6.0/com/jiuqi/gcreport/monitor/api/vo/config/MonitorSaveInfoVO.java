/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorNrSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorSceneNodeVO;
import java.util.List;
import java.util.Map;

public class MonitorSaveInfoVO {
    private String recid;
    private String groupId;
    private String code;
    private String title;
    private String label;
    private int showNode;
    private List<MonitorNrSchemeVO> monitorNrData;
    private Map<String, MonitorConfigDetailVO> nodeDataMap;
    private List<MonitorSceneNodeVO> allNodes;
    private List<String> nodeList;

    public String getLabel() {
        if (this.label == null) {
            this.label = this.title;
        }
        return this.label;
    }

    public List<String> getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(List<String> nodeList) {
        this.nodeList = nodeList;
    }

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer isShowNode() {
        return this.showNode;
    }

    public Integer getShowNode() {
        return this.showNode;
    }

    public void setShowNode(Integer showNode) {
        this.showNode = showNode;
    }

    public List<MonitorNrSchemeVO> getMonitorNrData() {
        return this.monitorNrData;
    }

    public void setMonitorNrData(List<MonitorNrSchemeVO> monitorNrData) {
        this.monitorNrData = monitorNrData;
    }

    public Map<String, MonitorConfigDetailVO> getNodeDataMap() {
        return this.nodeDataMap;
    }

    public void setNodeDataMap(Map<String, MonitorConfigDetailVO> nodeDataMap) {
        this.nodeDataMap = nodeDataMap;
    }

    public List<MonitorSceneNodeVO> getAllNodes() {
        return this.allNodes;
    }

    public void setAllNodes(List<MonitorSceneNodeVO> allNodes) {
        this.allNodes = allNodes;
    }

    public String toString() {
        return "MonitorSaveInfoVO{recid=" + this.recid + ", groupId=" + this.groupId + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", label='" + this.label + '\'' + ", showNode=" + this.showNode + ", monitorNrData=" + this.monitorNrData + ", nodeDataMap=" + this.nodeDataMap + ", allNodes=" + this.allNodes + ", nodeList=" + this.nodeList + '}';
    }
}

