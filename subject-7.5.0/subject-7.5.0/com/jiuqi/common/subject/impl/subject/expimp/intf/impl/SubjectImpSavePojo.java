/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SubjectImpSavePojo
implements Serializable {
    private static final long serialVersionUID = -5215001588806283302L;
    private Integer create = 0;
    private Integer modify = 0;
    private Map<Integer, String> failData;

    public Integer getCreate() {
        return this.create;
    }

    public void setCreate(Integer create) {
        this.create = create;
    }

    public Integer getModify() {
        return this.modify;
    }

    public void setModify(Integer modify) {
        this.modify = modify;
    }

    public Map<Integer, String> getFailData() {
        return this.failData == null ? new HashMap() : this.failData;
    }

    public void putFailData(Integer idx, String message) {
        if (this.failData == null) {
            this.failData = new HashMap<Integer, String>();
        }
        this.failData.put(idx, message);
    }
}

