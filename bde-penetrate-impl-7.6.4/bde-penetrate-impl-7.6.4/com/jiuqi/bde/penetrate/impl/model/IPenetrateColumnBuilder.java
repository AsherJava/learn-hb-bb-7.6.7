/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 */
package com.jiuqi.bde.penetrate.impl.model;

import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import java.util.List;

public interface IPenetrateColumnBuilder<C> {
    public static final String DEFAULT_KEY = "DEFAULT_COLUMNBUILDER";

    public String getBizModel();

    public PenetrateTypeEnum getPenetrateType();

    public List<PenetrateColumn> createQueryColumns(C var1);
}

