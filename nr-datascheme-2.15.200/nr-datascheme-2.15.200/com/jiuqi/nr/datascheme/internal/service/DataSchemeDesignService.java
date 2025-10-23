/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import java.util.List;

public interface DataSchemeDesignService {
    public List<DesignDataScheme> searchByKeyword(String var1);

    public DesignDataScheme getByParentAndTitle(String var1, String var2);
}

