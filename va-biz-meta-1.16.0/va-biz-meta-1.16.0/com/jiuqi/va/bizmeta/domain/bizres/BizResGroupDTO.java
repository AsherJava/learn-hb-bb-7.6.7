/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.bizres;

import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDO;
import java.util.List;

public class BizResGroupDTO
extends BizResGroupDO {
    private List<BizResGroupDTO> children;
    private Boolean catalog;

    public Boolean getCatalog() {
        return this.catalog;
    }

    public void setCatalog(Boolean catalog) {
        this.catalog = catalog;
    }

    public List<BizResGroupDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<BizResGroupDTO> children) {
        this.children = children;
    }
}

