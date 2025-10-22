/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DataLinkColumn;
import java.util.ArrayList;
import java.util.List;

public class CalcCellInfo {
    private DataLinkColumn dataLinkColumn;
    private List<String> calcFormulas = new ArrayList<String>();
    private String calcJavaScript;
    private boolean readOnly = true;

    public DataLinkColumn getDataLinkColumn() {
        return this.dataLinkColumn;
    }

    public List<String> getCalcFormulas() {
        return this.calcFormulas;
    }

    public void setDataLinkColumn(DataLinkColumn dataLinkColumn) {
        this.dataLinkColumn = dataLinkColumn;
    }

    public String getCalcJavaScript() {
        return this.calcJavaScript;
    }

    public void setCalcJavaScript(String calcJavaScript) {
        this.calcJavaScript = calcJavaScript;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String toString() {
        if (this.dataLinkColumn != null) {
            return this.dataLinkColumn + ":" + this.calcFormulas;
        }
        return super.toString();
    }
}

