/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.intf.bean;

import com.jiuqi.nr.singlequeryimport.intf.bean.SearchModelItem;
import java.io.Serializable;
import java.util.List;

public class SearchModelResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SearchModelItem> searchModelItems;
    private boolean result;

    public List<SearchModelItem> getSearchModelItems() {
        return this.searchModelItems;
    }

    public void setSearchModelItems(List<SearchModelItem> searchModelItems) {
        this.searchModelItems = searchModelItems;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}

