/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 */
package com.jiuqi.bde.bizmodel.define.datamodel;

import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.category.CustomBizModelCategory;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchDataModel
implements IBizDataModel {
    @Autowired
    private CustomBizModelCategory category;

    public String getCode() {
        return BizDataModelEnum.CUSTOMFETCHMODEL.getCode();
    }

    public String getName() {
        return BizDataModelEnum.CUSTOMFETCHMODEL.getName();
    }

    public String getCategory() {
        return this.category.getCode();
    }

    public int getOrder() {
        return 70;
    }

    public String getEffectScope() {
        return null;
    }
}

