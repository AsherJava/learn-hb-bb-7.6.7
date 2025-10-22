/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import java.util.ArrayList;
import java.util.List;

public class FormulaCheckDesBatchSaveInfo {
    private FormulaCheckDesQueryInfo queryInfo;
    private List<FormulaCheckDesInfo> desInfos = new ArrayList<FormulaCheckDesInfo>();

    public FormulaCheckDesQueryInfo getQueryInfo() {
        return this.queryInfo;
    }

    public void setQueryInfo(FormulaCheckDesQueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public List<FormulaCheckDesInfo> getDesInfos() {
        return this.desInfos;
    }

    public void setDesInfos(List<FormulaCheckDesInfo> desInfos) {
        this.desInfos = desInfos;
    }
}

