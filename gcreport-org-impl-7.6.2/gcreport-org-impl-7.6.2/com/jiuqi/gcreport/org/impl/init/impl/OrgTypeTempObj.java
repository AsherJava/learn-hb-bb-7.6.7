/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.init.impl;

import com.jiuqi.gcreport.org.impl.init.impl.OrgTableIndex;
import java.util.List;

public class OrgTypeTempObj {
    private String code;
    private String name;
    private String[] bizkeyfields;
    private boolean mutliversion;
    private List<OrgTableIndex> indexs;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getBizkeyfields() {
        return this.bizkeyfields;
    }

    public void setBizkeyfields(String[] bizkeyfields) {
        this.bizkeyfields = bizkeyfields;
    }

    public void setMutliversion(boolean mutliversion) {
        this.mutliversion = mutliversion;
    }

    public boolean isMutliversion() {
        return this.mutliversion;
    }

    public List<OrgTableIndex> getIndexs() {
        return this.indexs;
    }

    public void setIndexs(List<OrgTableIndex> indexs) {
        this.indexs = indexs;
    }
}

