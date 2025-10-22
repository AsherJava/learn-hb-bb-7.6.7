/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import java.util.Iterator;

public interface IGRIterator
extends Iterator<IGroupDataRow> {
    public IGroupDataRow peekFirst();
}

