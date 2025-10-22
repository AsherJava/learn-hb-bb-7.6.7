/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.io.params.base.TableContext;
import java.util.List;
import java.util.Map;

public interface IoQualifier {
    public static final String FORMKEYS = "formKeys";
    public static final String NOACCESSNFORMKEYS = "noAccessnFormKeys";

    public List<String> getFormKeys();

    public List<String> getNoAccessFormKeys();

    public Map<String, List<String>> initQualifier(TableContext var1, DimensionValueSet var2);
}

