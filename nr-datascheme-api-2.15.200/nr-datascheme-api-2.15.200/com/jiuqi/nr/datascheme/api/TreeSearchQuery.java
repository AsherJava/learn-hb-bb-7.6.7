/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.nr.datascheme.api.core.NodeType;
import java.io.Serializable;
import java.util.List;

public class TreeSearchQuery
implements Serializable {
    private String keyword;
    private String scheme;
    private List<String> schemes;
    private int searchType;

    public int getSearchType() {
        return this.searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public List<String> getSchemes() {
        return this.schemes;
    }

    public void setSchemes(List<String> schemes) {
        this.schemes = schemes;
    }

    public TreeSearchQuery() {
    }

    public TreeSearchQuery(String keyword) {
        this.keyword = keyword;
    }

    public TreeSearchQuery(String keyword, String scheme) {
        this.keyword = keyword;
        this.scheme = scheme;
    }

    public TreeSearchQuery(String keyword, int searchType) {
        this.keyword = keyword;
        this.searchType = searchType;
    }

    public TreeSearchQuery(String keyword, String scheme, int searchType) {
        this.keyword = keyword;
        this.scheme = scheme;
        this.searchType = searchType;
    }

    public TreeSearchQuery appendSearchType(NodeType nodeType) {
        this.searchType = this.searchType == 0 ? nodeType.getValue() : (this.searchType |= nodeType.getValue());
        return this;
    }
}

