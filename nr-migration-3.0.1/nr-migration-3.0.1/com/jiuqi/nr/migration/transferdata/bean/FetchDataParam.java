/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.FetchSaveDataParam;
import java.util.List;

public class FetchDataParam
extends FetchSaveDataParam {
    private List<String> dataFieldKeys;

    public List<String> getDataFieldKeys() {
        return this.dataFieldKeys;
    }

    public void setDataFieldKeys(List<String> dataFieldKeys) {
        this.dataFieldKeys = dataFieldKeys;
    }
}

