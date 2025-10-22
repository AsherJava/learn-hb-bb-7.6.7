/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.nr.period.common.utils.SearchType;

public class SearchParam {
    String language;
    SearchType searchType;

    public SearchParam(String language, SearchType searchType) {
        this.language = language;
        this.searchType = searchType;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public SearchType getSearchType() {
        return this.searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
}

