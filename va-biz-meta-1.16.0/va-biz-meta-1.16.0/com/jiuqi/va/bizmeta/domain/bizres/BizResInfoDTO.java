/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.bizmeta.domain.bizres;

import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.List;

public class BizResInfoDTO
extends BizResInfoDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private int offset;
    private int limit;
    private String searchKey;
    private boolean pagination;
    private String ids;
    private String title;
    private List<String> groupnames;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGroupnames() {
        return this.groupnames;
    }

    public void setGroupnames(List<String> groupnames) {
        this.groupnames = groupnames;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public boolean isPagination() {
        return this.pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }
}

