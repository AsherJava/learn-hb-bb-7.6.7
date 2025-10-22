/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.lwtree.request;

import com.jiuqi.nr.lwtree.para.TreeLoadParaImpl;
import com.jiuqi.nr.lwtree.request.SearchParam;
import java.util.List;

public class LightTreeLoadParam
extends TreeLoadParaImpl {
    private int showPloy;
    private int showType;
    private String locateNode;
    private List<String> checkNodeKeys;
    private SearchParam searchParam;

    public int getShowPloy() {
        return this.showPloy;
    }

    public void setShowPloy(int showPloy) {
        this.showPloy = showPloy;
    }

    public String getLocateNode() {
        return this.locateNode;
    }

    public void setLocateNode(String locateNode) {
        this.locateNode = locateNode;
    }

    public SearchParam getSearchParam() {
        return this.searchParam;
    }

    public void setSearchParam(SearchParam searchParam) {
        this.searchParam = searchParam;
    }

    public int getShowType() {
        return this.showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public List<String> getCheckNodeKeys() {
        return this.checkNodeKeys;
    }

    public void setCheckNodeKeys(List<String> checkNodeKeys) {
        this.checkNodeKeys = checkNodeKeys;
    }
}

