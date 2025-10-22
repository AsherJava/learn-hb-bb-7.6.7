/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.gather.IListGathers;
import com.jiuqi.nr.dataentry.gather.ViewItem;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="dataentry_views")
public class ViewsGatherImp
implements IListGathers<ViewItem> {
    @Override
    public List<ViewItem> gather() {
        return null;
    }

    @Override
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.VIEW;
    }
}

