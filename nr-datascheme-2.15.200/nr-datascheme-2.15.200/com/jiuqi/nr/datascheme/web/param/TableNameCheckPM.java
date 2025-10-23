/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;
import java.util.List;

public class TableNameCheckPM {
    private String groupKey;
    private String dataSchemeKey;
    private List<BaseDataVO> tables;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<BaseDataVO> getTables() {
        return this.tables;
    }

    public void setTables(List<BaseDataVO> tables) {
        this.tables = tables;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

