/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.checker;

import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IFilterCheckValuesImpl
implements IFilterCheckValues {
    private Map<String, String> runtimePara = new HashMap<String, String>();
    private List<Map<String, String>> values = new ArrayList<Map<String, String>>();

    @Override
    public Map<String, String> getRuntimePara() {
        return this.runtimePara;
    }

    @Override
    public List<Map<String, String>> getValues() {
        return this.values;
    }

    public void setRuntimePara(Map<String, String> runtimePara) {
        this.runtimePara = runtimePara;
    }

    public void setValues(List<Map<String, String>> values) {
        this.values = values;
    }
}

