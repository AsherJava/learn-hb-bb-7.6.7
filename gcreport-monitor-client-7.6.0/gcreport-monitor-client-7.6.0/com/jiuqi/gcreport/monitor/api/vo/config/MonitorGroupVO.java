/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import com.jiuqi.gcreport.monitor.api.vo.config.MonitorVO;
import java.util.List;

public class MonitorGroupVO
extends MonitorVO {
    private List<MonitorGroupVO> children;
    private String nodeType;

    public List<MonitorGroupVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MonitorGroupVO> children) {
        this.children = children;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}

