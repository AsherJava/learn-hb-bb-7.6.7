/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.web.request;

import com.jiuqi.nr.unit.uselector.web.request.USRequestParam;
import java.util.List;

public class FilterSetRemoveParam
extends USRequestParam {
    private List<String> removeList;

    public List<String> getRemoveList() {
        return this.removeList;
    }

    public void setRemoveList(List<String> removeList) {
        this.removeList = removeList;
    }
}

