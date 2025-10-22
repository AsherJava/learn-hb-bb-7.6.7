/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.nr.etl.utils.Task;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ArrayOfTask", namespace="http://service.server.etl.jiuqi.com", propOrder={"task"})
public class ArrayOfTask {
    @XmlElement(name="Task", nillable=true)
    protected List<Task> task;

    public List<Task> getTask() {
        if (this.task == null) {
            this.task = new ArrayList<Task>();
        }
        return this.task;
    }
}

