/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBFieldType;

public class ZBField
extends QueryField {
    private ZBFieldType zbType = ZBFieldType.FIXED;

    public ZBField() {
        this.setType(QueryObjectType.ZB);
    }

    public ZBFieldType getZbType() {
        return this.zbType;
    }

    public void setZbType(ZBFieldType zbType) {
        this.zbType = zbType;
    }
}

