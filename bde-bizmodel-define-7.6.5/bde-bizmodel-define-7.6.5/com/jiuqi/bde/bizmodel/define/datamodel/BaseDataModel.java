/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 */
package com.jiuqi.bde.bizmodel.define.datamodel;

import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.category.BaseDataBizModelCategory;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataModel
implements IBizDataModel {
    @Autowired
    private BaseDataBizModelCategory category;

    public String getCode() {
        return BizDataModelEnum.BASEDATAMODEL.getCode();
    }

    public String getName() {
        return BizDataModelEnum.BASEDATAMODEL.getName();
    }

    public String getCategory() {
        return this.category.getCode();
    }

    public int getOrder() {
        return 90;
    }

    public String getEffectScope() {
        return "";
    }
}

