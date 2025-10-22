/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.gather.ISlotListGathers;
import com.jiuqi.nr.dataentry.gather.SlotItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="dataentry_slots")
public class SlotsGatherImp
implements ISlotListGathers<SlotItem> {
    @Override
    public List<SlotItem> gather() {
        return new ArrayList<SlotItem>();
    }
}

