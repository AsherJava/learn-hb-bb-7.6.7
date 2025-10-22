/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service.dto;

import com.jiuqi.nr.data.common.service.Result;
import java.util.List;

public class ResultDTO
implements Result {
    private String message;
    private List<String> failUnits;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public List<String> getFailUnits() {
        return this.failUnits;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFailUnits(List<String> failUnits) {
        this.failUnits = failUnits;
    }
}

