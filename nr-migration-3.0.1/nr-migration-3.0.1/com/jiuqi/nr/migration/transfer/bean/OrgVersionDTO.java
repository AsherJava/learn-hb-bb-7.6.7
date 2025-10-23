/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgVersionDO
 */
package com.jiuqi.nr.migration.transfer.bean;

import com.jiuqi.va.domain.org.OrgVersionDO;
import java.util.List;

public class OrgVersionDTO {
    private String defineName;
    private List<OrgVersionDO> items;

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public List<OrgVersionDO> getItems() {
        return this.items;
    }

    public void setItems(List<OrgVersionDO> items) {
        this.items = items;
    }
}

