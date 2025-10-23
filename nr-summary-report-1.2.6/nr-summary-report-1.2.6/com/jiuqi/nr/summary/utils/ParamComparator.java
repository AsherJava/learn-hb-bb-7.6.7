/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ParamComparator<FIRST, SECOND> {
    public static int EXIST = 1;
    public static int NOT_EXIST = 0;
    private List<FIRST> insertList;
    private List<FIRST> modifyList;
    private List<SECOND> deleteList;

    public void processDiff(List<FIRST> designDataList, List<SECOND> deployDataList) {
        HashMap deployDataMap = new HashMap();
        HashMap<String, Integer> deployDataState = new HashMap<String, Integer>();
        if (deployDataList != null) {
            deployDataList.stream().forEach(keyData -> {
                String key = this.getSecondValueKey(keyData);
                deployDataMap.putIfAbsent(key, keyData);
                deployDataState.putIfAbsent(key, NOT_EXIST);
            });
        }
        this.insertList = new ArrayList<FIRST>();
        this.modifyList = new ArrayList<FIRST>();
        this.deleteList = new ArrayList<SECOND>();
        designDataList.stream().forEach(designData -> {
            String key = this.getFirstValueKey(designData);
            if (key == null) {
                this.insertList.add(designData);
            } else {
                deployDataState.put(key, EXIST);
                if (!deployDataMap.keySet().contains(key)) {
                    this.insertList.add(designData);
                } else if (this.getFirstValueTime(designData) > this.getSecondValueTime(deployDataMap.get(key))) {
                    this.modifyList.add(designData);
                }
            }
        });
        deployDataState.forEach((key, state) -> {
            if (state == NOT_EXIST) {
                this.deleteList.add(deployDataMap.get(key));
            }
        });
    }

    protected abstract String getFirstValueKey(FIRST var1);

    protected abstract String getSecondValueKey(SECOND var1);

    protected abstract long getFirstValueTime(FIRST var1);

    protected abstract long getSecondValueTime(SECOND var1);

    public List<FIRST> getInsertList() {
        return this.insertList;
    }

    public List<FIRST> getModifyList() {
        return this.modifyList;
    }

    public List<SECOND> getDeleteList() {
        return this.deleteList;
    }
}

