/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.entity;

import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;

public class RelatedItemOffsetRelEntity {
    private String id;
    private GcRelatedItemEO gcRelatedItemEO;
    private GcOffsetRelatedItemEO relatedItemOffsetRelEO;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GcRelatedItemEO getGcRelatedItemEO() {
        return this.gcRelatedItemEO;
    }

    public void setGcRelatedItemEO(GcRelatedItemEO gcRelatedItemEO) {
        this.gcRelatedItemEO = gcRelatedItemEO;
    }

    public GcOffsetRelatedItemEO getRelatedItemOffsetRelEO() {
        return this.relatedItemOffsetRelEO;
    }

    public void setRelatedItemOffsetRelEO(GcOffsetRelatedItemEO relatedItemOffsetRelEO) {
        this.relatedItemOffsetRelEO = relatedItemOffsetRelEO;
    }
}

