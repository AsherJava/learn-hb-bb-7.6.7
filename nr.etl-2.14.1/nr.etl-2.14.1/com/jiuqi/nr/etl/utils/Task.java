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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Task", namespace="http://service.server.etl.jiuqi.com", propOrder={"taskDescription", "taskGuid", "taskName"})
public class Task {
    @XmlElementRef(name="taskDescription", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> taskDescription;
    @XmlElementRef(name="taskGuid", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> taskGuid;
    @XmlElementRef(name="taskName", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> taskName;

    public JAXBElement<String> getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDescription(JAXBElement<String> value) {
        this.taskDescription = value;
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

