/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.nr.etl.utils.ArrayOfTask;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"out"})
@XmlRootElement(name="getAllTaskResponse")
public class GetAllTaskResponse {
    @XmlElement(required=true, nillable=true)
    protected ArrayOfTask out;

    public ArrayOfTask getOut() {
        return this.out;
    }

    public void setOut(ArrayOfTask value) {
        this.out = value;
    }
}

