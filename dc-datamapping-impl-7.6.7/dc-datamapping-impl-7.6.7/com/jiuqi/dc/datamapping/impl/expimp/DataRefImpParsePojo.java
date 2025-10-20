/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.datamapping.impl.expimp;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataRefImpParsePojo
implements Serializable {
    private static final long serialVersionUID = -5215001588806283302L;
    private Integer success = 0;
    private Map<Integer, String> failData;
    private Map<Integer, String> skipData;

    public Integer getSuccess() {
        return this.success;
    }

    public void success() {
        DataRefImpParsePojo dataRefImpParsePojo = this;
        Integer n = dataRefImpParsePojo.success;
        Integer n2 = dataRefImpParsePojo.success = Integer.valueOf(dataRefImpParsePojo.success + 1);
    }

    public void fail() {
        DataRefImpParsePojo dataRefImpParsePojo = this;
        Integer n = dataRefImpParsePojo.success;
        Integer n2 = dataRefImpParsePojo.success = Integer.valueOf(dataRefImpParsePojo.success - 1);
    }

    public void putSkipData(Integer idx, String message) {
        if (this.skipData == null) {
            this.skipData = new HashMap<Integer, String>();
        }
        this.skipData.put(idx, message);
    }

    public Map<Integer, String> getSkipData() {
        return this.skipData == null ? new HashMap() : this.skipData;
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

