/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import java.util.ArrayList;
import java.util.List;

public class DSDimensionInfo {
    List<String> dimList = new ArrayList<String>();
    List<String> dimNames = new ArrayList<String>();
    List<List<String>> dimDatass = new ArrayList<List<String>>();

    public List<String> getDimList() {
        return this.dimList;
    }

    public void setDimList(List<String> dimList) {
        this.dimList = dimList;
    }

    public List<String> getDimNames() {
        return this.dimNames;
    }

    public void setDimNames(List<String> dimNames) {
        this.dimNames = dimNames;
    }

    public List<List<String>> getDimDatass() {
        return this.dimDatass;
    }

    public void setDimDatass(List<List<String>> dimDatass) {
        this.dimDatass = dimDatass;
    }
}

