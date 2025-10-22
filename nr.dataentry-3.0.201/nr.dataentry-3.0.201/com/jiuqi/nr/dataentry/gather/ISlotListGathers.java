/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.IListGathers;
import com.jiuqi.nr.dataentry.util.Consts;

public interface ISlotListGathers<T>
extends IListGathers<T> {
    @Override
    default public Consts.GatherType getGatherType() {
        return Consts.GatherType.SLOT;
    }
}

