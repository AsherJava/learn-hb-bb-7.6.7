/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.item;

import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public class RelatedItemSaveParam {
    private List<GcRelatedItemEO> needAddItems;
    private List<GcRelatedItemEO> needUpdateItems;
    private List<GcRelatedItemEO> needDeleteItems;

    public RelatedItemSaveParam() {
    }

    public RelatedItemSaveParam(List<GcRelatedItemEO> needAddItems, List<GcRelatedItemEO> needUpdateItems, List<GcRelatedItemEO> needDeleteItems) {
        this.setNeedAddItems(needAddItems);
        this.setNeedUpdateItems(needUpdateItems);
        this.setNeedDeleteItems(needDeleteItems);
    }

    public List<GcRelatedItemEO> getNeedAddItems() {
        return this.needAddItems;
    }

    public void setNeedAddItems(List<GcRelatedItemEO> needAddItems) {
        this.needAddItems = needAddItems;
    }

    public List<GcRelatedItemEO> getNeedUpdateItems() {
        return this.needUpdateItems;
    }

    public void setNeedUpdateItems(List<GcRelatedItemEO> needUpdateItems) {
        this.needUpdateItems = needUpdateItems;
    }

    public List<GcRelatedItemEO> getNeedDeleteItems() {
        return this.needDeleteItems;
    }

    public void setNeedDeleteItems(List<GcRelatedItemEO> needDeleteItems) {
        this.needDeleteItems = needDeleteItems;
    }
}

