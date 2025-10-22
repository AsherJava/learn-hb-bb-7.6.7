/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class DimensionZbKeyDataMap {
    private Map<String, List<String>> assistMap = new LinkedHashMap<String, List<String>>();
    private Map<String, DataFillBaseCell> simpleMap = new HashMap<String, DataFillBaseCell>();

    public Map<String, List<String>> getAssistMap() {
        return this.assistMap;
    }

    public void setAssistMap(Map<String, List<String>> assistMap) {
        this.assistMap = assistMap;
    }

    public Map<String, DataFillBaseCell> getSimpleMap() {
        return this.simpleMap;
    }

    public void setSimpleMap(Map<String, DataFillBaseCell> simpleMap) {
        this.simpleMap = simpleMap;
    }

    private void init(DimensionValueSet dimensionValueSet) {
        if (this.assistMap.isEmpty()) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                this.assistMap.put(dimensionValueSet.getName(i), new ArrayList());
            }
            this.initZb();
        }
    }

    private void initZb() {
        if (!this.assistMap.containsKey("ZB")) {
            this.assistMap.put("ZB", new ArrayList());
        }
    }

    public StringBuilder putHeader(DimensionValueSet dimensionValueSet) {
        this.init(dimensionValueSet);
        StringBuilder hashHeader = new StringBuilder();
        for (String name : this.assistMap.keySet()) {
            if (name.equals("ZB")) continue;
            List<String> dataList = this.assistMap.get(name);
            int index = dataList.indexOf((String)dimensionValueSet.getValue(name));
            if (index == -1) {
                index = dataList.size();
                dataList.add((String)dimensionValueSet.getValue(name));
            }
            hashHeader.append(index).append("_");
        }
        return hashHeader;
    }

    public void putZbKey(StringBuilder hashHeader, String ZbValue, DataFillBaseCell baseCell) {
        List<String> dataList = this.assistMap.get("ZB");
        int index = dataList.indexOf(ZbValue);
        if (index == -1) {
            index = dataList.size();
            dataList.add(ZbValue);
        }
        if (null == baseCell || baseCell.getValue() == null && (baseCell.getReadOnly() == null || !baseCell.getReadOnly().booleanValue()) && !StringUtils.hasLength(baseCell.getMessage())) {
            return;
        }
        this.simpleMap.put(String.valueOf(hashHeader) + index, baseCell);
    }

    public DataFillBaseCell getCell(DimensionValueSet dimensionValueSet) {
        StringBuilder hash = new StringBuilder();
        boolean enableRegex = false;
        for (String name : this.assistMap.keySet()) {
            List<String> dataList = this.assistMap.get(name);
            int index = dataList.indexOf((String)dimensionValueSet.getValue(name));
            if (index == -1) {
                hash.append("\\d+").append("_");
                enableRegex = true;
                continue;
            }
            hash.append(index).append("_");
        }
        if (0 == hash.length()) {
            return null;
        }
        hash.deleteCharAt(hash.length() - 1);
        if (!enableRegex) {
            if (!this.simpleMap.containsKey(hash.toString())) {
                return new DataFillBaseCell();
            }
            return this.simpleMap.get(hash.toString());
        }
        hash.insert(0, "^");
        hash.append("$");
        for (String str : this.simpleMap.keySet()) {
            if (!Pattern.matches(hash.toString(), str)) continue;
            return this.simpleMap.get(str);
        }
        return new DataFillBaseCell();
    }
}

