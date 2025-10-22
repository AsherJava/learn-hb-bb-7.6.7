/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.nr.etl.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"out"})
@XmlRootElement(name="getTaskStateResponse")
public class GetTaskStateResponse {
    protected int out;

    public int getOut() {
        return this.out;
    }

    public void setOut(int value) {
        this.out = value;
    }
}

