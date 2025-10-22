/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult;
import java.util.List;

public class ShowResult {
    private String workflowId;
    private List<ShowNodeResult> nodeList;
    private List<String> lineList;
    private List<WorkFlowLine> worklineList;
    private boolean confrimed = false;
    private String title;
    private boolean defaultWorkflow;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public List<ShowNodeResult> getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(List<ShowNodeResult> nodeList) {
        this.nodeList = nodeList;
    }

    public List<String> getLineList() {
        return this.lineList;
    }

    public void setLineList(List<String> lineList) {
        this.lineList = lineList;
    }

    public List<WorkFlowLine> getWorklineList() {
        return this.worklineList;
    }

    public void setWorklineList(List<WorkFlowLine> worklineList) {
        this.worklineList = worklineList;
    }

    public boolean isConfrimed() {
        return this.confrimed;
    }

    public void setConfrimed(boolean confrimed) {
        this.confrimed = confrimed;
    }

    public boolean isDefaultWorkflow() {
        return this.defaultWorkflow;
    }

    public void setDefaultWorkflow(boolean defaultWorkflow) {
        this.defaultWorkflow = defaultWorkflow;
    }
}

