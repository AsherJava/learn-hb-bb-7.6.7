/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import java.util.List;

public class SendTodoJobParameter {
    private String taskKey;
    private String formschemeKey;
    private List<InstanceInfo> instanceInfos;
    private String remark;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormschemeKey() {
        return this.formschemeKey;
    }

    public void setFormschemeKey(String formschemeKey) {
        this.formschemeKey = formschemeKey;
    }

    public List<InstanceInfo> getInstanceInfos() {
        return this.instanceInfos;
    }

    public void setInstanceInfos(List<InstanceInfo> instanceInfos) {
        this.instanceInfos = instanceInfos;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class InstanceInfo {
        private String id;
        private IBusinessObject businessObject;
        private String processDefinitionId;
        private String curTaskId;
        private String curNode;
        private String oringnalTaskId;
        private String oringnalUserTask;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOringnalTaskId() {
            return this.oringnalTaskId;
        }

        public void setOringnalTaskId(String oringnalTaskId) {
            this.oringnalTaskId = oringnalTaskId;
        }

        public IBusinessObject getBusinessObject() {
            return this.businessObject;
        }

        public void setBusinessObject(IBusinessObject businessObject) {
            this.businessObject = businessObject;
        }

        public String getCurNode() {
            return this.curNode;
        }

        public void setCurNode(String curNode) {
            this.curNode = curNode;
        }

        public String getCurTaskId() {
            return this.curTaskId;
        }

        public void setCurTaskId(String curTaskId) {
            this.curTaskId = curTaskId;
        }

        public String getProcessDefinitionId() {
            return this.processDefinitionId;
        }

        public void setProcessDefinitionId(String processDefinitionId) {
            this.processDefinitionId = processDefinitionId;
        }

        public String getOringnalUserTask() {
            return this.oringnalUserTask;
        }

        public void setOringnalUserTask(String oringnalUserTask) {
            this.oringnalUserTask = oringnalUserTask;
        }
    }
}

