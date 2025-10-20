/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.extend.DataModelTemplate
 */
package com.jiuqi.va.extend;

import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;

public interface DataModelBillExtendTemplate
extends DataModelTemplate {
    default public DataModelType.BizType getBizType() {
        return DataModelType.BizType.BILL;
    }

    default public int getSubBizType() {
        return this.getName().hashCode();
    }

    default public int getOrdinal() {
        return 3;
    }
}

