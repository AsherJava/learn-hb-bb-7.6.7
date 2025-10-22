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

import com.jiuqi.nr.etl.utils.AnyType2AnyTypeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"in0", "in1", "in2", "in3"})
@XmlRootElement(name="execute1")
public class Execute1 {
    @XmlElement(required=true, nillable=true)
    protected String in0;
    @XmlElement(required=true, nillable=true)
    protected AnyType2AnyTypeMap in1;
    @XmlElement(required=true, nillable=true)
    protected String in2;
    @XmlElement(required=true, nillable=true)
    protected String in3;

    public String getIn0() {
        return this.in0;
    }

    public void setIn0(String value) {
        this.in0 = value;
    }

    public AnyType2AnyTypeMap getIn1() {
        return this.in1;
    }

    public void setIn1(AnyType2AnyTypeMap value) {
        this.in1 = value;
    }

    public String getIn2() {
        return this.in2;
    }

    public void setIn2(String value) {
        this.in2 = value;
    }

    public String getIn3() {
        return this.in3;
    }

    public void setIn3(String value) {
        this.in3 = value;
    }
}

