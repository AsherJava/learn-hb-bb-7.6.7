/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class DataFileCheckInfo
implements Serializable {
    private Set<String> type = new HashSet<String>();
    private Set<String> length = new HashSet<String>();
    private Set<String> precision = new HashSet<String>();
    private Set<String> nullAble = new HashSet<String>();
    private Set<String> customCode = new HashSet<String>();

    public Set<String> getType() {
        return this.type;
    }

    public void setType(Set<String> type) {
        this.type = type;
    }

    public Set<String> getLength() {
        return this.length;
    }

    public Set<String> getPrecision() {
        return this.precision;
    }

    public Set<String> getNullAble() {
        return this.nullAble;
    }

    public Set<String> getCustomCode() {
        return this.customCode;
    }

    public void setLength(Set<String> length) {
        this.length = length;
    }

    public void setPrecision(Set<String> precision) {
        this.precision = precision;
    }

    public void setNullAble(Set<String> nullAble) {
        this.nullAble = nullAble;
    }

    public void setCustomCode(Set<String> customCode) {
        this.customCode = customCode;
    }

    @JsonIgnore
    public boolean isCheckSuccess() {
        return this.type.size() + this.length.size() + this.precision.size() + this.nullAble.size() + this.customCode.size() == 0;
    }
}

