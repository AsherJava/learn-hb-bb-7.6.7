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

import com.jiuqi.nr.etl.utils.ProcessLog;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ArrayOfProcessLog", namespace="http://service.server.etl.jiuqi.com", propOrder={"processLog"})
public class ArrayOfProcessLog {
    @XmlElement(name="ProcessLog", nillable=true)
    protected List<ProcessLog> processLog;

    public List<ProcessLog> getProcessLog() {
        if (this.processLog == null) {
            this.processLog = new ArrayList<ProcessLog>();
        }
        return this.processLog;
    }
}

