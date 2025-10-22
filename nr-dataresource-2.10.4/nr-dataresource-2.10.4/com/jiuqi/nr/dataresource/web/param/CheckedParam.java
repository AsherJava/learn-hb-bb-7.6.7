/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 */
package com.jiuqi.nr.dataresource.web.param;

import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import java.util.Set;

public class CheckedParam {
    private String group;
    private boolean onlyField;
    private Set<RuntimeDataSchemeNodeDTO> selected;

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<RuntimeDataSchemeNodeDTO> getSelected() {
        return this.selected;
    }

    public void setSelected(Set<RuntimeDataSchemeNodeDTO> selected) {
        this.selected = selected;
    }

    public boolean isOnlyField() {
        return this.onlyField;
    }

    public void setOnlyField(boolean onlyField) {
        this.onlyField = onlyField;
    }

    public String toString() {
        return "CheckedDTO{group='" + this.group + '\'' + ", selected=" + this.selected + '}';
    }
}

