/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather;

import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;
import java.util.List;

public interface GcOffsetItemShowType {
    public GcOffsetItemPage getPage();

    public GcOffsetItemDataSource getDataSource();

    public String getCode();

    public String getTitle();

    public List<Object> getSelectedData(Object var1);

    public List<GcOffSetItemAction> actions();

    default public boolean isEnableMemorySort() {
        return false;
    }
}

