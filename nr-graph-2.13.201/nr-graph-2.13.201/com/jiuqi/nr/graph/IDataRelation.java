/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.ILabelabled;

public interface IDataRelation
extends ILabelabled<EdgeLabel> {
    public String getOutKey();

    public String getInKey();
}

