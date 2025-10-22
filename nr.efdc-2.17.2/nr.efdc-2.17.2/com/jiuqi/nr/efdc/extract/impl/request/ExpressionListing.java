/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.efdc.extract.impl.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.efdc.extract.impl.request.FixExpression;
import com.jiuqi.nr.efdc.extract.impl.request.FloatExpression;
import java.util.List;

public class ExpressionListing {
    private List<FixExpression> fixExpressions;
    private List<FloatExpression> floatExpressions;
    private boolean isFormatNeeded;

    public List<FixExpression> getFixExpressions() {
        return this.fixExpressions;
    }

    public void setFixExpressions(List<FixExpression> fixExpressions) {
        this.fixExpressions = fixExpressions;
    }

    public List<FloatExpression> getFloatExpressions() {
        return this.floatExpressions;
    }

    public void setFloatExpressions(List<FloatExpression> floatExpressions) {
        this.floatExpressions = floatExpressions;
    }

    @JsonProperty(value="isFormatNeeded")
    public boolean isFormatNeeded() {
        return this.isFormatNeeded;
    }

    public void setFormatNeeded(boolean isFormatNeeded) {
        this.isFormatNeeded = isFormatNeeded;
    }
}

