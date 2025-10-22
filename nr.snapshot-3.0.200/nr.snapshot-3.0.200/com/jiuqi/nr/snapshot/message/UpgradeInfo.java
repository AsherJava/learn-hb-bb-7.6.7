/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.List;

public class UpgradeInfo {
    private DataRegionDefine dataRegionDefine;
    private List<String> fieldCodes = new ArrayList<String>();
    private List<String> dataTableKeys = new ArrayList<String>();
    private List<List<String>> datass = new ArrayList<List<String>>();

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public void setDataRegionDefine(DataRegionDefine dataRegionDefine) {
        this.dataRegionDefine = dataRegionDefine;
    }

    public List<String> getFieldCodes() {
        return this.fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }

    public List<String> getDataTableKeys() {
        return this.dataTableKeys;
    }

    public void setDataTableKeys(List<String> dataTableKeys) {
        this.dataTableKeys = dataTableKeys;
    }

    public List<List<String>> getDatass() {
        return this.datass;
    }

    public void setDatass(List<List<String>> datass) {
        this.datass = datass;
    }
}

