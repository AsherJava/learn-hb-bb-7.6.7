/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.refactor.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DimsObject {
    private List<String> names = new ArrayList<String>();
    private List<Object> values = new ArrayList<Object>();

    public List<Object> getValues() {
        return this.values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + Objects.hashCode(this.names);
        result = 31 * result + Objects.hashCode(this.values);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        DimsObject other = (DimsObject)obj;
        return Objects.equals(this.names, other.names) && Objects.equals(this.values, other.values);
    }
}

