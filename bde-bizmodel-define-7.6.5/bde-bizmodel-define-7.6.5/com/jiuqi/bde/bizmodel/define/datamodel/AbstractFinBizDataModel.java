/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 */
package com.jiuqi.bde.bizmodel.define.datamodel;

import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.category.FinBizModelCategory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractFinBizDataModel
implements IBizDataModel {
    @Autowired
    private FinBizModelCategory category;

    public String getCategory() {
        return this.category.getCode();
    }
}

