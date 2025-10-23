/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.nr.formulaeditor.vo.FunctionData;
import java.util.List;

public class FunctionList {
    private String group;
    private List<FunctionData> functions;

    public FunctionList(String group, List<FunctionData> functions) {
        this.group = group;
        this.functions = functions;
    }

    public FunctionList(List<FunctionData> functions) {
        this.functions = functions;
        this.group = functions.get(0).getGroup();
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<FunctionData> getFunctions() {
        return this.functions;
    }

    public void setFunctions(List<FunctionData> functions) {
        this.functions = functions;
    }
}

