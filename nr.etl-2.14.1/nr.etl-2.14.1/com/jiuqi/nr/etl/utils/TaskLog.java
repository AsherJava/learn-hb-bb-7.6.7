/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBElement
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlElementRef
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.nr.etl.utils.ArrayOfProcessLog;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TaskLog", namespace="http://service.server.etl.jiuqi.com", propOrder={"controlFlowGuid", "dataFlowName", "finishTime", "guid", "list", "result", "startTime", "taskGuid", "taskName"})
public class TaskLog {
    @XmlElementRef(name="controlFlowGuid", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> controlFlowGuid;
    @XmlElementRef(name="dataFlowName", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> dataFlowName;
    protected Long finishTime;
    @XmlElementRef(name="guid", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> guid;
    @XmlElementRef(name="list", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<ArrayOfProcessLog> list;
    protected Integer result;
    protected Long startTime;
    @XmlElementRef(name="taskGuid", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> taskGuid;
    @XmlElementRef(name="taskName", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> taskName;

    public JAXBElement<String> getControlFlowGuid() {
        return this.controlFlowGuid;
    }

    public void setControlFlowGuid(JAXBElement<String> value) {
        this.controlFlowGuid = value;
    }

    public JAXBElement<String> getDataFlowName() {
        return this.dataFlowName;
    }

    public void setDataFlowName(JAXBElement<String> value) {
        this.dataFlowName = value;
    }

    public Long getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Long value) {
        this.finishTime = value;
    }

    public JAXBElement<String> getGuid() {
        return this.guid;
    }

    public void setGuid(JAXBElement<String> value) {
        this.guid = value;
    }

    public JAXBElement<ArrayOfProcessLog> getList() {
        return this.list;
    }

    public void setList(JAXBElement<ArrayOfProcessLog> value) {
        this.list = value;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer value) {
        this.result = value;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long value) {
        this.startTime = value;
    }

    public JAXBElement<String> getTaskGuid() {
        return this.taskGuid;
    }

    public void setTaskGuid(JAXBElement<String> value) {
        this.taskGuid = value;
    }

    public JAXBElement<String> getTaskName() {
        return this.taskName;
    }

    public void setTaskName(JAXBElement<String> value) {
        this.taskName = value;
    }
}

