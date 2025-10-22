/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.dafafill.model.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataFillBaseCell
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Serializable value;
    private Boolean readOnly;
    private String message;

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public Serializable getValue() {
        return this.value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    public String toString() {
        return null != this.value ? this.value.toString() : "";
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

