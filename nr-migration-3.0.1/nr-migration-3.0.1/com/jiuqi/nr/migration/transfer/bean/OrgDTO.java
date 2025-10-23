/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.nr.migration.transfer.bean;

import com.jiuqi.va.domain.org.OrgDO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrgDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String defineName;
    private String type;
    private Map<String, List<OrgDO>> items;

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, List<OrgDO>> getItems() {
        return this.items;
    }

    public void setItems(Map<String, List<OrgDO>> items) {
        this.items = items;
    }
}

