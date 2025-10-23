/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.datascheme.web.param;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MoveTablePM {
    @NotNull(message="\u76ee\u6807\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u76ee\u6807\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a") String groupKey;
    @NotEmpty(message="\u6570\u636e\u8868\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotEmpty(message="\u6570\u636e\u8868\u4e0d\u80fd\u4e3a\u7a7a") List<String> tableKeys;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<String> getTableKeys() {
        return this.tableKeys;
    }

    public void setTableKeys(List<String> tableKeys) {
        this.tableKeys = tableKeys;
    }
}

