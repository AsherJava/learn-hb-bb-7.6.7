/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zb.scheme.core.ZbScheme
 */
package com.jiuqi.nr.system.check2.zbScheme;

import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import java.util.List;
import java.util.Set;

public class ZbSchemeReverseVO {
    private List<ZbScheme> zbSchemes;
    private Set<String> filterZbSchemes;
    private Set<String> filterDataSchemes;

    public List<ZbScheme> getZbSchemes() {
        return this.zbSchemes;
    }

    public void setZbSchemes(List<ZbScheme> zbSchemes) {
        this.zbSchemes = zbSchemes;
    }

    public Set<String> getFilterZbSchemes() {
        return this.filterZbSchemes;
    }

    public void setFilterZbSchemes(Set<String> filterZbSchemes) {
        this.filterZbSchemes = filterZbSchemes;
    }

    public Set<String> getFilterDataSchemes() {
        return this.filterDataSchemes;
    }

    public void setFilterDataSchemes(Set<String> filterDataSchemes) {
        this.filterDataSchemes = filterDataSchemes;
    }
}

