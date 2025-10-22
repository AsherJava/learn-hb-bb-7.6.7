/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.text.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangMappingParam {
    private List<Object[]> allDataValues = new ArrayList<Object[]>();
    private List<String> dwCodes = new ArrayList<String>();
    private List<String> periodCodes = new ArrayList<String>();
    private Map<Integer, List<String>> fieldIndex2Data = new HashMap<Integer, List<String>>();
    private Map<Integer, Map<String, String>> fileIndex2GroupKey = new HashMap<Integer, Map<String, String>>();

    public List<Object[]> getAllDataValues() {
        return this.allDataValues;
    }

    public List<String> getDwCodes() {
        return this.dwCodes;
    }

    public List<String> getPeriodCodes() {
        return this.periodCodes;
    }

    public Map<Integer, List<String>> getFieldIndex2Data() {
        return this.fieldIndex2Data;
    }

    public Map<Integer, Map<String, String>> getFileIndex2GroupKey() {
        return this.fileIndex2GroupKey;
    }

    public void clearMappingParams() {
        this.allDataValues.clear();
        this.dwCodes.clear();
        this.periodCodes.clear();
        this.fieldIndex2Data.clear();
        this.fileIndex2GroupKey.clear();
    }
}

