/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.web.request;

import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.web.request.USRequestParam;

public class FilteringRequestParam
extends USRequestParam {
    private String keyword;
    private IFilterCheckValuesImpl checkValues;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public IFilterCheckValuesImpl getCheckValues() {
        return this.checkValues;
    }

    public void setCheckValues(IFilterCheckValuesImpl checkValues) {
        this.checkValues = checkValues;
    }
}

